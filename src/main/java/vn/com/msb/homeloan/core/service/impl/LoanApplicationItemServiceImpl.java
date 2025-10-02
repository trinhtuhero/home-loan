package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.constant.collateral.LoanPurposeDetailEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanSupplementingBusinessCapitalEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.*;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.ObjectUtil;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class LoanApplicationItemServiceImpl implements LoanApplicationItemService {

  private final LoanApplicationItemRepository loanApplicationItemRepository;

  private final LoanApplicationService loanApplicationService;

  private final LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  private final CollateralRepository collateralRepository;

  private final CollateralService collateralService;

  private final LoanItemCollateralDistributionService loanItemCollateralDistributionService;

  private final CMSTabActionService cmsTabActionService;

  private final CreditworthinessItemService creditworthinessItemService;

  private final LoanApplicationRepository loanApplicationRepository;

  private final OverdraftService overdraftService;

  private final OverdraftRepository overdraftRepository;

  private final HomeLoanUtil homeLoanUtil;

  private final CMSLoanItemOverDraftMapper mapper;

  @Override
  public List<LoanApplicationItem> findByLoanApplicationIdOrderByCreatedAtAsc(String loanId) {

    List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
        loanId);

    return LoanApplicationItemMapper.INSTANCE.toModels(loanApplicationItemEntities);
  }

  @Override
  @Transactional
  public void delete(String uuid, ClientTypeEnum clientType) {
    LoanApplicationItemEntity loanApplicationItemEntity = loanApplicationItemRepository.findById(
        uuid).orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_application_item:uuid",
            uuid));
    String loanId = loanApplicationItemEntity.getLoanApplicationId();

    loanApplicationItemRepository.deleteById(uuid);
    // xóa thông tin bản ghi tương ứng trong creditworthinessItem
    creditworthinessItemService.deleteByConditions(CreditworthinessItem.builder()
        .loanApplicationId(loanApplicationItemEntity.getLoanApplicationId())
        .loanApplicationItemId(loanApplicationItemEntity.getUuid()).build());

    if (clientType.equals(ClientTypeEnum.CMS)) {
      if (CollectionUtils.isEmpty(
          loanApplicationItemRepository.findByLoanApplicationIdWithoutCreditCardAndOverdraft(
              loanId))
          && CollectionUtils.isEmpty(
          overdraftRepository.findByLoanApplicationIdAndFormOfCredit(loanId,
              FormOfCreditEnum.SECURED_OVERDRAFT))) {
        cmsTabActionService.delete(loanId, CMSTabEnum.LOAN_INFO);
      }
    }
  }

  @Override
  @Transactional
  public List<LoanApplicationItem> saves(List<LoanApplicationItem> loanApplicationItems,
      int countOverdraft,ClientTypeEnum clientType) {

    String loanId = loanApplicationItems.get(0).getLoanApplicationId();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    List<LoanItemCollateralDistribution> deleteLoanItemCollateralDistributions = loanItemCollateralDistributionService.getByLoanIdAndType(
        loanId, "KHOAN_VAY");
    loanItemCollateralDistributionService.deleteAll(deleteLoanItemCollateralDistributions);

    List<LoanApplicationItem> list = new ArrayList<>();
    loanApplicationItems.forEach(loanApplicationItem -> {
      LoanApplicationItem saveLoanApplicationItem = save(loanApplicationItem,countOverdraft, clientType);

      loanApplicationItem.getLoanItemCollateralDistributions().forEach(
          loanItemCollateralDistribution -> loanItemCollateralDistribution.setLoanItemId(
              saveLoanApplicationItem.getUuid()));
      List<LoanItemCollateralDistribution> saveLoanItemCollateralDistributions = loanItemCollateralDistributionService.saves(
          loanApplicationItem.getLoanItemCollateralDistributions(), "KHOAN_VAY");

      saveLoanApplicationItem.setLoanItemCollateralDistributions(
          saveLoanItemCollateralDistributions);

      list.add(saveLoanApplicationItem);
    });

    if (clientType.equals(ClientTypeEnum.CMS)) {
      if (CollectionUtils.isNotEmpty(loanApplicationItems)) {
        cmsTabActionService.save(loanId, CMSTabEnum.LOAN_INFO);
      }
    }

    return list;
  }

  // Update or insert
  @Override
  @Transactional
  public LoanApplicationItem save(LoanApplicationItem loanApplicationItem, int countOverdraft,
      ClientTypeEnum clientType) {
    // validate input
    validateInput(loanApplicationItem, clientType);

    if (loanApplicationItem.getUuid() != null) {
      findById(loanApplicationItem.getUuid());
    } else {
      // không quá 5 khoản vay
      int count =
          loanApplicationItemRepository.countByLoanApplicationId(
              loanApplicationItem.getLoanApplicationId());
      if (count + countOverdraft >= 5) {
        throw new ApplicationException(
            ErrorEnum.ITEM_LIMIT, "loan application item", String.valueOf(5));
      }
    }
    return LoanApplicationItemMapper.INSTANCE.toModel(loanApplicationItemRepository.save(
        LoanApplicationItemMapper.INSTANCE.toEntity(loanApplicationItem)));
  }

  @Override
  public LoanApplicationItem findById(String uuid) {
    LoanApplicationItemEntity entity = loanApplicationItemRepository.findById(uuid).
        orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND,
            "loan_application_item:uuid", uuid));
    return LoanApplicationItemMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<LoanApplicationItem> getItemCollateralDistribute(String loanId) {
    List<LoanApplicationItem> loanApplicationItems = LoanApplicationItemMapper.INSTANCE.toModels(
        loanApplicationItemRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanId));
    List<LoanItemCollateralDistribution> loanItemCollateralDistributions = LoanItemCollateralDistributionMapper.INSTANCE.toModels(
        loanItemCollateralDistributeRepository.getByLoanApplicationId(loanId));
    if (CollectionUtils.isEmpty(loanItemCollateralDistributions)) {
      return loanApplicationItems;
    }
    List<Collateral> collaterals = CollateralMapper.INSTANCE.toModels(
        collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    emptyIfNull(loanApplicationItems).forEach(loanApplicationItem -> {
      List<LoanItemCollateralDistribution> temp = loanItemCollateralDistributions.stream()
          .filter(x -> loanApplicationItem.getUuid().equalsIgnoreCase(x.getLoanItemId()))
          .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(temp)) {
        temp.forEach(collateralDistribution -> {
          collateralDistribution.setCollateral(
              homeLoanUtil.getById(collaterals, collateralDistribution.getCollateralId()));
          collateralDistribution.setIndex(collateralDistribution.getCollateral() != null
              ? collateralDistribution.getCollateral().getIndex() : 0);
        });
        temp = temp.stream()
            .sorted(Comparator.comparing(LoanItemCollateralDistribution::getIndex))
            .collect(Collectors.toList());
        loanApplicationItem.setLoanItemCollateralDistributions(temp);
      }
    });

    return loanApplicationItems;
  }

  @Override
  public List<Overdraft> getOverdraftCollateralDistribute(String loanId) {
    List<Overdraft> overdrafts = OverdraftMapper.INSTANCE.toModels(
        overdraftRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanId));
    List<LoanItemCollateralDistribution> loanItemCollateralDistributions = LoanItemCollateralDistributionMapper.INSTANCE.toModels(
        loanItemCollateralDistributeRepository.getOverdraftDistribute(loanId));
    if (CollectionUtils.isEmpty(loanItemCollateralDistributions)) {
      return overdrafts;
    }
    List<Collateral> collaterals = CollateralMapper.INSTANCE.toModels(
        collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    emptyIfNull(overdrafts).forEach(overdraft -> {
      List<LoanItemCollateralDistribution> temp = loanItemCollateralDistributions.stream()
          .filter(x -> overdraft.getUuid().equalsIgnoreCase(x.getLoanItemId()))
          .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(temp)) {
        temp.forEach(collateralDistribution -> {
          collateralDistribution.setCollateral(
              homeLoanUtil.getById(collaterals, collateralDistribution.getCollateralId()));
          collateralDistribution.setIndex(collateralDistribution.getCollateral() != null
              ? collateralDistribution.getCollateral().getIndex() : 0);
        });
        temp = temp.stream()
            .sorted(Comparator.comparing(LoanItemCollateralDistribution::getIndex))
            .collect(Collectors.toList());
        overdraft.setLoanItemCollateralDistributions(temp);
      }
    });

    return overdrafts;
  }

  @Override
  public long totalLoanAmount(String loanId) {
    return loanApplicationItemRepository.totalLoanAmount(loanId);
  }


  private void validateInput(LoanApplicationItem loanApplicationItem, ClientTypeEnum clientType) {
    Map<String, String> errorDetail = new HashMap<>();

    if (loanApplicationItem.getLoanAmount() > loanApplicationItem.getLoanAssetValue()) {
      errorDetail.put("invalid.loan_amount", "less than or equal to loan_asset_value");
    }

    if (ClientTypeEnum.CMS.equals(clientType)) {
      // Ngân hàng, Số tài khoản, Người thụ hưởng
      // Chỉ hiển thị khi Phương thức giải ngân = “Chuyển khoản”
      // hoặc Phương thức giải ngân = “Phong tỏa” hoặc Phương thức giải ngân = “Chuyển khoản/ Tiền mặt
      if (!Arrays.asList(DisbursementMethodEnum.TRANSFER, DisbursementMethodEnum.BLOCKAGE,
              DisbursementMethodEnum.TRANSFER_OR_CASH)
          .contains(loanApplicationItem.getDisbursementMethod())) {
        if (loanApplicationItem.getBeneficiaryBank() != null) {
          errorDetail.put("invalid.beneficiary_bank", "must be null");
        }
        if (loanApplicationItem.getBeneficiaryAccount() != null) {
          errorDetail.put("invalid.beneficiary_account", "must be null");
        }
        if (loanApplicationItem.getBeneficiaryFullName() != null) {
          errorDetail.put("invalid.beneficiary_full_name", "must be null");
        }
      }

      // Bắt buộc nếu Phương thức giải ngân = Chuyển khoản hoặc Phong tỏa
            /*if (Arrays.asList(DisbursementMethodEnum.TRANSFER, DisbursementMethodEnum.BLOCKAGE).contains(loanApplicationItem.getDisbursementMethod())) {
                if (Strings.isEmpty(loanApplicationItem.getBeneficiaryBank())) {
                    errorDetail.put("not_empty.beneficiary_bank", "must not be empty");
                }
                if (Strings.isEmpty(loanApplicationItem.getBeneficiaryAccount())) {
                    errorDetail.put("not_empty.beneficiary_account", "must not be empty");
                }
                if (Strings.isEmpty(loanApplicationItem.getBeneficiaryFullName())) {
                    errorDetail.put("not_empty.beneficiary_full_name", "must not be empty");
                }
            }*/

      if (LoanPurposeEnum.LAND.equals(loanApplicationItem.getLoanPurpose())) {
        if (!Arrays.asList(ProductTextCodeEnum.LAND, ProductTextCodeEnum.M_HOUSING)
            .contains(loanApplicationItem.getProductTextCode())) {
          errorDetail.put("pattern.product_text_code", "must match pattern LAND|M_HOUSING");
        }
      }

      if (LoanPurposeEnum.TIEU_DUNG.equals(loanApplicationItem.getLoanPurpose())) {
        if (!Arrays.asList(ProductTextCodeEnum.TIEU_DUNG_TSBĐ, ProductTextCodeEnum.M_HOUSING)
            .contains(loanApplicationItem.getProductTextCode())) {
          errorDetail.put("pattern.product_text_code",
              "must match pattern TIEU_DUNG_TSBĐ|M_HOUSING");
        }
      }

      if (LoanPurposeEnum.XAY_SUA_NHA.equals(loanApplicationItem.getLoanPurpose())) {
        if (!Arrays.asList(ProductTextCodeEnum.HOAN_THIEN_CHCC, ProductTextCodeEnum.XAY_SUA_NHA,
            ProductTextCodeEnum.M_HOUSING).contains(loanApplicationItem.getProductTextCode())) {
          errorDetail.put("pattern.product_text_code",
              "must match pattern HOAN_THIEN_CHCC|XAY_SUA_NHA|M_HOUSING");
        }
      }

      // Ho kinh doanh
      boolean mustNullLoanPurposeDetail = true;
      boolean mustNullLoanSupplementingBusinessCapital = true;
      boolean mustNullDebtAcknowledgmentContractPeriod = true;
      boolean mustNullLoanInvestmentFixedAsset = true;
      boolean mustNullIsPrivateBusinessOwner = true;
      boolean mustNullRefinanceLoan = true;

      if (LoanPurposeEnum.HO_KINH_DOANH.equals(loanApplicationItem.getLoanPurpose())) {

        // Chỉ hiển thị nếu Mục đích vay vốn = Vay hộ kinh doanh
        if (loanApplicationItem.getLoanPurposeDetail() == null) {
          errorDetail.put("not_empty.loan_purpose_detail", "must not be empty");
        } else {
          mustNullLoanPurposeDetail = false;
          mustNullIsPrivateBusinessOwner = false;
          if (LoanPurposeDetailEnum.VAY_BO_SUNG_VON_KINH_DOANH.equals(
              loanApplicationItem.getLoanPurposeDetail())) {
            if (loanApplicationItem.getLoanSupplementingBusinessCapital() == null) {
              errorDetail.put("not_empty.loan_supplementing_business_capital", "must not be empty");
            } else {
              mustNullLoanSupplementingBusinessCapital = false;
            }
          }

          if (LoanPurposeDetailEnum.VAY_BO_SUNG_VON_KINH_DOANH.equals(
              loanApplicationItem.getLoanPurposeDetail())
              && loanApplicationItem.getLoanSupplementingBusinessCapital() != null) {
            // Chỉ hiển thị nếu Vay bổ sung vốn kinh doanh = Vay hạn mức
            if (LoanSupplementingBusinessCapitalEnum.VAY_HAN_MUC.equals(
                loanApplicationItem.getLoanSupplementingBusinessCapital())) {
              mustNullRefinanceLoan = false;
              if (loanApplicationItem.getDebtAcknowledgmentContractPeriod() == null) {
                errorDetail.put("not_empty.debt_acknowledgment_contract_period",
                    "must not be empty");
              } else {
                mustNullDebtAcknowledgmentContractPeriod = false;
              }
              if (loanApplicationItem.getDebtAcknowledgmentContractPeriod()
                  > loanApplicationItem.getLoanTime()) {
                errorDetail.put("invalid.debt_acknowledgment_contract_period",
                    "must be less than the loan time");
              }
              if (!Arrays.asList(ProductTextCodeEnum.VAY_KINH_DOANH_HAN_MUC)
                  .contains(loanApplicationItem.getProductTextCode())) {
                errorDetail.put("pattern.product_text_code",
                    "must match pattern VAY_KINH_DOANH_HAN_MUC");
              }
            } else {
              if (!Arrays.asList(ProductTextCodeEnum.VAY_KINH_DOANH_TUNG_LAN)
                  .contains(loanApplicationItem.getProductTextCode())) {
                errorDetail.put("pattern.product_text_code",
                    "must match pattern VAY_KINH_DOANH_TUNG_LAN");
              }
            }
          }
          if (LoanPurposeDetailEnum.VAY_DAU_TU_TAI_SAN_CO_DINH.equals(
              loanApplicationItem.getLoanPurposeDetail())) {
            if (loanApplicationItem.getLoanInvestmentFixedAsset() == null) {
              errorDetail.put("not_empty.loan_investment_fixed_asset", "must not be empty");
            } else {
              mustNullLoanInvestmentFixedAsset = false;
            }
            if (!Arrays.asList(ProductTextCodeEnum.VAY_KINH_DOANH_TUNG_LAN)
                .contains(loanApplicationItem.getProductTextCode())) {
              errorDetail.put("pattern.product_text_code",
                  "must match pattern VAY_KINH_DOANH_TUNG_LAN");
            }
          }
        }
      }

      if (loanApplicationItem.getLoanPurposeDetail() != null && mustNullLoanPurposeDetail) {
        errorDetail.put("invalid.loan_purpose_detail", "must be null");
      }
      if (loanApplicationItem.getLoanSupplementingBusinessCapital() != null
          && mustNullLoanSupplementingBusinessCapital) {
        errorDetail.put("invalid.loan_supplementing_business_capital", "must be null");
      }
      if (loanApplicationItem.getLoanInvestmentFixedAsset() != null
          && mustNullLoanInvestmentFixedAsset) {
        errorDetail.put("invalid.loan_investment_fixed_asset", "must be null");
      }
      if (loanApplicationItem.getDebtAcknowledgmentContractPeriod() != null
          && mustNullDebtAcknowledgmentContractPeriod) {
        errorDetail.put("invalid.debt_acknowledgment_contract_period", "must be null");
      }
      if (loanApplicationItem.getIsPrivateBusinessOwner() != null
          && mustNullIsPrivateBusinessOwner) {
        errorDetail.put("invalid.is_private_business_owner", "must be null");
      }
      if (loanApplicationItem.getRefinanceLoan() != null && mustNullRefinanceLoan) {
        errorDetail.put("invalid.refinance_loan", "must be null");
      }
    }

    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }

  @Override
  @Transactional
  public LoanItemAndOverdraft saveLoanItemAndOverdraft(LoanItemAndOverdraft loanItemAndOverdraft,
      ClientTypeEnum clientTypeEnum) {

    if (Boolean.TRUE.equals(checkLoanItemIsEmpty(loanItemAndOverdraft))) {
      throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_ITEM_NOT_EMPTY);
    }
    List<LoanApplicationItem> loanApplicationItems = new ArrayList<>();
    log.info("Start saveLoanItemAndOverdraft: {}", loanItemAndOverdraft);
    LoanItemAndOverdraft itemAndOverdraft = new LoanItemAndOverdraft();
    List<LoanItemOverDraftDto> lstDto = new ArrayList<>();
    if (!loanItemAndOverdraft.getLoanApplicationItem().isEmpty()) {
      lstDto.addAll(
          loanItemAndOverdraft.getLoanApplicationItem().stream()
              .map(mapper::mapLoanItemToDto)
              .collect(Collectors.toList()));
    }
    int countOverdraft = 0;
    if (!loanItemAndOverdraft.getOverdraft().isEmpty()
        && ObjectUtil.isNotEmpty(loanItemAndOverdraft.getOverdraft().get(0).getFormOfCredit())
        && loanItemAndOverdraft
        .getOverdraft()
        .get(0)
        .getFormOfCredit()
        .equals(FormOfCreditEnum.SECURED_OVERDRAFT)) {
      lstDto.add(mapper.mapOverDraftToDto(loanItemAndOverdraft.getOverdraft().get(0)));
      countOverdraft ++;
    }

    validateCollateralDistributions(lstDto);

    if (!loanItemAndOverdraft.getLoanApplicationItem().isEmpty()) {
      loanApplicationItems = saves(loanItemAndOverdraft.getLoanApplicationItem(),countOverdraft, clientTypeEnum);
      itemAndOverdraft.setLoanApplicationItem(loanApplicationItems);
    }
    if (!loanItemAndOverdraft.getOverdraft().isEmpty()) {
      itemAndOverdraft.setOverdraft(
          overdraftService.saves(loanItemAndOverdraft.getOverdraft(), clientTypeEnum));
    }
    return itemAndOverdraft;
  }

  private void validateCollateralDistributions(List<LoanItemOverDraftDto> lstDto) {
    String loanId = lstDto.get(0).getLoanApplicationId();
    HashMap<String, Float> distributeMap = new HashMap<>();

    for (LoanItemOverDraftDto dto
        : lstDto) {
      if (CollectionUtils.isEmpty(dto.getLoanItemCollateralDistributions())) {
        // throw
        throw new ApplicationException(ErrorEnum.COLLATERAL_DISTRIBUTION_ERROR_1);
      } else {
        for (LoanItemCollateralDistribution loanItemCollateralDistribution : dto.getLoanItemCollateralDistributions()) {
          if (loanItemCollateralDistribution.getCollateralId() == null) {
            // throw
            throw new ApplicationException(ErrorEnum.COLLATERAL_DISTRIBUTION_ERROR_1);
          } else {
            collateralService.findById(loanItemCollateralDistribution.getCollateralId());
            distributeMap.put(loanItemCollateralDistribution.getCollateralId(),
                distributeMap.get(loanItemCollateralDistribution.getCollateralId()) != null ?
                    distributeMap.get(loanItemCollateralDistribution.getCollateralId())
                        + loanItemCollateralDistribution.getPercent()
                    : loanItemCollateralDistribution.getPercent());
          }
        }
      }
    }

    // Nếu có ít nhất 1 TSBĐ chưa được phân bổ, hệ thống hiển thị thông báo “TSBĐ <x> chưa được phân bổ vào khoản vay”
    // Lấy thông tin tài sản bảo đảm
    List<Collateral> collaterals = collateralService.findByLoanId(loanId);
    for (Collateral collateral : collaterals) {
      List<LoanItemCollateralDistribution> collateralDistributions = loanItemCollateralDistributionService.getByLoanIdAndCollateralId(
          loanId, collateral.getUuid());
      if (!distributeMap.containsKey(collateral.getUuid()) && collateralDistributions.isEmpty()) {
        throw new ApplicationException(ErrorEnum.COLLATERAL_DISTRIBUTION_ERROR_2,
            collateral.getUuid());
      }
    }

    // Nếu TSBĐ đã được phân bổ >100%, hiển thị thông báo “Tổng tỉ lệ phân bổ của Tài sản bảo đảm <x> phải nhỏ hơn hoặc bằng 100%”.
    for (Map.Entry<String, Float> entry : distributeMap.entrySet()) {
      if (entry.getValue() > 100) {
        throw new ApplicationException(ErrorEnum.COLLATERAL_DISTRIBUTION_ERROR_3, entry.getKey());
      }
    }
  }

  private Boolean checkLoanItemIsEmpty(LoanItemAndOverdraft loanItemAndOverdraft) {
    boolean isOverdraftSecured = false;
    for (Overdraft overdraft : loanItemAndOverdraft.getOverdraft()) {
      if (overdraft.getFormOfCredit().equals(FormOfCreditEnum.SECURED_OVERDRAFT)) {
        isOverdraftSecured = true;
      }
    }
    return !isOverdraftSecured && loanItemAndOverdraft.getLoanApplicationItem().isEmpty();
  }


}
