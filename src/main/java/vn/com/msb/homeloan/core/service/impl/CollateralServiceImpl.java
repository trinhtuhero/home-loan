package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.constant.collateral.StatusUsingEnum;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.CollateralOwnerMap;
import vn.com.msb.homeloan.core.model.ErrorDetail;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
import vn.com.msb.homeloan.core.model.mapper.CollateralOwnerMapper;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class CollateralServiceImpl implements CollateralService {

  private final CollateralRepository collateralRepository;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final CollateralOwnerMapRepository collateralOwnerMapRepository;
  private final EnvironmentProperties environmentProperties;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  @Override
  public Collateral findById(String id) {
    CollateralEntity entity = collateralRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.COLLATERAL_NOT_FOUND, id));

    return CollateralMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<Collateral> findByLoanId(String loanId) {
    List<CollateralEntity> entities = collateralRepository.findByLoanId(loanId);
    return CollateralMapper.INSTANCE.toModels(entities);
  }

  @Override
  public List<Collateral> findByLoanIdOrderByCreatedAtAsc(String loanId) {
    List<CollateralEntity> entities = collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId);
    return CollateralMapper.INSTANCE.toModels(entities);
  }

  @Override
  public List<Collateral> findCollateralInfoByLoanId(String loanId) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    List<Collateral> collaterals = CollateralMapper.INSTANCE.toModels(
        collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    List<CollateralOwner> collateralOwners = CollateralOwnerMapper.INSTANCE.toModels(
        collateralOwnerRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    List<CollateralOwnerMapEntity> collateralOwnerMapEntities = collateralOwnerMapRepository.findByLoanId(
        loanId);
    for (Collateral collateral : collaterals) {
      if (!CollateralStatusEnum.THIRD_PARTY.equals(collateral.getStatus())) {
        continue;
      }
      List<CollateralOwnerMapEntity> temp = collateralOwnerMapEntities.stream()
          .filter(x -> x.getCollateralId().equalsIgnoreCase(collateral.getUuid()))
          .collect(Collectors.toList());

      collateral.setCollateralOwners(
          getCollateralOwnerByCollateral(loanApplication, collateralOwners, temp));
    }
    return collaterals;
  }

  @Override
  @Transactional
  public Collateral save(Collateral collateral, ClientTypeEnum clientType) {
    validateInput(collateral, clientType);
    if (StringUtils.isEmpty(collateral.getRelationship())) {
      collateral.setRelationship(null);
    }

    if (StringUtils.isEmpty(collateral.getLocation())) {
      collateral.setLocation(null);
    }

    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            collateral.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    CollateralEntity entity;

    if (!StringUtils.isEmpty(collateral.getUuid())) {
      CollateralEntity entityDB = collateralRepository.findById(collateral.getUuid())
          .orElseThrow(
              () -> new ApplicationException(ErrorEnum.COLLATERAL_NOT_FOUND, collateral.getUuid()));
      if (ClientTypeEnum.LDP.equals(clientType)) {
        entity = ldpModelToEntity(entityDB, collateral);
      } else {
        entity = CollateralMapper.INSTANCE.toEntity(collateral);
        entity.setSoTsMvalue(entityDB.getSoTsMvalue());
      }
    } else {
      if (LoanCurrentStepEnum.LOAN_INCOME.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_COLLATERAL.getCode());
        loanApplicationRepository.save(loanApplication);
      }
      entity = CollateralMapper.INSTANCE.toEntity(collateral);
    }

    entity = collateralRepository.save(entity);
    collateral.setUuid(entity.getUuid());

    if (ClientTypeEnum.CMS.equals(clientType)) {
      // Xóa map chủ tài sản của tài sản (collateral_id)
      collateralOwnerMapRepository.deleteByCollateralId(collateral.getUuid());
      //Save collateral owner map
      if (!CollectionUtils.isEmpty(collateral.getCollateralOwnerMaps())) {
        List<CollateralOwnerMapEntity> collateralOwnerMapEntities = new ArrayList<>();
        for (CollateralOwnerMap map : collateral.getCollateralOwnerMaps()) {
          collateralOwnerMapEntities.add(CollateralOwnerMapEntity.builder()
              .collateralId(entity.getUuid())
              .collateralOwnerId(map.getCollateralOwnerId())
              .type(map.getType())
              .loanId(entity.getLoanId())
              .build());
        }
        //tra ra thong tin collateral owner
        collateralOwnerMapEntities = collateralOwnerMapRepository.saveAll(
            collateralOwnerMapEntities);
        List<CollateralOwner> collateralOwners = CollateralOwnerMapper.INSTANCE.toModels(
            collateralOwnerRepository.getOwnersOfCollateral(collateral.getUuid()));
        collateral.setCollateralOwners(
            getCollateralOwnerByCollateral(loanApplication, collateralOwners,
                collateralOwnerMapEntities));
      }
      cmsTabActionService.save(collateral.getLoanId(), CMSTabEnum.COLLATERAL_INFO);
    }

    return collateral;
  }

  @Override
  @Transactional
  public List<Collateral> saves(List<Collateral> collaterals, ClientTypeEnum clientType) {
    if (org.apache.commons.collections.CollectionUtils.isEmpty(collaterals)) {
      throw new ApplicationException(ErrorEnum.COLLATERAL_NOT_EMPTY);
    }

        /*Khi Lưu tab thông tin TSBĐ, thực hiện kiểm tra,
        nếu tồn tại bản ghi TSBĐ = Phương tiện vận tải hoặc Giấy tờ có giá thì cần tồn tại ít nhất 1 bản ghi TSBĐ = Nhà đất hoặc chung cư*/
    List<CollateralTypeEnum> collateralTypes = CollectionUtils.emptyIfNull(collaterals).stream()
        .map(Collateral::getType).collect(Collectors.toList());
    if ((collateralTypes.contains(CollateralTypeEnum.PTVT) || collateralTypes.contains(
        CollateralTypeEnum.GTCG)) && !(collateralTypes.contains(CollateralTypeEnum.ND)
        || collateralTypes.contains(CollateralTypeEnum.CC))) {
      throw new ApplicationException(ErrorEnum.COLLATERAL_SAVES_ERROR_1);
    }

    List<Collateral> result = new ArrayList<>();
    for (Collateral collateral : collaterals) {
      result.add(save(collateral, clientType));
    }
    return result;
  }

  private CollateralEntity ldpModelToEntity(CollateralEntity entity, Collateral model) {
    if (entity == null) {
      entity = CollateralEntity.builder().build();
    }
    entity.setUuid(model.getUuid());
    entity.setLoanId(model.getLoanId());
    entity.setType(model.getType());
    entity.setStatus(model.getStatus());
    entity.setFullName(model.getFullName());
    if (!StringUtils.isEmpty(model.getRelationship())) {
      entity.setRelationship(Enum.valueOf(ContactPersonTypeEnum.class, model.getRelationship()));
    }
    entity.setProvince(model.getProvince());
    entity.setProvinceName(model.getProvinceName());
    entity.setDistrict(model.getDistrict());
    entity.setDistrictName(model.getDistrictName());
    entity.setWard(model.getWard());
    entity.setWardName(model.getWardName());
    entity.setAddress(model.getAddress());
    entity.setValue(model.getValue());
    if (!StringUtils.isEmpty(model.getLocation())) {
      entity.setLocation(Enum.valueOf(CollateralLocationEnum.class, model.getLocation()));
    }
    entity.setDescription(model.getDescription());
    return entity;
  }

  @Override
  @Transactional
  public void deleteById(String id, ClientTypeEnum clientType) {
    CollateralEntity entity = collateralRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "Uuid", id));
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(entity.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "Uuid",
            entity.getLoanId()));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    collateralRepository.deleteById(id);

    List<LoanItemCollateralDistributionEntity> loanItemCollateralDistributionEntities = loanItemCollateralDistributeRepository.findByCollateralId(
        id);
    loanItemCollateralDistributeRepository.deleteAll(loanItemCollateralDistributionEntities);

    try {
      collateralOwnerMapRepository.deleteByLoanIdAndCollateralId(entity.getLoanId(), id);
    } catch (Exception ignored) {
      log.error(ignored.getMessage());
    }
    if (clientType.equals(ClientTypeEnum.CMS)) {
      List<CollateralEntity> entities = collateralRepository.findByLoanId(
          loanApplication.getUuid());
      if (CollectionUtils.isEmpty(entities)) {
        cmsTabActionService.delete(loanApplication.getUuid(), CMSTabEnum.COLLATERAL_INFO);
      }
    }
  }

  @Override
  public long totalValues(String loanId) {
    return collateralRepository.totalValues(loanId);
  }

  @Override
  public long totalGuaranteedValues(String loanId) {
    return collateralRepository.totalGuaranteedValues(loanId);
  }

  @Override
  public List<String> validateBeforeNextTab(String loanId) {
    List<String> list = new ArrayList<>();
        /*Khi Lưu tab thông tin TSBĐ, thực hiện kiểm tra,
        nếu tồn tại bản ghi TSBĐ = Phương tiện vận tải hoặc Giấy tờ có giá thì cần tồn tại ít nhất 1 bản ghi TSBĐ = Nhà đất hoặc chung cư*/
    List<Collateral> collaterals = findByLoanId(loanId);

    if (CollectionUtils.isEmpty(collaterals)) {
      list.add(Constants.TAB_COLLATERAL_INVALID_CODE_1);
    }

    List<CollateralTypeEnum> collateralTypes = CollectionUtils.emptyIfNull(collaterals).stream()
        .map(Collateral::getType).collect(Collectors.toList());
    if ((collateralTypes.contains(CollateralTypeEnum.PTVT) || collateralTypes.contains(
        CollateralTypeEnum.GTCG)) && !(collateralTypes.contains(CollateralTypeEnum.ND)
        || collateralTypes.contains(CollateralTypeEnum.CC))) {
      list.add(Constants.TAB_COLLATERAL_INVALID_CODE_2);
    }
    return list;
  }

  private void validateInput(Collateral collateral, ClientTypeEnum clientType) {
    Map<String, String> errorDetail = new HashMap<>();
    if (!StringUtils.isEmpty(collateral.getRelationship())
        && StringUtils.isEmpty(ContactPersonTypeEnum.toMap.get(collateral.getRelationship()))) {
      errorDetail.put("pattern.relationship", "must match pattern " +
          String.join("|", ContactPersonTypeEnum.toMap.keySet()));
    }

    if (!StringUtils.isEmpty(collateral.getLocation())
        && StringUtils.isEmpty(CollateralLocationEnum.toMap.get(collateral.getLocation()))) {
      errorDetail.put("pattern.location", "must match pattern (MD|MD35|MD5|OTHERS)");
    }

    if (!Arrays.asList(CollateralTypeEnum.PTVT, CollateralTypeEnum.GTCG, CollateralTypeEnum.OTHERS)
        .contains(collateral.getType())) {
      if (StringUtils.isEmpty(collateral.getProvince())) {
        errorDetail.put("not_blank.province", "must not be blank");
      }

      if (StringUtils.isEmpty(collateral.getProvinceName())) {
        errorDetail.put("not_blank.province_name", "must not be blank");
      }

      if (StringUtils.isEmpty(collateral.getDistrict())) {
        errorDetail.put("not_blank.district", "must not be blank");
      }

      if (StringUtils.isEmpty(collateral.getDistrictName())) {
        errorDetail.put("not_blank.district_name", "must not be blank");
      }

      if (StringUtils.isEmpty(collateral.getWard())) {
        errorDetail.put("not_blank.ward", "must not be blank");
      }

      if (StringUtils.isEmpty(collateral.getWardName())) {
        errorDetail.put("not_blank.ward_name", "must not be blank");
      }
    }

    if (ClientTypeEnum.LDP.equals(clientType)) {
      if (environmentProperties.getProvinceSpecial()
          .contains(String.format(";%s;", collateral.getProvince()))
          && collateral.getType().getCode().equalsIgnoreCase(CollateralTypeEnum.ND.getCode())) {
        if (StringUtils.isEmpty(collateral.getLocation())) {
          errorDetail.put("not_blank.location", "must not be blank");
        }
      }
    }

    if (ClientTypeEnum.CMS.equals(clientType)) {
      if (Arrays.asList(CollateralTypeEnum.ND, CollateralTypeEnum.CC)
          .contains(collateral.getType())) {
        // legal_doc
        if (StringUtils.isEmpty(collateral.getLegalDoc())) {
          errorDetail.put("not_blank.legal_doc", "must not be blank");
        }
        // doc_issued_on
        if (collateral.getDocIssuedOn() == null) {
          errorDetail.put("not_blank.doc_issued_on", "must not be blank");
        }
        //doc_place_of_issue
        if (StringUtils.isEmpty(collateral.getDocPlaceOfIssue())) {
          errorDetail.put("not_blank.doc_place_of_issue", "must not be blank");
        }
      }
      if (CollateralTypeEnum.PTVT.equals(collateral.getType())) {
        // Loại giấy tờ
        if (collateral.getTypeOfDoc() == null) {
          errorDetail.put("not_blank.type_of_doc", "must not be blank");
        }
        // Số đăng ký xe/HĐMB
        if (StringUtils.isEmpty(collateral.getRegistrationOrContractNo())) {
          errorDetail.put("not_blank.registration_or_contract_no", "must not be blank");
        }
        // Ngày cấp
        if (collateral.getDocIssuedOn() == null) {
          errorDetail.put("not_blank.doc_issued_on", "must not be blank");
        }
        // Số khung
        if (StringUtils.isEmpty(collateral.getChassisNo())) {
          errorDetail.put("not_blank.chassis_no", "must not be blank");
        }
        // Số máy
        if (StringUtils.isEmpty(collateral.getEngineNo())) {
          errorDetail.put("not_blank.engine_no", "must not be blank");
        }
        // Tình trạng ô tô
        if (collateral.getStatusUsing() == null) {
          errorDetail.put("not_blank.status_using", "must not be blank");
        }
        // Thời gian sử dụng (tháng)
        if (StatusUsingEnum.OLD_CAR.equals(collateral.getStatusUsing())
            && collateral.getDurationOfUsed() == null) {
          errorDetail.put("not_blank.duration_of_used", "must not be blank");
        }
        // Loại xe-Biển kiểm soát
                /*if (StringUtils.isEmpty(collateral.getAssetDescription())) {
                    errorDetail.put("not_blank.asset_description", "must not be blank");
                }*/
      }
      if (CollateralTypeEnum.GTCG.equals(collateral.getType())) {
        // Số tài khoản
                /*if (StringUtils.isEmpty(collateral.getAccountNumber())) {
                    errorDetail.put("not_blank.account_number", "must not be blank");
                }*/

        // Số sổ tiết kiệm
        if (StringUtils.isEmpty(collateral.getSavingBookNo())) {
          errorDetail.put("not_blank.saving_book_no", "must not be blank");
        }

        if (collateral.getMaturityDate() == null) {
          errorDetail.put("not_blank.", "must not be blank");
        }
        // Ngày đến hạn tiền gửi
        if (collateral.getMaturityDate() == null) {
          errorDetail.put("not_blank.maturity_date", "must not be blank");
        }
        // Chi nhánh phát hành
        if (StringUtils.isEmpty(collateral.getIssuedBranch())) {
          errorDetail.put("not_blank.issued_branch", "must not be blank");
        }
        // Lãi suất tiết kiệm
        if (collateral.getInterestRate() == null) {
          errorDetail.put("not_blank.interest_rate", "must not be blank");
        }
      }
    }

    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }

    new ErrorDetail();
  }

  private List<CollateralOwner> getCollateralOwnerByCollateral(
      LoanApplicationEntity loanApplication, List<CollateralOwner> collateralOwners,
      List<CollateralOwnerMapEntity> collateralOwnerMapEntities) {
    if (CollectionUtils.isEmpty(collateralOwnerMapEntities)) {
      return new ArrayList<>();
    }

    List<CollateralOwner> results = new ArrayList<>();
    //cua khach hang
    List<String> customers = collateralOwnerMapEntities.stream()
        .filter(x -> CollateralOwnerMapTypeEnum.ME.equals(x.getType()))
        .map(CollateralOwnerMapEntity::getCollateralOwnerId)
        .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(customers)) {
      CollateralOwner collateralOwner = CollateralOwner
          .builder()
          .uuid(loanApplication.getUuid())
          .phone(loanApplication.getPhone())
          .idNo(loanApplication.getIdNo())
          .fullName(loanApplication.getFullName())
          .mapType(CollateralOwnerMapTypeEnum.ME)
          .build();

      results.add(collateralOwner);
    }

    //Cua vk chong khach hang
    List<String> couples = collateralOwnerMapEntities.stream()
        .filter(x -> CollateralOwnerMapTypeEnum.COUPLE.equals(x.getType()))
        .map(CollateralOwnerMapEntity::getCollateralOwnerId)
        .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(couples)) {
      Optional<MarriedPersonEntity> marriedPersonEntity = marriedPersonRepository.findById(
          couples.get(0));
      if (marriedPersonEntity.isPresent()) {
        ContactPersonTypeEnum personType =
            GenderEnum.FEMALE.equals(loanApplication.getGender()) ? ContactPersonTypeEnum.HUSBAND
                : ContactPersonTypeEnum.WIFE;
        CollateralOwner collateralOwner = CollateralOwner
            .builder()
            .uuid(marriedPersonEntity.get().getUuid())
            .fullName(marriedPersonEntity.get().getFullName())
            .phone(marriedPersonEntity.get().getPhone())
            .idNo(marriedPersonEntity.get().getIdNo())
            .relationship(personType.getCode())
            .mapType(CollateralOwnerMapTypeEnum.COUPLE)
            .build();
        results.add(collateralOwner);
      }
    }

    //Nguoi dong tra no
    List<String> loanPayers = collateralOwnerMapEntities.stream()
        .filter(x -> CollateralOwnerMapTypeEnum.LOAN_PAYER.equals(x.getType()))
        .map(CollateralOwnerMapEntity::getCollateralOwnerId)
        .collect(Collectors.toList());

    if (!CollectionUtils.isEmpty(loanPayers)) {
      List<LoanPayerEntity> loanPayerEntities = loanPayerRepository.findByLoanId(
          loanApplication.getUuid());
      if (!CollectionUtils.isEmpty(loanPayerEntities)) {
        List<LoanPayerEntity> temp = loanPayerEntities.stream()
            .filter(x -> loanPayers.contains(x.getUuid()))
            .collect(Collectors.toList());
        emptyIfNull(temp).forEach(loanPayer -> {
          CollateralOwner collateralOwner = CollateralOwner
              .builder()
              .uuid(loanPayer.getUuid())
              .fullName(loanPayer.getFullName())
              .phone(loanPayer.getPhone())
              .idNo(loanPayer.getIdNo())
              .relationship(
                  loanPayer.getRelationshipType() != null ? loanPayer.getRelationshipType()
                      .getCode() : "")
              .mapType(CollateralOwnerMapTypeEnum.LOAN_PAYER)
              .build();
          results.add(collateralOwner);
        });
      }
    }

    //Ben thu 3
    List<String> thirdParties = collateralOwnerMapEntities.stream()
        .filter(x -> CollateralOwnerMapTypeEnum.THIRD_PARTY.equals(x.getType()))
        .map(CollateralOwnerMapEntity::getCollateralOwnerId)
        .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(thirdParties) && !CollectionUtils.isEmpty(collateralOwners)) {
      List<CollateralOwner> temps = collateralOwners.stream()
          .filter(x -> thirdParties.contains(x.getUuid()))
          .collect(Collectors.toList());
      emptyIfNull(temps).forEach(temp -> {
        temp.setMapType(CollateralOwnerMapTypeEnum.THIRD_PARTY);
        results.add(temp);
      });
    }

    return results;
  }
}
