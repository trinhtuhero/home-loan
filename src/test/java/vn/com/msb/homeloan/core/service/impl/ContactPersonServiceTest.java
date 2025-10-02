package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.model.mapper.ContactPersonInfoMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.ContactPersonRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class ContactPersonServiceTest {

  private final String CONTACT_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  ContactPersonService contactPersonService;

  @Mock
  ContactPersonRepository contactPersonRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanApplicationServiceImpl loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @BeforeEach
  void setUp() {
    this.contactPersonService = new ContactPersonServiceImpl(
        contactPersonRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService);
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnContactPersonNotFound() {

    doReturn(Optional.empty()).when(contactPersonRepository).findById(CONTACT_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      contactPersonService.findById(CONTACT_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CONTACT_PERSON_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .build();
    doReturn(Optional.of(entity)).when(contactPersonRepository).findById(CONTACT_ID);

    ContactPerson result = contactPersonService.findById(CONTACT_ID);

    assertEquals(result.getUuid(), entity.getUuid());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      contactPersonService.save(ContactPersonInfoMapper.INSTANCE.toModel(entity),
          ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenInsertNotExistByLoanId_shouldReturnSuccess() throws IOException {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(null).when(contactPersonRepository).findOneByLoanId(LOAN_ID);
    doReturn(entity).when(contactPersonRepository).save(entity);
    ContactPerson result = contactPersonService.save(
        ContactPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    assertEquals(result.getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenInsertExistByLoanId_shouldReturnSuccess() throws IOException {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    doReturn(ContactPersonEntity.builder().uuid(CONTACT_ID).build()).when(contactPersonRepository)
        .findOneByLoanId(LOAN_ID);

    ContactPersonEntity entitySave = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    doReturn(entitySave).when(contactPersonRepository).save(entity);

    ContactPerson result = contactPersonService.save(
        ContactPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    assertEquals(result.getLoanId(), entity.getLoanId());
    assertEquals(result.getUuid(), CONTACT_ID);
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnContactPersonNotFound() {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.empty()).when(contactPersonRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      contactPersonService.save(ContactPersonInfoMapper.INSTANCE.toModel(entity),
          ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.CONTACT_PERSON_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(contactPersonRepository).findById(LOAN_ID);

    doReturn(entity).when(contactPersonRepository).save(entity);

    ContactPerson result = contactPersonService.save(
        ContactPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);

    assertEquals(result.getUuid(), entity.getUuid());
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenValidInput_ThenUpdateCMS_shouldReturnSuccess() throws IOException {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(contactPersonRepository).findById(LOAN_ID);

    doReturn(entity).when(contactPersonRepository).save(entity);

    ContactPerson result = contactPersonService.save(
        ContactPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.CMS);

    assertEquals(result.getUuid(), entity.getUuid());
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenValidInput_ThenFindByLoanId_shouldReturnSuccess() {
    ContactPersonEntity entity = ContactPersonEntity.builder()
        .uuid(CONTACT_ID)
        .loanId(LOAN_ID)
        .type(ContactPersonTypeEnum.HUSBAND)
        .fullName("fullName")
        .build();
    List<ContactPersonEntity> personEntities = new ArrayList<>();
    personEntities.add(entity);
    doReturn(personEntities).when(contactPersonRepository).findByLoanId(LOAN_ID);

    List<ContactPerson> result = contactPersonService.findByLoanId(LOAN_ID);

    assertEquals(result.get(0).getUuid(), entity.getUuid());
    assertEquals(result.get(0).getFullName(), entity.getFullName());
  }
}