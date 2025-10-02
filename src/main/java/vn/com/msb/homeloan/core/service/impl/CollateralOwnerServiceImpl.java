package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.mapper.CollateralOwnerMapper;
import vn.com.msb.homeloan.core.repository.CollateralOwnerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CollateralOwnerService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class CollateralOwnerServiceImpl implements CollateralOwnerService {

  private final CollateralOwnerRepository collateralOwnerRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final CMSTabActionService cmsTabActionService;
  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final LoanApplicationService loanApplicationService;

  @Override
  public CollateralOwner findById(String id) {
    CollateralOwnerEntity entity = collateralOwnerRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.COLLATERAL_OWNER_NOT_FOUND, id));

    return CollateralOwnerMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<CollateralOwner> findByLoanId(String loanId) {
    List<CollateralOwnerEntity> entities = collateralOwnerRepository.findByLoanId(loanId);
    return CollateralOwnerMapper.INSTANCE.toModels(entities);
  }

  @Override
  public List<CollateralOwner> getAllByLoanId(String loanId) {
    List<CollateralOwner> results = new ArrayList<>();
    //Khach hang
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CollateralOwner customer = CollateralOwner.builder()
        .uuid(loanApplication.getUuid())
        .mapType(CollateralOwnerMapTypeEnum.ME)
        .fullName(loanApplication.getFullName())
        .phone(loanApplication.getPhone())
        .idNo(loanApplication.getIdNo())
        .isCheck(false)
        .build();
    results.add(customer);

    //Vc kh
    MarriedPersonEntity marriedPersonEntity = marriedPersonRepository.findOneByLoanId(loanId);
    if (marriedPersonEntity != null) {
      ContactPersonTypeEnum personType =
          GenderEnum.FEMALE.equals(loanApplication.getGender()) ? ContactPersonTypeEnum.HUSBAND
              : ContactPersonTypeEnum.WIFE;
      CollateralOwner temp = CollateralOwner.builder()
          .uuid(marriedPersonEntity.getUuid())
          .mapType(CollateralOwnerMapTypeEnum.COUPLE)
          .fullName(marriedPersonEntity.getFullName())
          .phone(marriedPersonEntity.getPhone())
          .idNo(marriedPersonEntity.getIdNo())
          .relationship(personType.getCode())
          .isCheck(false)
          .build();
      results.add(temp);
    }

    //nguoi dong tra no
    List<LoanPayerEntity> loanPayerEntities = loanPayerRepository.findByLoanId(loanId);
    emptyIfNull(loanPayerEntities).forEach(loanPayer -> {
      CollateralOwner temp = CollateralOwner
          .builder()
          .uuid(loanPayer.getUuid())
          .fullName(loanPayer.getFullName())
          .phone(loanPayer.getPhone())
          .idNo(loanPayer.getIdNo())
          .relationship(loanPayer.getRelationshipType() == null ? ""
              : loanPayer.getRelationshipType().getCode())
          .mapType(CollateralOwnerMapTypeEnum.LOAN_PAYER)
          .isCheck(false)
          .build();
      results.add(temp);
    });

    //ben thu 3
    List<CollateralOwner> collateralOwners = findByLoanId(loanId);
    emptyIfNull(collateralOwners).forEach(item -> {
      CollateralOwner temp = CollateralOwner
          .builder()
          .uuid(item.getUuid())
          .fullName(item.getFullName())
          .phone(item.getPhone())
          .idNo(item.getIdNo())
          .relationship(item.getRelationship())
          .mapType(CollateralOwnerMapTypeEnum.THIRD_PARTY)
          .isCheck(false)
          .build();
      results.add(temp);
    });

    return results;
  }

  @Override
  public CollateralOwner save(CollateralOwner collateralOwner) {
    if (StringUtils.isEmpty(collateralOwner.getRelationship())) {
      collateralOwner.setRelationship(null);
    }
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            collateralOwner.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);

    if (!StringUtils.isEmpty(collateralOwner.getUuid())) {
      Optional<CollateralOwnerEntity> entityDB = collateralOwnerRepository.findById(
          collateralOwner.getUuid());
      if (!entityDB.isPresent()) {
        throw new ApplicationException(ErrorEnum.COLLATERAL_OWNER_NOT_FOUND,
            collateralOwner.getUuid());
      }
    } else {
      if (LoanCurrentStepEnum.LOAN_INCOME.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_COLLATERAL.getCode());
        loanApplicationRepository.save(loanApplication);
      }
    }

    CollateralOwnerEntity entity = CollateralOwnerMapper.INSTANCE.toEntity(collateralOwner);
    entity = collateralOwnerRepository.save(entity);
    return CollateralOwnerMapper.INSTANCE.toModel(entity);
  }

  @Override
  @Transactional
  public List<CollateralOwner> saves(List<CollateralOwner> collateralOwners) {
    if (CollectionUtils.isEmpty(collateralOwners)) {
      return collateralOwners;
    }
    List<CollateralOwner> results = new ArrayList<>();

    for (CollateralOwner collateralOwner : collateralOwners) {
      results.add(save(collateralOwner));
    }

    cmsTabActionService.save(collateralOwners.get(0).getLoanId(), CMSTabEnum.COLLATERAL_OWNER);
    return results;
  }

  @Override
  public void deleteById(String id, ClientTypeEnum clientType) {
    Optional<CollateralOwnerEntity> entity = collateralOwnerRepository.findById(id);
    if (!entity.isPresent()) {
      throw new ApplicationException(ErrorEnum.COLLATERAL_OWNER_NOT_FOUND, id);
    }

    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            entity.get().getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);

    collateralOwnerRepository.deleteById(id);

    if (clientType.equals(ClientTypeEnum.CMS)) {
      List<CollateralOwnerEntity> entities = collateralOwnerRepository.findByLoanId(
          entity.get().getLoanId());
      if (CollectionUtils.isEmpty(entities)) {
        cmsTabActionService.delete(entity.get().getLoanId(), CMSTabEnum.COLLATERAL_OWNER);
      }
    }
  }
}
