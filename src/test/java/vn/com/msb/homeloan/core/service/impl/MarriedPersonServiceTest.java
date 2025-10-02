package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MarriedPerson;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.mapper.MarriedPersonInfoMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MarriedPersonRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.MarriedPersonService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class MarriedPersonServiceTest {

  private final String MARRIED_PERSON_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  MarriedPersonService marriedPersonService;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  @BeforeEach
  void setUp() {
    this.marriedPersonService = new MarriedPersonServiceImpl(
        marriedPersonRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService);

  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnMarriedPersonNotFound() {

    doReturn(Optional.empty()).when(marriedPersonRepository).findById(MARRIED_PERSON_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      marriedPersonService.findById(MARRIED_PERSON_ID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.MARRIED_PERSON_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenFindById_shouldReturnSuccess() {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .build();
    doReturn(Optional.of(entity)).when(marriedPersonRepository).findById(MARRIED_PERSON_ID);

    MarriedPerson result = marriedPersonService.findById(MARRIED_PERSON_ID);

    assertEquals(result.getUuid(), entity.getUuid());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnLoanApplicationNotFound() {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      marriedPersonService.save(MarriedPersonInfoMapper.INSTANCE.toModel(entity),
          ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenRMUpdateMarriedPerson_shouldReturnSuccess()
      throws ParseException, IOException {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(marriedPersonRepository).findByUuid(MARRIED_PERSON_ID);
    doReturn(entity).when(marriedPersonRepository).save(any());
    MarriedPersonEntity result = marriedPersonService.save(
        MarriedPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.CMS);
    assertEquals(result.getLoanId(), entity.getLoanId());
  }

  @Test
  void givenValidInput_ThenRMUpdateMarriedPerson_shouldReturnFail() throws ParseException {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .loanId(LOAN_ID)
        .fullName("fullName")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      marriedPersonService.save(MarriedPersonInfoMapper.INSTANCE.toModel(entity),
          ClientTypeEnum.CMS);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.INVALID_FORM.getCode());
  }

  @Test
  void givenValidInput_ThenInsertExistByLoanId_shouldReturnSuccess()
      throws ParseException, IOException {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(null)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996"))
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    doReturn(MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID).build()).when(marriedPersonRepository).findOneByLoanId(LOAN_ID);

    MarriedPersonEntity entitySave = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    doReturn(entitySave).when(marriedPersonRepository).save(any());

    MarriedPersonEntity result = marriedPersonService.save(
        MarriedPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);
    assertEquals(result.getLoanId(), entity.getLoanId());
    assertEquals(result.getUuid(), MARRIED_PERSON_ID);
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnMarriedPersonNotFound() {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.empty()).when(marriedPersonRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      marriedPersonService.save(MarriedPersonInfoMapper.INSTANCE.toModel(entity),
          ClientTypeEnum.LDP);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.MARRIED_PERSON_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() throws IOException {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    loanApplication.setStatus(LoanInfoStatusEnum.DRAFT);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(Optional.of(entity)).when(marriedPersonRepository).findById(LOAN_ID);

    doReturn(entity).when(marriedPersonRepository).save(entity);

    MarriedPersonEntity result = marriedPersonService.save(
        MarriedPersonInfoMapper.INSTANCE.toModel(entity), ClientTypeEnum.LDP);

    assertEquals(result.getUuid(), entity.getUuid());
    assertEquals(result.getFullName(), entity.getFullName());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnSuccess() {
    marriedPersonService.deleteById(MARRIED_PERSON_ID);
    verify(marriedPersonRepository, times(1)).deleteById(MARRIED_PERSON_ID);
  }

  @Test
  void givenValidInput_ThenFindByLoanID_shouldReturnSuccess() {
    MarriedPersonEntity entity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON_ID)
        .loanId(LOAN_ID)
        .fullName("fullName")
        .build();
    List<MarriedPersonEntity> personEntities = new ArrayList<>();
    personEntities.add(entity);

    doReturn(personEntities).when(marriedPersonRepository).findByLoanId(LOAN_ID);

    List<MarriedPerson> result = marriedPersonService.findByLoanId(LOAN_ID);

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getUuid(), entity.getUuid());
    assertEquals(result.get(0).getFullName(), entity.getFullName());
  }

}
