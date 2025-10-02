package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.client.InsuranceClient;
import vn.com.msb.homeloan.core.client.InsuranceClient2;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.PicRmHistoryEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.request.integration.cj4.CalculateInsuranceRequest;
import vn.com.msb.homeloan.core.model.request.integration.cj4.SendLeadInfoRequest;
import vn.com.msb.homeloan.core.model.request.integration.cj4.UpdateLeadRequest;
import vn.com.msb.homeloan.core.model.response.cj4.CJ4Response;
import vn.com.msb.homeloan.core.model.response.cj4.CalculateInsurance;
import vn.com.msb.homeloan.core.model.response.cj4.GetLifeInfo;
import vn.com.msb.homeloan.core.model.response.cj4.ProductInfo;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.PicRmHistoryRepository;
import vn.com.msb.homeloan.core.service.InsuranceService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.SHA256EncryptUtil;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

  private final LoanApplicationRepository loanApplicationRepository;

  private final LoanApplicationService loanApplicationService;

  private final InsuranceClient insuranceClient;

  private final InsuranceClient2 insuranceClient2;

  private final EnvironmentProperties environmentProperties;

  private final HomeLoanUtil homeLoanUtil;

  private final PicRmHistoryRepository picRmHistoryRepository;

  @Override
  public boolean sendLead(String loanId, String emplId) throws ParseException, IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    log.info("Loan Application Status when send lead cj4: {}", loanApplicationEntity.getStatus());

    // token
    // String raw = request.getRequestDate() + request.getLoanId() + request.getPhoneNumber() + request.getIsInterested() + request.getEmployeeId();
    Date requestDate = DateUtils.getCurrentUtcTime();
    String hash = SHA256EncryptUtil.createCJ4Token(
        environmentProperties.getCj4Secret(),
        String.format("%s%s%s%s%s%s"
            , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss")
            , loanApplicationEntity.getLoanCode()
            , loanApplicationEntity.getPhone()
            , 0
            , emplId
            , environmentProperties.getCj4Channel()
        )
    );

    // build request
    SendLeadInfoRequest sendLeadInfoRequest = SendLeadInfoRequest.builder()
        .fullName(loanApplicationEntity.getFullName())
        .phoneNumber(loanApplicationEntity.getPhone())
        .email(loanApplicationEntity.getEmail())
        .address(loanApplicationEntity.getAddress())
        // 16/02/1994
        .dob(DateUtils.convertToSimpleFormat(loanApplicationEntity.getBirthday(), "dd/MM/yyyy"))
        .idNumber(loanApplicationEntity.getIdNo())
        // 04/05/2021
        .idIssuedDate(
            DateUtils.convertToSimpleFormat(loanApplicationEntity.getIssuedOn(), "dd/MM/yyyy"))
        .idIssuedPlace(loanApplicationEntity.getPlaceOfIssue())
        .gender(loanApplicationEntity.getGender().getCode())
        .monthlyIncome(
            loanApplicationService.totalIncomeActuallyReceived(loanApplicationEntity.getUuid()))
        .maritalStatus(loanApplicationEntity.getMaritalStatus().getCode())
        .isInterested(0)
        .isReferral(InterestedEnum.INTERESTED.equals(loanApplicationEntity.getCj4CmsInterested()) ? 1 : 0)
        .numberOfDependents(loanApplicationEntity.getNumberOfDependents())
        .provinceId(loanApplicationEntity.getProvince())
        .districtId(loanApplicationEntity.getDistrict())
        .wardId(loanApplicationEntity.getWard())
        .loanId(loanApplicationEntity.getLoanCode())
        .requestDate(DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss"))
        .employeeId(emplId)
        .channel(environmentProperties.getCj4Channel())
        .token(hash)
        .build();

    // call api
    boolean isSuccess;
    try {
      ResponseEntity<CJ4Response> response = insuranceClient.sendLead(sendLeadInfoRequest);
      log.info("CJ4 response: {}", response.getBody());
      if (response.getStatusCodeValue() == 200) {
        isSuccess = true;
      } else {
        isSuccess = false;
      }
    } catch (Exception ex) {
      log.error("Send lead to CJ4 error: ", ex);
      isSuccess = false;
    }

    if (isSuccess) {
      loanApplicationRepository.updateCj4SendLeadStatus(
          "SUCCESS", new Date(), loanApplicationEntity.getUuid());
      return true;
    } else {
      loanApplicationRepository.updateCj4SendLeadStatus(
          "FAILED", new Date(), loanApplicationEntity.getUuid());
    }
    return false;
  }

  @Override
  public String getLink(String loanId) throws ParseException, IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    // token
    // requestDate + loanId
    Date requestDate = DateUtils.getCurrentUtcTime();
    String hash = SHA256EncryptUtil.createCJ4Token(
        environmentProperties.getCj4Secret(),
        String.format("%s%s%s"
            , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss")
            , loanApplicationEntity.getLoanCode()
            , environmentProperties.getCj4Channel()
        )
    );

    ResponseEntity<CJ4Response<String>> response = insuranceClient.getLink(
        loanApplicationEntity.getLoanCode(),
        DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss"),
        environmentProperties.getCj4Channel(), hash);

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      return response.getBody().getData();
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
          response.getBody().getMessage());
    }
  }

  @Override
  @Transactional
  public boolean updateLead(String loanId, String emplId, InterestedEnum status, String channel)
      throws ParseException, IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    // Lấy rm đầu tiên
    PicRmHistoryEntity firstPicEntity = picRmHistoryRepository.findFirstPic(loanId);
    if (firstPicEntity != null) {
      emplId = !StringUtils.isEmpty(firstPicEntity.getPicFrom()) ? firstPicEntity.getPicFrom()
          : firstPicEntity.getPicTo();
    } else {
      emplId = loanApplicationEntity.getPicRm();
    }

    // token
    // String raw = request.getRequestDate() + request.getLoanId() + request.getIsInterested() + request.getEmployeeId();
    Date requestDate = DateUtils.getCurrentUtcTime();
    String hash = SHA256EncryptUtil.createCJ4Token(
        environmentProperties.getCj4Secret(),
        String.format("%s%s%s%s%s"
            , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss")
            , loanApplicationEntity.getLoanCode()
            , InterestedEnum.INTERESTED.equals(status) ? 1 : 0
            , emplId
            , environmentProperties.getCj4Channel()
        )
    );

    UpdateLeadRequest updateLeadRequest = UpdateLeadRequest.builder()
        .loanId(loanApplicationEntity.getLoanCode())
        .employeeId(emplId)
        .isInterested(InterestedEnum.INTERESTED.equals(status) ? 1 : 0)
        .isReferral(InterestedEnum.INTERESTED.equals(loanApplicationEntity.getCj4CmsInterested()) ? 1 : 0)
        .requestDate(DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss"))
        .channel(environmentProperties.getCj4Channel())
        .token(hash)
        .build();

    // call api
    ResponseEntity<CJ4Response> response = insuranceClient.updateLead(updateLeadRequest);

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      if (channel != null) {
        if ("LDP".equals(channel)) {
          loanApplicationEntity.setCj4Interested(status);
          loanApplicationEntity.setCj4InterestedDate(new Date());
        } else if ("CMS".equals(channel)) {
          loanApplicationEntity.setCj4CmsInterested(status);
          loanApplicationEntity.setCj4CmsInterestedDate(new Date());
        }
        loanApplicationRepository.save(loanApplicationEntity);
      }
      return true;
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
          response.getBody().getMessage());
    }
  }

  @Override
  public List<ProductInfo> getProducts(String loanId) throws IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    // call api
    ResponseEntity<CJ4Response<List<ProductInfo>>> response = insuranceClient.getProducts(
        loanApplicationEntity.getLoanCode());

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      return response.getBody().getData();
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
          response.getBody().getMessage());
    }
  }

  @Override
  public CalculateInsurance calculateInsurance(CalculateInsuranceRequest request, String loanId)
      throws ParseException, IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    // isGenIllustrate: false
    request.setDob(
        DateUtils.convertToSimpleFormat(loanApplicationEntity.getBirthday(), "dd/MM/yyyy"));
    request.setGender(mapGender(loanApplicationEntity.getGender().getCode()));
    request.setIsGenIllustrate(Constants.CJ4.IS_GENILLUSTRATE);
    request.setMonthlyIncome(loanApplicationService.totalIncome(loanId));

    // call api
    ResponseEntity<CJ4Response<CalculateInsurance>> response = insuranceClient.calculateInsurance(
        request);

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      return response.getBody().getData();
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
          response.getBody().getMessage());
    }
  }

  @Override
  public GetLifeInfo getLifeInfo(String loanId) throws ParseException, IOException {

    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                loanId)
        );

    Date requestDate = DateUtils.getCurrentUtcTime();
    // requestDate + loanId + channel
    String hash = SHA256EncryptUtil.createCJ4Token(
        environmentProperties.getCj4Secret(),
        String.format("%s%s%s"
            , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss")
            , loanApplicationEntity.getLoanCode()
            , environmentProperties.getCj4Channel()
        )
    );

    // call api
    ResponseEntity<CJ4Response<GetLifeInfo>> response = insuranceClient.getLifeInfo(
        loanApplicationEntity.getLoanCode()
        , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss"),
        environmentProperties.getCj4Channel(), hash);

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      return response.getBody().getData();
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
          response.getBody().getMessage());
    }
  }

  @Override
  @Transactional
  public boolean updateLeadCms(String loanId, InterestedEnum status)
          throws ParseException, IOException {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(loanId)
            .orElseThrow(
                    () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid",
                            loanId)
            );
    // Lấy rm đầu tiên
    String emplId;
    PicRmHistoryEntity firstPicEntity = picRmHistoryRepository.findFirstPic(loanId);
    if (firstPicEntity != null) {
      emplId = !StringUtils.isEmpty(firstPicEntity.getPicFrom()) ? firstPicEntity.getPicFrom()
              : firstPicEntity.getPicTo();
    } else {
      emplId = loanApplicationEntity.getPicRm();
    }

    // token
    // String raw = request.getRequestDate() + request.getLoanId() + request.getIsInterested() + request.getEmployeeId();
    Date requestDate = DateUtils.getCurrentUtcTime();
    String hash = SHA256EncryptUtil.createCJ4Token(
            environmentProperties.getCj4Secret(),
            String.format("%s%s%s%s%s"
                    , DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss")
                    , loanApplicationEntity.getLoanCode()
                    , InterestedEnum.INTERESTED.equals(loanApplicationEntity.getCj4Interested()) ? 1 : 0
                    , emplId
                    , environmentProperties.getCj4Channel()
            )
    );

    UpdateLeadRequest updateLeadRequest = UpdateLeadRequest.builder()
            .loanId(loanApplicationEntity.getLoanCode())
            .employeeId(emplId)
            .isInterested(InterestedEnum.INTERESTED.equals(loanApplicationEntity.getCj4Interested()) ? 1 : 0)
            .isReferral(InterestedEnum.INTERESTED.equals(status) ? 1 : 0)
            .requestDate(DateUtils.convertToSimpleFormat(requestDate, "yyMMddHHmmss"))
            .channel(environmentProperties.getCj4Channel())
            .token(hash)
            .build();

    // call api
    ResponseEntity<CJ4Response> response = insuranceClient.updateLead(updateLeadRequest);

    log.info("CJ4 response: {}", response.getBody());
    if (response.getStatusCodeValue() == 200) {
      return true;
    } else {
      throw new ApplicationException(response.getBody().getStatus(),
              response.getBody().getMessage());
    }
  }


  private Integer mapGender(String gender) {
    if (GenderEnum.MALE.equals(GenderEnum.valueOf(gender))) {
      return 1;
    } else if (GenderEnum.FEMALE.equals(GenderEnum.valueOf(gender))) {
      return 0;
    } else {
      return null;
    }
  }
}
