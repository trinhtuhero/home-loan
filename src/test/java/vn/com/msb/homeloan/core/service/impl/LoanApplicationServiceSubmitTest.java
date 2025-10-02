package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.DownloadStatus;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanCurrentStepEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.MobioStatusEnum;
import vn.com.msb.homeloan.core.entity.FileConfigEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationSequenceEntity;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceSubmitTest extends LoanApplicationServiceBaseTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String PROFILE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @Test
  @DisplayName("LoanApplicationService Test CUSTOMER SUBMIT FILE function success")
  void givenValidInput_ThenSubmitLoanApplication_shouldReturnSuccess() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setLoanCode("190120220001");
    loanApplicationEntity.setCreatedAt(Instant.now());
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode(),
            loanApplicationEntity.getLoanCode(), LOAN_ID, LoanCurrentStepEnum.LOAN_SUBMIT.getCode(),
            DownloadStatus.NEED_UPLOAD_ZIP.getValue(), MobioStatusEnum.WAITING.getCode(), "");
    doReturn(null).when(loanStatusChangeRepository).save(any());
    doReturn("").when(sendMailService).genHtmlContentOfAttachFile(loanApplication.getUuid(), null);

    doReturn(null).when(fileService).generatePreSignedUrlToUpload(any());

    //doReturn(true).when(fileService).upload(any(), any(), true);

    LoanUploadFileEntity loanUploadFileEntity = new LoanUploadFileEntity();
    loanUploadFileEntity.setUuid("test");

    FileConfigEntity fileConfigEntity = new FileConfigEntity();

    doReturn(Optional.of(fileConfigEntity)).when(fileConfigRepository).findByCode(any());

    doReturn(Optional.of(loanUploadFileEntity)).when(loanUploadFileRepository)
        .getDNVVByLoanId(any());

    doReturn(loanUploadFileEntity).when(loanUploadFileRepository).save(loanUploadFileEntity);

    try (MockedStatic<HomeLoanUtil> theMock = Mockito.mockStatic(HomeLoanUtil.class)) {
      theMock.when(() -> HomeLoanUtil.exportFilePdf(loanApplication.getUuid(), ""))
          .thenReturn(new byte[0]);

      int result = loanApplicationService.customerSubmitFile(LOAN_ID,
          LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(), "");

      assertEquals(result, 1);
    }

  }

  @Test
  @DisplayName("LoanApplicationService Test CUSTOMER SUBMIT FILE function success")
  void givenValidInput_ThenAcceptLoanApplication_shouldReturnSuccess() throws IOException {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.NEED_ADDITIONAL_INFO);
    loanApplicationEntity.setLoanCode("190120220001");
    loanApplicationEntity.setCreatedAt(Instant.now());
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    doReturn(1).when(loanApplicationRepository)
        .updateStatus(LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode(),
            loanApplicationEntity.getLoanCode(), LOAN_ID, LoanCurrentStepEnum.LOAN_SUBMIT.getCode(),
            DownloadStatus.NEED_UPLOAD_ZIP.getValue(), MobioStatusEnum.WAITING.getCode(), "");

    doReturn("").when(sendMailService).genHtmlContentOfAttachFile(loanApplication.getUuid(), null);

    LoanUploadFileEntity loanUploadFileEntity = new LoanUploadFileEntity();
    loanUploadFileEntity.setUuid("test");

    FileConfigEntity fileConfigEntity = new FileConfigEntity();

    doReturn(Optional.of(fileConfigEntity)).when(fileConfigRepository).findByCode(any());

    doReturn(Optional.of(loanUploadFileEntity)).when(loanUploadFileRepository)
        .getDNVVByLoanId(any());

    doReturn(loanUploadFileEntity).when(loanUploadFileRepository).save(loanUploadFileEntity);

    try (MockedStatic<HomeLoanUtil> theMock = Mockito.mockStatic(HomeLoanUtil.class)) {
      theMock.when(() -> HomeLoanUtil.exportFilePdf(loanApplication.getUuid(), ""))
          .thenReturn(new byte[0]);

      int result = loanApplicationService.customerSubmitFile(LOAN_ID,
          LoanInfoStatusEnum.ACCEPT_LOAN_REQUEST.getCode(), "");

      assertEquals(result, 1);
    }
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE LOAN INFO function LOAN_APPLICATION_NOT_FOUND")
  void givenValidateInput_ThenSubmit_shouldReturnLoanAppNotFound() {
    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.customerSubmitFile(LOAN_ID,
          LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.getCode(), "");
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE LOAN INFO function LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW")
  void givenValidInput_ThenSubmitLoanApplication_shouldReturnStatusNotAllow() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.DRAFT);
    loanApplicationEntity.setLoanCode("190120220001");
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.customerSubmitFile(LOAN_ID,
          LoanInfoStatusEnum.ACCEPT_LOAN_APPLICATION.getCode(), "");
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test UPDATE LOAN INFO function LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW")
  void givenValidInput_ThenAcceptLoanApplication_shouldReturnStatusNotAllow() {
    LoanApplication loanApplication = BuildData.buildLoanApplication(PROFILE_ID);
    loanApplication.setUuid(LOAN_ID);
    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        loanApplication);
    loanApplicationEntity.setCurrentStep(LoanCurrentStepEnum.LOAN_FILE.getCode());
    loanApplicationEntity.setStatus(LoanInfoStatusEnum.NEED_ADDITIONAL_INFO);
    loanApplicationEntity.setLoanCode("190120220001");
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(loanApplication.getUuid());

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationService.customerSubmitFile(LOAN_ID,
          LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode(), "");
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW.getCode());
  }

  @Test
  @DisplayName("LoanApplicationService Test GET LOAN CODE function success")
  void givenValidInput_ThenGetLoanCode_shouldReturnSuccess() {
    Long day = Long.valueOf(DateUtils.convertToSimpleFormat(new Date(), "ddMMyyyy"));
    doReturn(Optional.empty()).when(sequenceRepository).findByDay(day);
    String loanCode =
        ReflectionTestUtils.invokeMethod(
            loanApplicationService, "getLoanCode", ClientTypeEnum.LDP.toString());

    assertEquals(loanCode, String.format("%s%s%s", "LDP", intToString(day, 8), "0001"));
  }

  private String intToString(long num, int digits) {
    String output = Long.toString(num);
    while (output.length() < digits) {
      output = "0" + output;
    }
    return output;
  }

  @Test
  @DisplayName("LoanApplicationService Test GET LOAN CODE function success")
  void givenValidInput_ThenGetLoanCodeExistSequence_shouldReturnSuccess() {
    Long day = Long.valueOf(DateUtils.convertToSimpleFormat(new Date(), "ddMMyyyy"));
    LoanApplicationSequenceEntity sequenceEntity = LoanApplicationSequenceEntity.builder()
        .sequence(20L)
        .build();
    doReturn(Optional.of(sequenceEntity)).when(sequenceRepository).findByDay(day);
    String loanCode =
        ReflectionTestUtils.invokeMethod(
            loanApplicationService, "getLoanCode", ClientTypeEnum.LDP.toString());

    assertEquals(loanCode, String.format("%s%s%s", "LDP", intToString(day, 8), "0021"));
  }

  @Test
  @DisplayName("LoanApplicationService Test RECOVER function success")
  void givenValidInput_ThenRecover_shouldReturnSuccess() {
    Exception exception = new ApplicationException();
    String result =
        ReflectionTestUtils.invokeMethod(
            loanApplicationService, "recover", exception);
    assertEquals(result, null);
  }
}