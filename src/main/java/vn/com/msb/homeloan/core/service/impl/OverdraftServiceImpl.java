package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.entity.OverdraftEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
import vn.com.msb.homeloan.core.model.mapper.OverdraftMapper;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.OverdraftRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.LoanItemCollateralDistributionService;
import vn.com.msb.homeloan.core.service.OverdraftService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OverdraftServiceImpl implements OverdraftService {

  private final OverdraftRepository overdraftRepository;

  private final LoanApplicationRepository loanApplicationRepository;

  private final CollateralService collateralService;
  private final LoanItemCollateralDistributionService loanItemCollateralDistributionService;

  private final LoanApplicationItemRepository loanApplicationItemRepository;

  private final HomeLoanUtil homeLoanUtil;

  private final CollateralRepository collateralRepository;

  private final CMSTabActionService cmsTabActionService;

  @Override
  public Overdraft save(Overdraft overdraft, ClientTypeEnum clientTypeEnum) {

    loanApplicationRepository.findById(overdraft.getLoanApplicationId()).orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
            overdraft.getLoanApplicationId()));

    List<OverdraftEntity> overdraftEntities = overdraftRepository.findByLoanApplicationId(
        overdraft.getLoanApplicationId());

    String overdraftUuid = overdraft.getUuid();
    if (StringUtils.isEmpty(overdraftUuid)) {
      if (!overdraftEntities.isEmpty()) {
        throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "overdraft", String.valueOf(1));
      }
    } else {
      OverdraftEntity overdraftEntity = overdraftRepository.findById(overdraftUuid).orElseThrow(
          () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "overdraft:uuid",
              overdraftUuid));
      if (FormOfCreditEnum.SECURED_OVERDRAFT.equals(overdraftEntity.getFormOfCredit())
          && FormOfCreditEnum.UNSECURED_OVERDRAFT.equals(overdraft.getFormOfCredit())) {
        loanItemCollateralDistributionService.deleteAllByOverdraftId(overdraftUuid);
      }
      overdraftEntity.setOverdraftPurpose(overdraft.getOverdraftPurpose());
      overdraftEntity.setLoanAmount(overdraft.getLoanAmount());
      overdraftEntity.setOverdraftSubject(overdraft.getOverdraftSubject());
      if (clientTypeEnum.equals(ClientTypeEnum.CMS)) {
        overdraftEntity.setFormOfCredit(overdraft.getFormOfCredit());
        overdraftEntity.setLoanTime(overdraft.getLoanTime());
        overdraftEntity.setInterestCode(overdraft.getInterestCode());
        overdraftEntity.setMargin(overdraft.getMargin());
        overdraftEntity.setPaymentAccountNumber(overdraft.getPaymentAccountNumber());
        overdraftEntity.setDebtPaymentMethod(overdraft.getDebtPaymentMethod());
        overdraftEntity.setLoanAssetValue(overdraft.getLoanAssetValue());
        overdraftEntity.setProductTextCode(overdraft.getProductTextCode());
        overdraftEntity.setDocumentNumber2(overdraft.getDocumentNumber2());
        updateStatusLoanApplication(overdraftEntity.getLoanApplicationId());
      }
    }
    saveLoanItem(overdraft);
    return OverdraftMapper.INSTANCE.toModel(
        overdraftRepository.save(OverdraftMapper.INSTANCE.toEntity(overdraft)));
  }

  @Override
  public List<Overdraft> saves(List<Overdraft> overdrafts, ClientTypeEnum clientTypeEnum) {
    String message = validInput(overdrafts);
    if (!message.equals("")) {
      throw new ApplicationException(400, message);
    }
    log.info("Start save list overdraft: {}", overdrafts);
    DecimalFormat df = new DecimalFormat("#.##");

    String loanId = overdrafts.get(0).getLoanApplicationId();

    List<Overdraft> list = new ArrayList<>();

    List<LoanItemCollateralDistribution> deleteLoanItemCollateralDistributions = loanItemCollateralDistributionService.getByLoanIdAndType(
        loanId, "THAU_CHI");
    loanItemCollateralDistributionService.deleteAll(deleteLoanItemCollateralDistributions);

    overdrafts.forEach(overdraft -> {

      if (overdraft.getMargin() != null) {
        Float percent = Float.valueOf(df.format(overdraft.getMargin()));
        log.info("number after being rounded: {}", percent);
        overdraft.setMargin(percent);
      }

      Overdraft saveOverdraft = save(overdraft, clientTypeEnum);

      if (overdraft.getFormOfCredit().equals(FormOfCreditEnum.SECURED_OVERDRAFT)) {
        overdraft.getLoanItemCollateralDistributions().forEach(
            loanItemCollateralDistribution -> loanItemCollateralDistribution.setLoanItemId(
                saveOverdraft.getUuid()));
        List<LoanItemCollateralDistribution> saveLoanItemCollateralDistributions = loanItemCollateralDistributionService.saves(
            overdraft.getLoanItemCollateralDistributions(), "THAU_CHI");

        saveOverdraft.setLoanItemCollateralDistributions(saveLoanItemCollateralDistributions);
        saveOverdraft.setLTDPercent(
            (float) saveOverdraft.getLoanAmount() / saveOverdraft.getLoanAssetValue() * 100);
        saveOverdraft.setEquityCapital(
            saveOverdraft.getLoanAssetValue() - saveOverdraft.getLoanAmount());
      }
      list.add(saveOverdraft);
    });

    if (clientTypeEnum.equals(ClientTypeEnum.CMS)) {
      if (CollectionUtils.isNotEmpty(overdrafts)) {
        cmsTabActionService.save(loanId, CMSTabEnum.LOAN_INFO);
      }
    }

    return list;
  }

  @Override
  public void delete(String uuid, ClientTypeEnum clientTypeEnum) {
    OverdraftEntity overdraftEntity = overdraftRepository.findById(uuid).orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "overdraft:uuid", uuid));
    overdraftRepository.deleteById(uuid);

    updateStatusLoanApplication(overdraftEntity.getLoanApplicationId());
    //Xoa du lieu trong bang loan application item
    List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationIdAndLoanPurpose(
        overdraftEntity.getLoanApplicationId(), LoanPurposeEnum.THAU_CHI);

    if (!CollectionUtils.isEmpty(loanApplicationItemEntities)) {
      loanApplicationItemRepository.deleteAll(loanApplicationItemEntities);
    }

    if (ClientTypeEnum.CMS.equals(clientTypeEnum)) {
      if (CollectionUtils.isEmpty(
          loanApplicationItemRepository.findByLoanApplicationIdWithoutCreditCardAndOverdraft(
              overdraftEntity.getLoanApplicationId()))
          && CollectionUtils.isEmpty(overdraftRepository.findByLoanApplicationIdAndFormOfCredit(
          overdraftEntity.getLoanApplicationId(), FormOfCreditEnum.SECURED_OVERDRAFT))) {
        cmsTabActionService.delete(overdraftEntity.getLoanApplicationId(), CMSTabEnum.LOAN_INFO);
      }
    }
  }

  @Override
  public List<Overdraft> getListOverdraft(String loanId) {
    log.info("Start getListOverdraft by loanId: {}", loanId);
    List<OverdraftEntity> overdraftEntities = overdraftRepository.findByLoanApplicationId(loanId);
    log.info("OverdraftEntities found: {}", overdraftEntities);
    List<LoanItemCollateralDistribution> collateralDistributions = getCollateralDistributions(
        loanId);
    List<Overdraft> overdrafts = OverdraftMapper.INSTANCE.toModels(overdraftEntities);

    List<Collateral> collaterals = CollateralMapper.INSTANCE.toModels(
        collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId));

    overdrafts.forEach(overdraft -> {
      List<LoanItemCollateralDistribution> collateralDistributionOfOverdraft = collateralDistributions.stream()
          .filter(loanItemCollateralDistribution -> loanItemCollateralDistribution.getLoanItemId()
              .equals(overdraft.getUuid())).collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(collateralDistributionOfOverdraft)) {
        collateralDistributionOfOverdraft.forEach(collateralDistribution -> {
          collateralDistribution.setCollateral(
              homeLoanUtil.getById(collaterals, collateralDistribution.getCollateralId()));
          collateralDistribution.setIndex(collateralDistribution.getCollateral() != null
              ? collateralDistribution.getCollateral().getIndex() : 0);
        });
        collateralDistributionOfOverdraft = collateralDistributionOfOverdraft.stream()
            .sorted(Comparator.comparing(LoanItemCollateralDistribution::getIndex))
            .collect(Collectors.toList());
        overdraft.setLoanItemCollateralDistributions(collateralDistributionOfOverdraft);
      }

      overdraft.setLoanItemCollateralDistributions(collateralDistributionOfOverdraft);
    });

    log.info("End getListOverdraft: {}", overdrafts);
    return overdrafts;
  }

  private String validInput(List<Overdraft> overdrafts) {
    StringBuilder message = new StringBuilder();
    String objectName = "overdraft";
    for (Overdraft overdraft : overdrafts) {
      String separator = "";
      if (overdraft.getFormOfCredit().equals(FormOfCreditEnum.SECURED_OVERDRAFT)) {
        if (overdraft.getLoanAmount() == null) {
          message.append(separator).append(objectName).append(".LoanAmount mustn't be null");
        }
        if (overdraft.getProductTextCode() == null) {
          message.append(separator).append(objectName).append(".ProductCode mustn't be null");
        }
        if (overdraft.getLoanAssetValue() == null) {
          message.append(separator).append(objectName).append(".LoanAssetValue mustn't be null");
        }
        if (overdraft.getLoanItemCollateralDistributions().isEmpty()) {
          message.append(separator).append(objectName)
              .append(".LoanItemCollateralDistributions mustn't be empty");
        }
      }
    }

    return message.toString();
  }

  private void saveLoanItem(Overdraft overdraft) {
    log.info("Start save Loan Item Overdraft");

    LoanApplicationItemEntity itemEntity = getOverdraftFromLoanItem(
        overdraft.getLoanApplicationId());
    itemEntity = itemEntity == null ? LoanApplicationItemEntity.builder().build() : itemEntity;

    itemEntity.setLoanApplicationId(overdraft.getLoanApplicationId());
    // itemEntity.setProductTextCode(ProductTextCodeEnum.CREDIT_CARD);
    itemEntity.setLoanPurpose(LoanPurposeEnum.THAU_CHI);
    itemEntity.setLoanAmount(overdraft.getLoanAmount());
    log.info("Overdraft: {}", itemEntity);

    loanApplicationItemRepository.save(itemEntity);
  }

  private LoanApplicationItemEntity getOverdraftFromLoanItem(String loanId) {
    List<LoanApplicationItemEntity> loanApplicationItemEntities = loanApplicationItemRepository.findByLoanApplicationId(
        loanId);
    if (!CollectionUtils.isEmpty(loanApplicationItemEntities)) {
      List<LoanApplicationItemEntity> temps = loanApplicationItemEntities.stream()
          .filter(x -> LoanPurposeEnum.THAU_CHI.equals(x.getLoanPurpose()))
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(temps)) {
        return temps.get(0);
      }
    }
    return null;
  }

  private List<LoanItemCollateralDistribution> getCollateralDistributions(String loanId) {

    return loanItemCollateralDistributionService.getCollateralAndDistributionByLoanIdAndType(loanId,
        "THAU_CHI");
  }

  private void updateStatusLoanApplication(String loanId) {
//        LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId).orElseThrow(()
//            -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loanApplication:uuid", loanId));
//        log.info("Start update status for loanApplication");
//        loanApplication.setStatus(LoanInfoStatusEnum.WAITING_FOR_CONFIRM);
//        loanApplicationRepository.save(loanApplication);
//        log.info("Successful update status for loanApplication to WAITING_FOR_CONFIRM");
  }
}
