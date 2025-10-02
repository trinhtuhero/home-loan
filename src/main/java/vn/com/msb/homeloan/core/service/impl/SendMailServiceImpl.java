package vn.com.msb.homeloan.core.service.impl;

import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import vn.com.msb.homeloan.api.dto.request.SendMailRequest;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.api.dto.response.DataCommonResponse;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.CreditCardObjectEnum;
import vn.com.msb.homeloan.core.constant.FileRuleEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.CreditCardMapper;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.ObjectUtil;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMailServiceImpl implements SendMailService {

  private final FreeMarkerConfigurer freeMarkerConfigurer;

  private final LoanApplicationService loanApplicationService;

  private final MarriedPersonService marriedPersonService;

  private final LoanPayerService loanPayerService;

  private final ContactPersonService contactPersonService;

  private final SalaryIncomeService salaryIncomeService;

  private final BusinessIncomeService businessIncomeService;

  private final OtherIncomeService otherIncomeService;

  private final CollateralService collateralService;

  private final NotificationFeignClient notificationFeignClient;
  private final EnvironmentProperties environmentProperties;
  private final FileConfigService fileConfigService;
  private final PlaceOfIssueIdCardService placeOfIssueIdCardService;

  private final CreditCardRepository creditCardRepository;

  private final LoanStatusChangeRepository loanStatusChangeRepository;

  private final CmsUserRepository cmsUserRepository;

  private final CardTypeRepository cardTypeRepository;

  private final LoanApplicationItemRepository loanApplicationItemRepository;
  private final FileConfigCategoryService fileConfigCategoryService;

  private final LoanApplicationItemService loanApplicationItemService;

  private final NationalityRepository nationalityRepository;

  private final LoanPayerRepository loanPayerRepository;

  private final OverdraftRepository overdraftRepository;

  @Override
  public void sendMailWhenSubmitLoanSuccess(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList) {
    SendMailRequest request = new SendMailRequest();

    request.setTo(loanApplication.getEmail());
    request.setTitle("Xác nhận nộp hồ sơ đăng ký vay vốn thành công - Khách hàng "
        + loanApplication.getFullName());
    request.setContent(getSendMailWhenSubmitLoanSuccessContent(loanApplication));
    if (fileList != null && !fileList.isEmpty()) {
      request.setFiles(fileList);
    }

    if (!StringUtils.isEmpty(loanApplication.getEmail())) {
      ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
          environmentProperties.getClientCode(), request);
      log.info("response send loan application success mail: status {}, message: {}",
          response.getStatus(),
          response.getData().getMessage() == null ? "" : response.getData().getMessage());

    }
  }

  @Override
  public void sendMailWhenSubmitSubmitFeedback(LoanApplicationEntity loanApplication,
      List<LoanApplicationCommentEntity> loanApplicationCommentEntities,
      List<UploadFileCommentEntity> uploadFileCommentEntities,
      List<UploadFileStatusEntity> uploadFileStatusEntities) {
    SendMailRequest request = new SendMailRequest();

    request.setTo(loanApplication.getEmail());
    request.setTitle("Kính chào " + loanApplication.getFullName()
        + " - Yêu cầu bổ sung hồ sơ vay online tại MSB");
    request.setContent(
        getContentOfSubmitFeedbackTemplate(loanApplication, loanApplicationCommentEntities,
            uploadFileCommentEntities, uploadFileStatusEntities));

    if (!StringUtils.isEmpty(loanApplication.getEmail())) {
      ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
          environmentProperties.getClientCode(), request);
      log.info("response send loan application success mail: status {}, message: {}",
          response.getStatus(),
          response.getData().getMessage() == null ? "" : response.getData().getMessage());

    }
  }

  private String getContentOfSubmitFeedbackTemplate(LoanApplicationEntity loanApplication,
      List<LoanApplicationCommentEntity> loanApplicationCommentEntities,
      List<UploadFileCommentEntity> uploadFileCommentEntities,
      List<UploadFileStatusEntity> uploadFileStatusEntities) {
    try {
      Map<String, Object> templateModel = new HashMap<>();

      addStaticResources(templateModel);

      templateModel.put("loanApplication", loanApplication);
      templateModel.put("chiTietVayUrl",
          String.format("%s%s?loanId=%s", environmentProperties.getCj5LdpUrl(),
              environmentProperties.getChiTietVayPath(), loanApplication.getUuid()));

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/mail-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("submit_feedback_template_v2.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception e) {
      log.error("LOAN APPLICATION SUCCESS MAIL get template error: ", e);
      return null;
    }
  }

  private String getSendMailWhenSubmitLoanSuccessContent(LoanApplicationEntity loanApplication) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR_OF_DAY, 7);
    String date = DateUtils.convertToSimpleFormat(cal.getTime(), "dd/MM/yyyy HH:mm:ss");

    try {
      Map<String, Object> templateModel = new HashMap<>();

      // Static resources
      addStaticResources(templateModel);

      templateModel.put("danhSachVayUrl",
          environmentProperties.getCj5LdpUrl() + environmentProperties.getDanhSachVayPath());

      templateModel.put("name", loanApplication.getFullName());
      templateModel.put("successTime", date);
      templateModel.put("loanApplication", loanApplication);

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/mail-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("loan_application_success.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception e) {
      log.error("LOAN APPLICATION SUCCESS MAIL get template error: ", e);
      return null;
    }
  }

  public String genHtmlContentOfAttachFile(String loanApplicationId, Date signedDate) {
    try {
      Map<String, Object> templateModel = new HashMap<>();

      LoanApplication loanApplication = loanApplicationService.findById(loanApplicationId);
      loanApplication.setPlaceOfIssueName(
          placeOfIssueIdCardService.getNameByCode(loanApplication.getPlaceOfIssue()));

      List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanApplicationId);
      templateModel.put("loanApplicationItems", loanApplicationItems);

      // Static resources
      addStaticResources(templateModel);

      // Thông tin khách hàng, Thông tin khoản vay
      templateModel.put("loanApplication", loanApplication);

      // Thông tin vợ chồng khách hàng
      List<MarriedPerson> marriedPersonList = marriedPersonService.findByLoanId(loanApplicationId);
      marriedPersonList.forEach(marriedPerson -> {
        marriedPerson.setPlaceOfIssueName(
            placeOfIssueIdCardService.getNameByCode(marriedPerson.getPlaceOfIssue()));
      });
      templateModel.put("marriedPersonList", marriedPersonList);

      // Thông tin người đồng trả nợ
      List<LoanPayer> loanPayerList = loanPayerService.findByLoanIdOrderByCreatedAtAsc(
          loanApplicationId);
      loanPayerList.forEach(loanPayer -> loanPayer.setPlaceOfIssueName(
          placeOfIssueIdCardService.getNameByCode(loanPayer.getPlaceOfIssue())));
      templateModel.put("loanPayerList", loanPayerList);

      // Thông tin người liên hệ
      List<ContactPerson> contactPersonList = contactPersonService.findByLoanId(loanApplicationId);
      templateModel.put("contactPersonList", contactPersonList);

      // Thông tin Nguồn thu từ lương
      List<SalaryIncome> salaryIncomeList = salaryIncomeService.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanApplicationId);
      salaryIncomeList.forEach(salaryIncome -> {
        if (OwnerTypeEnum.ME.equals(salaryIncome.getOwnerType())) {
          salaryIncome.setPayerName(loanApplication.getFullName());
        } else if (OwnerTypeEnum.MARRIED_PERSON.equals(salaryIncome.getOwnerType())) {
          salaryIncome.setPayerName(
              !marriedPersonList.isEmpty() ? marriedPersonList.get(0).getFullName() : null);
        } else if (salaryIncome != null && !StringUtils.isEmpty(salaryIncome.getPayerId())) {
          LoanPayerEntity loanPayerEntity = loanPayerRepository.findById(salaryIncome.getPayerId())
              .orElse(null);
          salaryIncome.setPayerName(loanPayerEntity != null ? loanPayerEntity.getFullName() : null);
        }
      });
      templateModel.put("salaryIncomeList", salaryIncomeList);

      // Thông tin Nguồn thu từ doanh nghiệp/hộ kinh doanh
      List<BusinessIncome> businessIncomeList = businessIncomeService.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanApplicationId);
      businessIncomeList.forEach(businessIncome -> {
        if (OwnerTypeEnum.ME.equals(businessIncome.getOwnerType())) {
          businessIncome.setPayerName(loanApplication.getFullName());
        } else if (OwnerTypeEnum.MARRIED_PERSON.equals(businessIncome.getOwnerType())) {
          businessIncome.setPayerName(
              !marriedPersonList.isEmpty() ? marriedPersonList.get(0).getFullName() : null);
        } else if (businessIncome != null && !StringUtils.isEmpty(businessIncome.getPayerId())) {
          LoanPayerEntity loanPayerEntity = loanPayerRepository.findById(
              businessIncome.getPayerId()).orElse(null);
          businessIncome.setPayerName(
              loanPayerEntity != null ? loanPayerEntity.getFullName() : null);
        }
      });
      templateModel.put("businessIncomeList", businessIncomeList);

      // Thông tin Nguồn thu khác
      List<OtherIncome> otherIncomeList = otherIncomeService.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanApplicationId);
      otherIncomeList.forEach(otherIncome -> {
        if (OwnerTypeEnum.ME.equals(otherIncome.getOwnerType())) {
          otherIncome.setPayerName(loanApplication.getFullName());
        } else if (OwnerTypeEnum.MARRIED_PERSON.equals(otherIncome.getOwnerType())) {
          otherIncome.setPayerName(
              !marriedPersonList.isEmpty() ? marriedPersonList.get(0).getFullName() : null);
        } else if (otherIncome != null && !StringUtils.isEmpty(otherIncome.getPayerId())) {
          LoanPayerEntity loanPayerEntity = loanPayerRepository.findById(otherIncome.getPayerId())
              .orElse(null);
          otherIncome.setPayerName(loanPayerEntity != null ? loanPayerEntity.getFullName() : null);
        }
      });
      templateModel.put("otherIncomeList", otherIncomeList);

      // Tổng thu nhập của khách hàng
      templateModel.put("totalIncome",
          totalIncome(salaryIncomeList, businessIncomeList, otherIncomeList));

      // Thông tin tài sản bảo đảm
      List<Collateral> collateralList = collateralService.findByLoanIdOrderByCreatedAtAsc(
          loanApplicationId);
      templateModel.put("collateralList", collateralList);

      // Tổng giá trị tài sản bảo đảm
      templateModel.put("totalCollateralValue", totalCollateralValue(collateralList));

      // Thẻ chính
      List<CreditCardEntity> primaryCards = creditCardRepository.findByLoanIdAndCardPriority(
          loanApplicationId, CardPriorityEnum.PRIMARY_CARD);
      List<CreditCard> lsPrimaryCards = primaryCards.stream().map(CreditCardMapper.INSTANCE::toModels).collect(Collectors.toList());
      lsPrimaryCards.forEach(card -> {
        CreditCardObjectEnum cardObjectEnum = CreditCardObjectEnum.asEnum(card.getObject());
        String cardName = ObjectUtil.isEmpty(cardObjectEnum)?card.getObject():cardObjectEnum.getName();
        card.setObject(cardName);
        });
      templateModel.put("primaryCards", lsPrimaryCards);

      //
      List<String> typeStrs = primaryCards.stream()
          .map(creditCardEntity -> creditCardEntity.getType()).collect(Collectors.toList());

      // Thẻ phụ
      List<CreditCardEntity> secondaryCards = creditCardRepository.findByLoanIdAndCardPriorityOrderByCreatedAtAsc(
          loanApplicationId, CardPriorityEnum.SECONDARY_CARD);
      List<CreditCard> lsSecondaryCards = secondaryCards.stream().map(CreditCardMapper.INSTANCE::toModels).collect(Collectors.toList());
      lsSecondaryCards.forEach(card -> {
        CreditCardObjectEnum cardObjectEnum = CreditCardObjectEnum.asEnum(card.getObject());
        String cardName = ObjectUtil.isEmpty(cardObjectEnum)?card.getObject():cardObjectEnum.getName();
        card.setObject(cardName);
      });
      templateModel.put("secondaryCards", lsSecondaryCards);

      // Thấu chi
      List<OverdraftEntity> overdraftEntities = overdraftRepository.findByLoanApplicationIdOrderByCreatedAtAsc(
          loanApplicationId);
      templateModel.put("overdraft",
          !CollectionUtils.isEmpty(overdraftEntities) ? overdraftEntities.get(0) : null);

      templateModel.put("cardTypeMap", cardTypeRepository.findAllById(typeStrs).stream()
          .collect(Collectors.toMap(CardTypeEntity::getCode, Function.identity())));

      List<NationalityEntity> nationalityEntities = nationalityRepository.findAll();
      Map<String, String> map = nationalityEntities.stream()
          .collect(Collectors.toMap(NationalityEntity::getCode, NationalityEntity::getName));
      templateModel.put("nationalityMap", map);
      // Lần gửi
      int count = 0;
      if (signedDate == null) {
        count = loanStatusChangeRepository.countLoanSubmited(loanApplicationId) + 1;
      }
      templateModel.put("count", count);

      // cj5LdpUrl
      templateModel.put("linkLdp", environmentProperties.getCj5LdpUrl());

      Calendar cal =
          signedDate != null ? DateUtils.dateToCalendar(signedDate) : Calendar.getInstance();
      cal.add(Calendar.HOUR_OF_DAY, 7);
      String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));     // gets the current month
      String minute = String.valueOf(cal.get(Calendar.MINUTE));
      String second = String.valueOf(cal.get(Calendar.SECOND));
      String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
      String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
      String year = String.valueOf(cal.get(Calendar.YEAR));
      List<String> dateElements = new ArrayList<>(
          Arrays.asList(hour, minute, second, day, month, year));
      templateModel.put("dateElements", dateElements);

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/attach-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("loan_request_success.ftl");
      log.info("log genHtmlContentOfAttachFile model: {}", templateModel);
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception e) {
      log.error("LOAN APPLICATION SUCCESS MAIL get template error: ", e);
      return null;
    }
  }

  //danh sách hồ sơ cần chuẩn bị
  public String genHtmlContentDocumentNeedProvide(String loanId, FileRuleEnum fileRuleEnum) {
    try {
      Map<String, Object> templateModel = new HashMap<>();

      List<FileConfigCategory> fileConfigCategories = fileConfigCategoryService.getFileConfigCategories(
          loanId, FileRuleEnum.LDP);

      // Static resources
      addStaticResources(templateModel);

      templateModel.put("fileConfigCategories", fileConfigCategories);

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/attach-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("documents_need_provide.ftl");

      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception e) {
      log.error("LOAN APPLICATION SUCCESS MAIL get template error: ", e);
      return null;
    }
  }

  @Override
  public void sendMailWhenRMCompleteToTrinh(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList) {
    SendMailRequest request = new SendMailRequest();

    request.setTo(loanApplication.getEmail());

    if (!StringUtils.isEmpty(loanApplication.getPicRm())) {
      String ccEmail = cmsUserRepository.findById(loanApplication.getPicRm())
          .map(cmsUser -> cmsUser.getEmail()).orElse(null);
      if (!StringUtils.isEmpty(ccEmail)) {
        request.setCc(ccEmail);
      }
    }

    request.setTitle(
        "Yêu cầu Xác nhận đề nghị vay vốn - Khách hàng " + loanApplication.getFullName());
    request.setContent(buildContentFromRMCompleteToTrinhTemplate(loanApplication));
    if (fileList != null && !fileList.isEmpty()) {
      request.setFiles(fileList);
    }

    if (!StringUtils.isEmpty(loanApplication.getEmail())) {
      ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
          environmentProperties.getClientCode(), request);
      log.info("response send loan application success mail: status {}, message: {}",
          response.getStatus(),
          response.getData().getMessage() == null ? "" : response.getData().getMessage());

    }
  }

  private String buildContentFromRMCompleteToTrinhTemplate(LoanApplicationEntity loanApplication) {
    try {
      Map<String, Object> templateModel = new HashMap<>();
      // Static resources
      addStaticResources(templateModel);

      // Thông tin khách hàng, Thông tin khoản vay
      templateModel.put("loanApplication", loanApplication);

      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.HOUR_OF_DAY, 7);
      cal.add(Calendar.DATE, 1);
      String date = DateUtils.convertToSimpleFormat(cal.getTime(), "dd/MM/yyyy");
      templateModel.put("confirmBeforeDate", date);

      templateModel.put("chiTietVayUrl",
          String.format("%s%s?loanId=%s", environmentProperties.getCj5LdpUrl(),
              environmentProperties.getChiTietVayPath(), loanApplication.getUuid()));

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/mail-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("rm_complete_to_trinh_template.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception ex) {
      log.error("Get template error: ", ex);
      return null;
    }
  }

  @Override
  public void sendMailToBMWhenRMCompleteToTrinh(LoanApplicationEntity loanApplication) {
    SendMailRequest request = new SendMailRequest();

    if (!StringUtils.isEmpty(loanApplication.getPicRm())) {
      CmsUserEntity rm = cmsUserRepository.findById(loanApplication.getPicRm()).orElse(null);
      if (rm != null) {
        request.setTo(rm.getLeaderEmail());
      }
    }

    if (!StringUtils.isEmpty(request.getTo())) {
      request.setTitle("Thông báo hồ sơ cần phê duyệt - hệ thống CMS - Khách hàng "
          + loanApplication.getFullName());
      request.setContent(buidRMCompleteToTrinhNotityBMTemplate(loanApplication));
      ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
          environmentProperties.getClientCode(), request);
      log.info("response send loan application success mail: status {}, message: {}",
          response.getStatus(),
          response.getData().getMessage() == null ? "" : response.getData().getMessage());
    }
  }

  private String buidRMCompleteToTrinhNotityBMTemplate(LoanApplicationEntity loanApplication) {
    try {
      Map<String, Object> templateModel = new HashMap<>();

      // Thông tin khách hàng, Thông tin khoản vay
      templateModel.put("loanApplication", loanApplication);

      // Static resources
      addStaticResources(templateModel);

      List<LoanApplicationItemEntity> loanApplicationItems = loanApplicationItemRepository.findByLoanApplicationIdWithoutCreditCard(
          loanApplication.getUuid());
      List<CreditCardEntity> creditCards = creditCardRepository.findByLoanId(
          loanApplication.getUuid());

      if (!CollectionUtils.isEmpty(loanApplicationItems)) {
        templateModel.put("loanApplicationItems", loanApplicationItems);
      }
      if (!CollectionUtils.isEmpty(creditCards)) {
        List<CreditCardEntity> lstPrimaryCard = creditCards.stream()
            .filter(cc -> cc.getCardPriority().equals(CardPriorityEnum.PRIMARY_CARD))
            .collect(Collectors.toList());
        List<CreditCardEntity> secondaryCards = creditCards.stream()
            .filter(cc -> cc.getCardPriority().equals(CardPriorityEnum.SECONDARY_CARD))
            .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(lstPrimaryCard)) {
          templateModel.put("primaryCard", lstPrimaryCard.get(0));
        }
        if (!CollectionUtils.isEmpty(secondaryCards)) {
          templateModel.put("secondaryCards", secondaryCards);
        }
      }

      if (!StringUtils.isEmpty(loanApplication.getPicRm())) {
        CmsUserEntity rm = cmsUserRepository.findById(loanApplication.getPicRm()).orElse(null);
        if (rm != null) {
          templateModel.put("nameRM", rm.getFullName());
          CmsUserEntity bm = cmsUserRepository.findByEmail(rm.getLeaderEmail()).orElse(null);
          if (bm != null) {
            templateModel.put("nameBM", bm.getFullName());
          }
        }
      }

      URI uri = new URIBuilder(String.format("%s%s%s", environmentProperties.getCj5CmsUrl(),
          environmentProperties.getCmsLoanDetailPath(), loanApplication.getUuid()))
          .addParameter("type", "READONLY")
          .build();
      templateModel.put("chiTietVayUrl", uri.toString());

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/mail-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("rm_complete_to_trinh_notify_bm_template.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception ex) {
      log.error("Get template error: ", ex);
      return null;
    }
  }

  @Override
  public void sendMailWhenCustomerApprovalChange(LoanApplicationEntity loanApplication,
      List<MultipartFile> fileList) {
    SendMailRequest request = new SendMailRequest();

    request.setTo(loanApplication.getEmail());
    request.setTitle(
        "Xác nhận đề nghị vay vốn thành công - Khách hàng " + loanApplication.getFullName());
    request.setContent(buildContentFromCustomerApprovalChangeTemplate(loanApplication));
    if (fileList != null && !fileList.isEmpty()) {
      request.setFiles(fileList);
    }

    if (!StringUtils.isEmpty(loanApplication.getEmail())) {
      ApiInternalResponse<DataCommonResponse> response = notificationFeignClient.sendMail(
          environmentProperties.getClientCode(), request);
      log.info("response send loan application success mail: status {}, message: {}",
          response.getStatus(),
          response.getData().getMessage() == null ? "" : response.getData().getMessage());

    }
  }

  private String buildContentFromCustomerApprovalChangeTemplate(
      LoanApplicationEntity loanApplication) {
    try {
      Map<String, Object> templateModel = new HashMap<>();
      // Static resources
      addStaticResources(templateModel);

      // Thông tin khách hàng, Thông tin khoản vay
      templateModel.put("loanApplication", loanApplication);

      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.HOUR_OF_DAY, 7);
      String date = DateUtils.convertToSimpleFormat(cal.getTime(), "dd/MM/yyyy HH:mm:ss");
      templateModel.put("successTime", date);

      templateModel.put("danhSachVayUrl",
          environmentProperties.getCj5LdpUrl() + environmentProperties.getDanhSachVayPath());

      templateModel.put("numberOfWorkingDays", environmentProperties.getNumberOfWorkingDays());

      freeMarkerConfigurer.getConfiguration()
          .setClassForTemplateLoading(this.getClass(), "/mail-templates/");
      Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
          .getTemplate("customer_approval_change_template.ftl");
      return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
    } catch (Exception ex) {
      log.error("Get template error: ", ex);
      return null;
    }
  }

  private void addStaticResources(Map<String, Object> templateModel) {
    templateModel.put("headerStaticUrl", environmentProperties.getHeaderStaticUrl());
    templateModel.put("appleStoreStaticUrl", environmentProperties.getAppleStoreStaticUrl());
    templateModel.put("googlePlayStaticUrl", environmentProperties.getGooglePlayStaticUrl());
    templateModel.put("bannerStaticUrl", environmentProperties.getBannerStaticUrl());
    templateModel.put("logoStaticUrl", environmentProperties.getLogoStaticUrl());
    templateModel.put("logoPageStaticUrl", environmentProperties.getLogoPageStaticUrl());
  }

  private long totalIncome(List<SalaryIncome> salaryIncomeList,
      List<BusinessIncome> businessIncomeList, List<OtherIncome> otherIncomeList) {
    return salaryIncomeList.stream().filter(salaryIncome -> salaryIncome.getValue() != null)
        .mapToLong(salaryIncome -> salaryIncome.getValue()).sum()
        + businessIncomeList.stream().filter(businessIncome -> businessIncome.getValue() != null)
        .mapToLong(businessIncome -> businessIncome.getValue()).sum()
        + otherIncomeList.stream().filter(otherIncome -> otherIncome.getValue() != null)
        .mapToLong(otherIncome -> otherIncome.getValue()).sum();
  }

  private long totalCollateralValue(List<Collateral> collateralList) {
    return collateralList.stream().filter(collateral -> collateral.getValue() != null)
        .mapToLong(collateral -> collateral.getValue()).sum();
  }

}
