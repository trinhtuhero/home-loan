package vn.com.msb.homeloan.api.controllers.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSAssetEvaluateResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSBusinessIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSCommonIncomeRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSContactPersonInfoMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSContactPersonRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSContactPersonResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSCreditCardResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanApplicationResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanApplicationReviewResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanPayerRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanPayerResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSMarriedPersonResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherEvaluateResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSOtherIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSSalaryIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsLoanApplicationExportExcelRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsLoanApplicationSearchRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CommonIncomeResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationItemResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCommonIncomeRequest;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationExportExcelRequest;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationSearchRequest;
import vn.com.msb.homeloan.api.dto.request.CMSLoanInitializeRequest;
import vn.com.msb.homeloan.api.dto.request.CMSLoanPayerAndContactPersonRequest;
import vn.com.msb.homeloan.api.dto.request.CMSLoanUploadStatusRequest;
import vn.com.msb.homeloan.api.dto.request.CloseLoanApplicationRequest;
import vn.com.msb.homeloan.api.dto.request.RMUpdateCustomerInfoRequest;
import vn.com.msb.homeloan.api.dto.request.SubmitFeedbackRequest;
import vn.com.msb.homeloan.api.dto.response.CMSAssetEvaluateResponse;
import vn.com.msb.homeloan.api.dto.response.CMSBusinessIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.CMSCreditCardResponse;
import vn.com.msb.homeloan.api.dto.response.CMSCustomerAndRelatedPersonInfoResponse;
import vn.com.msb.homeloan.api.dto.response.CMSIncomeInfoResponse;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.CMSLoanPayerAndContactPersonResponse;
import vn.com.msb.homeloan.api.dto.response.CMSOtherEvaluateResponse;
import vn.com.msb.homeloan.api.dto.response.CMSOtherIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.CMSSalaryIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.loanApplication.CommonIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.loanApplication.LoanApplicationResponse;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CMSCustomerAndRelatedPersonInfo;
import vn.com.msb.homeloan.core.model.CMSLoanApplicationReview;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearch;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.model.IncomeInfo;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanPayerService;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.core.util.export.ExcelExporter;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/loan-application")
@RequiredArgsConstructor
public class CMSLoanApplicationController {

  private final LoanApplicationService loanApplicationService;

  private final LoanPayerService loanPayerService;

  private final ContactPersonService contactPersonService;

  private final CommonIncomeService commonIncomeService;

  @GetMapping("/{loanUploadFileId}/pre-zip-file")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_PRE_ZIP_FILE
          + "')")
  public ResponseEntity<ApiResource> preZipFile(@PathVariable String loanUploadFileId) {
    DownloadPresignedUrlResponse response = loanApplicationService.preZipFile(loanUploadFileId);
    ApiResource apiResource = new ApiResource(response.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/search")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_SEARCH
          + "')")
  public ResponseEntity<ApiResource> search(@RequestBody CMSLoanApplicationSearchRequest request,
      HttpServletRequest httpRequest) throws IOException {
    CmsLoanApplicationSearch result = loanApplicationService.cmsSearch(
        CmsLoanApplicationSearchRequestMapper.INSTANCE.toModel(request), httpRequest);
    ApiResource apiResource = new ApiResource(result, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/{loan_id}/{key}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_UPDATE_RM_FOR_LOAN + "')")
  public ResponseEntity<ApiResource> updateRMForLoan(@PathVariable("loan_id") String loanId,
      @PathVariable("key") String key) throws NoSuchAlgorithmException {
    LoanApplication loanApplication = loanApplicationService.updateRmForLoan(loanId, key);
    ApiResource apiResource = new ApiResource(loanApplication, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/close")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_CLOSE
          + "')")
  public ResponseEntity<ApiResource> closeLoanApplication(
      @RequestBody CloseLoanApplicationRequest request) {
    loanApplicationService.closeLoanApplication(request.getUuid(), request.getCause());
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/export")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_EXPORT
          + "')")
  public ResponseEntity<byte[]> exportToExcel(
      @RequestBody CMSLoanApplicationExportExcelRequest request, HttpServletRequest httpRequest)
      throws IOException {
    ExcelExporter excelExporter = loanApplicationService.export(
        CmsLoanApplicationExportExcelRequestMapper.INSTANCE.toModel(request), httpRequest);
    byte[] bytes = excelExporter.export();
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
    String currentDateTime = dateFormatter.format(new Date());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=LoanApplicationInfo_" + currentDateTime + ".xlsx")
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(bytes);
  }

  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_DETAIL
          + "')")
  @GetMapping("/{loanApplicationId}" +
      "/detail")
  public ResponseEntity<ApiResource> detail(@PathVariable String loanApplicationId) {
    CMSLoanApplicationReview review = loanApplicationService.cmsReviewAndFile(loanApplicationId);
    ApiResource apiResource = new ApiResource(
        CMSLoanApplicationReviewResponseMapper.INSTANCE.toDTO(review), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/export/proposal-letter")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_EXPORT_PROPOSAL_LETTER + "')")
  public ResponseEntity<byte[]> exportProposalLetter(@PathVariable("loanId") String loanId) {
    Map map = loanApplicationService.exportProposalLetter(loanId);
    String fileName = (String) map.get("report-name");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
    headers.setContentDispositionFormData(fileName, fileName);
    headers.set("report-name", fileName);
    return new ResponseEntity<>((byte[]) map.get("data"), headers, HttpStatus.OK);
  }

  @PostMapping("/submit-feedback")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_SUBMIT_FEEDBACK + "')")
  public ResponseEntity<ApiResource> submitFeedback(
      @Valid @RequestBody SubmitFeedbackRequest request) throws SQLException {
    loanApplicationService.submitFeedback(request.getLoanApplicationId());
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/zip-loans")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_ZIP_LOANS
          + "')")
  public ResponseEntity<ApiResource> zipLoans(@RequestBody List<String> list) {
    loanApplicationService.zipLoans(list);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping("/customer")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_UPDATE_CUSTOMER_INFO + "')")
  public ResponseEntity<ApiResource> updateCustomer(
      @Valid @RequestBody RMUpdateCustomerInfoRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    ApiResource apiResource = new ApiResource(
        loanApplicationService.makeProposalCustomerInfo(
            LoanApplicationMapper.INSTANCE.toLoanApplication(request)), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/customer")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_UPDATE_CUSTOMER_INFO + "')")
  public ResponseEntity<ApiResource> createCustomer(
      @Valid @RequestBody RMUpdateCustomerInfoRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    ApiResource apiResource = new ApiResource(
        loanApplicationService.save(LoanApplicationMapper.INSTANCE.toLoanApplication(request),
            ClientTypeEnum.CMS), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/regenerate-dnvv")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_REGENERATE_DNVV + "')")
  public ResponseEntity<ApiResource> regenerateDNVV(@PathVariable String loanId) {
    loanApplicationService.regenerateDNVV(loanId);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/customer/{loanId}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_CUSTOMER_AND_RELATED_PERSON + "')")
  public ResponseEntity<ApiResource> getCustomerAndRelatedPersonInfo(
      @PathVariable("loanId") String loanId) {
    CMSCustomerAndRelatedPersonInfo cmsCustomerAndRelatedPersonInfo = loanApplicationService.getCustomerAndRelatedPersonInfo(
        loanId);
    CMSCustomerAndRelatedPersonInfoResponse response = new CMSCustomerAndRelatedPersonInfoResponse();
    response.setCustomerInfo(CMSLoanApplicationResponseMapper.INSTANCE.toDTO(
        cmsCustomerAndRelatedPersonInfo.getCustomerInfo()));
    response.setMarriedPerson(CMSMarriedPersonResponseMapper.INSTANCE.toDto(
        cmsCustomerAndRelatedPersonInfo.getMarriedPerson()));
    response.setContactPerson(CMSContactPersonInfoMapper.INSTANCE.toModel(
        cmsCustomerAndRelatedPersonInfo.getContactPerson()));
    response.setLoanPayers(CMSLoanPayerResponseMapper.INSTANCE.toResponses(
        cmsCustomerAndRelatedPersonInfo.getLoanPayers()));
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }


  @PatchMapping("/{loanId}/update-status")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_UPDATE_STATUS + "')")
  public ResponseEntity<ApiResource> updateStatus(@PathVariable("loanId") String loanId,
      @RequestBody CMSLoanUploadStatusRequest request) throws JsonProcessingException {
    String status = loanApplicationService.updateStatus(loanId, request.getStatus(),
        request.getNote(), ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("status", status);
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/incomes")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_LOAN_APPLICATION_GET_INCOME_INF0 + "')")
  public ResponseEntity<ApiResource> getIncomeInfo(@PathVariable String loanId,
      @RequestParam(required = false) String type) {
    List<CMSSalaryIncomeResponse> salaryIncomes = null;
    List<CMSBusinessIncomeResponse> businessHouseholdIncomes = null;
    List<CMSBusinessIncomeResponse> businessEnterpriseIncomes = null;
    List<CMSOtherIncomeResponse> otherIncomes = null;
    List<CMSOtherEvaluateResponse> otherEvaluates = null;
    CMSAssetEvaluateResponse cmsAssetEvaluate = null;

    IncomeInfo model = loanApplicationService.getIncomeInfo(loanId, type);

    LoanApplicationResponse loanApplication = LoanApplicationResponseMapper.INSTANCE.toDTO(
        model.getLoanApplication());

    if (type == null || CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode().equals(type)) {
      cmsAssetEvaluate = CMSAssetEvaluateResponseMapper.INSTANCE.toCmsResponse(
          model.getAssetEvaluate());
    }
    if (type == null || CMSTabEnum.SALARY_INCOME.getCode().equals(type)) {
      salaryIncomes = CMSSalaryIncomeResponseMapper.INSTANCE.toDTOs(model.getSalaryIncomes());
    }
    if (type == null || CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode().equals(type)) {
      businessHouseholdIncomes = CMSBusinessIncomeResponseMapper.INSTANCE.toCmsResponses(
          model.getBusinessHouseholdIncomes());
    }
    if (type == null || CMSTabEnum.BUSINESS_INCOME.getCode().equals(type)) {
      businessEnterpriseIncomes = CMSBusinessIncomeResponseMapper.INSTANCE.toCmsResponses(
          model.getBusinessEnterpriseIncomes());
    }
    if (type == null || CMSTabEnum.OTHERS_INCOME.getCode().equals(type)) {
      otherIncomes = CMSOtherIncomeResponseMapper.INSTANCE.toCmsResponses(model.getOtherIncomes());
    }
    if (type == null || CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode().equals(type)) {
      otherEvaluates = CMSOtherEvaluateResponseMapper.INSTANCE.toCmsResponses(
          model.getOtherEvaluates());
    }

    CommonIncomeResponse commonIncome = CommonIncomeResponseMapper.INSTANCE.toResponse(
        model.getCommonIncome());

    List<CMSLoanApplicationItemResponse> loanApplicationItems = LoanApplicationItemResponseMapper.INSTANCE.toCmsResponse(
        model.getLoanApplicationItems());

    List<CMSCreditCardResponse> creditCards = CMSCreditCardResponseMapper.INSTANCE.toCmsResponses(
        model.getCreditCards());

    CMSIncomeInfoResponse incomeInfoResponse = new CMSIncomeInfoResponse(salaryIncomes,
        businessHouseholdIncomes,
        businessEnterpriseIncomes,
        otherIncomes,
        cmsAssetEvaluate,
        loanApplication,
        otherEvaluates,
        commonIncome,
        loanApplicationItems,
        creditCards);
    ApiResource apiResource = new ApiResource(incomeInfoResponse, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}/mobio-link")
  public ResponseEntity<ApiResource> getMobioLink(@PathVariable String loanId) {
    String mobioLink = loanApplicationService.getMobioLink(loanId);
    Map map = new HashMap();
    map.put("mobio_link", mobioLink);
    ApiResource apiResource = new ApiResource(map, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/check-phone/{phone}")
  public ResponseEntity<ApiResource> checkPhone(@PathVariable String phone) {
    String loanId = loanApplicationService.checkPhone(phone);
    Map map = new HashMap();
    if (StringUtils.isEmpty(loanId)) {
      map.put("existed", false);
    } else {
      map.put("existed", true);
    }
    ApiResource apiResource = new ApiResource(map, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/initialize/{phone}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_APPLICATION_ADD_NEW
          + "')")
  public ResponseEntity<ApiResource> loanInitialize(@PathVariable String phone,
      @RequestBody CMSLoanInitializeRequest request) {
    Map map = loanApplicationService.loanInitialize(phone, request.isCopyLoan());
    ApiResource apiResource = new ApiResource(map, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/loan-payer-and-contact-person")
  public ResponseEntity<ApiResource> insertOrUpdateLoanPayerAndContactPerson(
      @Valid @RequestBody CMSLoanPayerAndContactPersonRequest request) {
    List<LoanPayer> loanPayers = loanPayerService.save(
        CMSLoanPayerRequestMapper.INSTANCE.toModels(request.getLoanPayers()));
    ContactPerson contactPerson = contactPersonService.save(
        CMSContactPersonRequestMapper.INSTANCE.toModel(request.getContactPerson()),
        ClientTypeEnum.CMS);
    CMSLoanPayerAndContactPersonResponse response = CMSLoanPayerAndContactPersonResponse.builder()
        .loanPayerResponses(CMSLoanPayerResponseMapper.INSTANCE.modelToResponses(loanPayers))
        .contactPersonResponse(CMSContactPersonResponseMapper.INSTANCE.toResponse(contactPerson))
        .build();
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/income-common")
  public ResponseEntity<ApiResource> save(@Valid @RequestBody CMSCommonIncomeRequest request) {
    CommonIncome commonIncome = CMSCommonIncomeRequestMapper.INSTANCE.toModel(request);
    ApiResource apiResource = new ApiResource(commonIncomeService.save(commonIncome),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping(value = "/income-common/{loanId}")
  public ResponseEntity get(@PathVariable String loanId) throws JsonProcessingException {
    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

    CommonIncomeResponse response = CommonIncomeResponseMapper.INSTANCE.toResponse(commonIncome);

    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());

    return new ResponseEntity<>(apiResource, HttpStatus.OK);
  }
}
