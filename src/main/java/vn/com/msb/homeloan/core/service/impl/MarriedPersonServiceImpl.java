package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MarriedPerson;
import vn.com.msb.homeloan.core.model.mapper.MarriedPersonInfoMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.MarriedPersonService;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MarriedPersonServiceImpl implements MarriedPersonService {

  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;

  @Override
  public MarriedPerson findById(String uuid) {
    MarriedPersonEntity entity = marriedPersonRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.MARRIED_PERSON_NOT_FOUND));

    return MarriedPersonInfoMapper.INSTANCE.toModel(entity);
  }

  @Override
  @Transactional
  public MarriedPersonEntity save(MarriedPerson marriedPerson, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            marriedPerson.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    MarriedPersonEntity marriedPersonEntity = null;
    if (ClientTypeEnum.LDP == clientType) {
      if (StringUtils.isEmpty(marriedPerson.getUuid())) {
        marriedPersonEntity = marriedPersonRepository.findOneByLoanId(marriedPerson.getLoanId());
        if (marriedPersonEntity == null) {
          marriedPersonEntity = MarriedPersonEntity.builder().build();
        }
      } else {
        marriedPersonEntity = marriedPersonRepository.findById(marriedPerson.getUuid())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.MARRIED_PERSON_NOT_FOUND));
      }
      fillDataMarriedPerson(marriedPersonEntity, marriedPerson);
    } else if (ClientTypeEnum.CMS == clientType) {
      if (!StringUtils.isEmpty(marriedPerson.getUuid())) {
        marriedPersonRepository.findByUuid(marriedPerson.getUuid())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.MARRIED_PERSON_NOT_FOUND));
      } else {
        validateInput(marriedPerson);
      }
      marriedPersonEntity = MarriedPersonInfoMapper.INSTANCE.toEntity(marriedPerson);
      cmsTabActionService.save(marriedPersonEntity.getLoanId(), CMSTabEnum.MARRIED_PERSON);
    }

    if (marriedPersonEntity != null && marriedPersonEntity.getBirthday() != null) {
      marriedPersonEntity.setAge(DateUtils.calculateAge(marriedPersonEntity.getBirthday()));
    }
    marriedPersonRepository.save(marriedPersonEntity);
    return marriedPersonEntity;
  }

  private void fillDataMarriedPerson(MarriedPersonEntity marriedPersonEntity,
      MarriedPerson marriedPerson) {
    marriedPersonEntity.setLoanId(marriedPerson.getLoanId());
    marriedPersonEntity.setBirthday(marriedPerson.getBirthday());
    marriedPersonEntity.setFullName(marriedPerson.getFullName());
    marriedPersonEntity.setGender(marriedPerson.getGender());
    marriedPersonEntity.setIdNo(marriedPerson.getIdNo());
    marriedPersonEntity.setIssuedOn(marriedPerson.getIssuedOn());
    marriedPersonEntity.setPlaceOfIssue(marriedPerson.getPlaceOfIssue());
    marriedPersonEntity.setOldIdNo(marriedPerson.getOldIdNo());
    marriedPersonEntity.setOldIdNo3(marriedPerson.getOldIdNo3());
    marriedPersonEntity.setPhone(marriedPerson.getPhone());
    marriedPersonEntity.setEmail(marriedPerson.getEmail());
    marriedPersonEntity.setNationality(marriedPerson.getNationality());
    marriedPersonEntity.setProvince(marriedPerson.getProvince());
    marriedPersonEntity.setProvinceName(marriedPerson.getProvinceName());
    marriedPersonEntity.setDistrict(marriedPerson.getDistrict());
    marriedPersonEntity.setDistrictName(marriedPerson.getDistrictName());
    marriedPersonEntity.setWard(marriedPerson.getWard());
    marriedPersonEntity.setWardName(marriedPerson.getWardName());
    marriedPersonEntity.setAddress(marriedPerson.getAddress());
  }

  @Override
  public void deleteById(String uuid) {
    marriedPersonRepository.deleteById(uuid);
  }

  @Override
  public List<MarriedPerson> findByLoanId(String loanId) {
    return MarriedPersonInfoMapper.INSTANCE.toModels(marriedPersonRepository.findByLoanId(loanId));
  }

  private void validateInput(MarriedPerson marriedPerson) {
    Map<String, String> errorDetail = new HashMap<>();
    if (marriedPerson.getNationality() == null) {
      errorDetail.put("not_blank.nationality", "must not be blank");
    }
    if (StringUtils.isEmpty(marriedPerson.getPhone())) {
      errorDetail.put("not_blank.phone", "must not be blank");
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
