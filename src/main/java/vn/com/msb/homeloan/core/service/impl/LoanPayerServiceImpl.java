package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.model.mapper.LoanPayerMapper;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanPayerService;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LoanPayerServiceImpl implements LoanPayerService {

  private final LoanPayerRepository loanPayerRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final SalaryIncomeRepository salaryIncomeRepository;
  private final BusinessIncomeRepository businessIncomeRepository;
  private final OtherIncomeRepository otherIncomeRepository;
  private final OtherEvaluateRepository otherEvaluateRepository;
  private final CicItemRepository cicItemRepository;
  private final OpRiskRepository opRiskRepository;

  private final CreditworthinessItemRepository creditworthinessItemRepository;

  @Override
  public LoanPayer findById(String id) {
    LoanPayerEntity entity = loanPayerRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));

    return LoanPayerMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<LoanPayer> findByLoanId(String loanId) {
    List<LoanPayerEntity> entities = loanPayerRepository.findByLoanId(loanId);
    return LoanPayerMapper.INSTANCE.toModels(entities);
  }

  @Override
  public List<LoanPayer> findByLoanIdOrderByCreatedAtAsc(String loanId) {
    List<LoanPayerEntity> entities = loanPayerRepository.findByLoanIdOrderByCreatedAtAsc(loanId);
    return LoanPayerMapper.INSTANCE.toModels(entities);
  }

  @Override
  @Transactional
  public List<LoanPayer> save(List<LoanPayer> loanPayers) {
    if (loanPayers.size() > 2) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "loan payer", String.valueOf(2));
    }
    List<LoanPayerEntity> lstResult = new ArrayList<>();
    for (LoanPayer loanPayer : loanPayers) {
      lstResult.add(save(loanPayer, ClientTypeEnum.CMS));
    }
    if (!CollectionUtils.isEmpty(loanPayers)) {
      cmsTabActionService.save(loanPayers.get(0).getLoanId(), CMSTabEnum.LOAN_PAYER);
    }
    return LoanPayerMapper.INSTANCE.toModels(lstResult);
  }

  @Override
  @Transactional
  public LoanPayerEntity save(LoanPayer loanPayer, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            loanPayer.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    LoanPayerEntity loanPayerEntity = new LoanPayerEntity();
    if (StringUtils.isEmpty(loanPayer.getUuid())) {
      Long countPayer = loanPayerRepository.countByLoanId(loanPayer.getLoanId());
      if (countPayer != null && countPayer >= 2) {
        throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "loan payer", String.valueOf(2));
      }
    } else {
      loanPayerEntity = loanPayerRepository.findById(loanPayer.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
    }

    if (ClientTypeEnum.LDP == clientType) {
      fillDataLoanPayer(loanPayerEntity, loanPayer);
      if (!StringUtils.isEmpty(loanPayer.getUuid()) && LoanCurrentStepEnum.LOAN_CUSTOMER.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INFORMATION.getCode());
        loanApplicationRepository.save(loanApplication);
      }
    } else if (ClientTypeEnum.CMS == clientType) {
      loanPayerEntity = LoanPayerMapper.INSTANCE.toEntity(loanPayer);
    }
    if (loanPayerEntity.getBirthday() != null) {
      loanPayerEntity.setAge(DateUtils.calculateAge(loanPayerEntity.getBirthday()));
    }
    loanPayerRepository.save(loanPayerEntity);
    return loanPayerEntity;
  }

  private void fillDataLoanPayer(LoanPayerEntity entity, LoanPayer loanPayer) {
    entity.setLoanId(loanPayer.getLoanId());
    entity.setRelationshipType(loanPayer.getRelationshipType());
    entity.setFullName(loanPayer.getFullName());
    entity.setGender(loanPayer.getGender());
    entity.setIdNo(loanPayer.getIdNo());
    entity.setOldIdNo3(loanPayer.getOldIdNo3());
    entity.setIssuedOn(loanPayer.getIssuedOn());
    entity.setPlaceOfIssue(loanPayer.getPlaceOfIssue());
    entity.setOldIdNo(loanPayer.getOldIdNo());
    entity.setEmail(loanPayer.getEmail());
    entity.setBirthday(loanPayer.getBirthday());
    entity.setProvince(loanPayer.getProvince());
    entity.setProvinceName(loanPayer.getProvinceName());
    entity.setDistrict(loanPayer.getDistrict());
    entity.setDistrictName(loanPayer.getDistrictName());
    entity.setWard(loanPayer.getWard());
    entity.setWardName(loanPayer.getWardName());
    entity.setAddress(loanPayer.getAddress());
    entity.setMaritalStatus(loanPayer.getMaritalStatus());
    entity.setNationality(loanPayer.getNationality());
    entity.setPhone(loanPayer.getPhone());
  }

  @Override
  @Transactional
  public void deleteById(String id, ClientTypeEnum clientType) {
    LoanPayerEntity entity = loanPayerRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "Uuid", id));
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(entity.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "Uuid",
            entity.getLoanId()));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    loanPayerRepository.deleteById(id);

    //xóa hết loan_payer_id ở những bảng liên quan(salary_incomes, business_incomes, other_incomes, other_evaluate)
    List<SalaryIncomeEntity> salaryIncomeEntities = salaryIncomeRepository.findByLoanApplicationIdAndPayerId(
        entity.getLoanId(), id);
    if (!CollectionUtils.isEmpty(salaryIncomeEntities)) {
      salaryIncomeEntities.forEach(si -> si.setPayerId(null));
      salaryIncomeRepository.saveAll(salaryIncomeEntities);
    }

    List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdAndPayerId(
        entity.getLoanId(), id);
    if (!CollectionUtils.isEmpty(businessIncomeEntities)) {
      businessIncomeEntities.forEach(si -> si.setPayerId(null));
      businessIncomeRepository.saveAll(businessIncomeEntities);
    }

    List<OtherIncomeEntity> otherIncomeEntities = otherIncomeRepository.findByLoanApplicationIdAndPayerId(
        entity.getLoanId(), id);
    if (!CollectionUtils.isEmpty(otherIncomeEntities)) {
      otherIncomeEntities.forEach(si -> si.setPayerId(null));
      otherIncomeRepository.saveAll(otherIncomeEntities);
    }

    List<OtherEvaluateEntity> otherEvaluateEntities = otherEvaluateRepository.findByLoanApplicationIdAndPayerId(
        entity.getLoanId(), id);
    if (!CollectionUtils.isEmpty(otherEvaluateEntities)) {
      otherEvaluateEntities.forEach(si -> si.setPayerId(null));
      otherEvaluateRepository.saveAll(otherEvaluateEntities);
    }

    // set target = null trong bảng creditworthiness_items
    List<CreditworthinessItemEntity> creditworthinessItemEntities = creditworthinessItemRepository.findByLoanApplicationIdAndTarget(
        entity.getLoanId(), id);
    if (!CollectionUtils.isEmpty(creditworthinessItemEntities)) {
      creditworthinessItemEntities.forEach(obj -> obj.setTarget(null));
      creditworthinessItemRepository.saveAll(creditworthinessItemEntities);
    }

    //xóa hết data liên quan ở bảng opRisk và cic_item
    List<OpRiskEntity> opRiskEntities = opRiskRepository.findByLoanApplicationIdAndIdentityCardIn(
        entity.getLoanId(), new HashSet<>(Arrays.asList(entity.getIdNo(), entity.getOldIdNo())));
    if (!CollectionUtils.isEmpty(opRiskEntities)) {
      opRiskRepository.deleteAll(opRiskEntities);
    }

    List<CicItemEntity> cicItemEntities = cicItemRepository.findByLoanApplicationAndIdentityCardIn(
        entity.getLoanId(), new HashSet<>(Arrays.asList(entity.getIdNo(), entity.getOldIdNo())));
    if (!CollectionUtils.isEmpty(cicItemEntities)) {
      cicItemRepository.deleteAll(cicItemEntities);
    }

    //nếu loan-payer ứng vs loanId bị xóa hết thì xóa cả thông tin cmsTabAction
    if (ClientTypeEnum.CMS == clientType) {
      List<LoanPayerEntity> loanPayerEntities = loanPayerRepository.findByLoanId(
          entity.getLoanId());
      if (CollectionUtils.isEmpty(loanPayerEntities)) {
        cmsTabActionService.delete(entity.getLoanId(), CMSTabEnum.LOAN_PAYER);
      }
    }
  }
}
