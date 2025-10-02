package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.model.mapper.ContactPersonInfoMapper;
import vn.com.msb.homeloan.core.repository.ContactPersonRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ContactPersonServiceImpl implements ContactPersonService {

  private final ContactPersonRepository contactPersonRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationServiceImpl loanApplicationService;
  private final CMSTabActionService cmsTabActionService;

  @Override
  public ContactPerson findById(String uuid) {
    ContactPersonEntity entity = contactPersonRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.CONTACT_PERSON_NOT_FOUND));

    return ContactPersonInfoMapper.INSTANCE.toModel(entity);
  }

  @Override
  @Transactional
  public ContactPerson save(ContactPerson contactPerson, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            contactPerson.getLoanId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    if (StringUtils.isEmpty(contactPerson.getUuid())) {
      ContactPersonEntity checkEntity = contactPersonRepository.findOneByLoanId(
          contactPerson.getLoanId());
      if (checkEntity != null) {
        contactPerson.setUuid(checkEntity.getUuid());
      }
    } else {
      Optional<ContactPersonEntity> entityOptional = contactPersonRepository.findById(
          contactPerson.getUuid());
      if (!entityOptional.isPresent()) {
        throw new ApplicationException(ErrorEnum.CONTACT_PERSON_NOT_FOUND);
      }
    }

    ContactPersonEntity entity = ContactPersonInfoMapper.INSTANCE.toEntity(contactPerson);
    entity = contactPersonRepository.save(entity);
    if (ClientTypeEnum.CMS.equals(clientType)) {
      cmsTabActionService.save(contactPerson.getLoanId(), CMSTabEnum.CONTACT_PERSON);
    }
    return ContactPersonInfoMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<ContactPerson> findByLoanId(String loanId) {
    return ContactPersonInfoMapper.INSTANCE.toModels(contactPersonRepository.findByLoanId(loanId));
  }
}
