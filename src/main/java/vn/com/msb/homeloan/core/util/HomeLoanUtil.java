package vn.com.msb.homeloan.core.util;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.core.constant.ApprovalFlowEnum;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodCWIEnum;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;
import vn.com.msb.homeloan.core.model.District;
import vn.com.msb.homeloan.core.model.FileConfig;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.Province;
import vn.com.msb.homeloan.core.model.Ward;
import vn.com.msb.homeloan.core.model.download.ZipBlock;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.service.CollateralService;
import vn.com.msb.homeloan.core.service.DistrictService;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;
import vn.com.msb.homeloan.core.service.ProvinceService;
import vn.com.msb.homeloan.core.service.WardService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@Slf4j
@Component
@AllArgsConstructor
public class HomeLoanUtil {

  private final EnvironmentProperties environmentProperties;

  private final FileService fileService;

  private final ProvinceService provinceService;

  private final DistrictService districtService;

  private final WardService wardService;

  private final RestTemplateUtil restTemplateUtil;
  public static final ScriptEngineManager manager = new ScriptEngineManager();
  public static final ScriptEngine engine = manager.getEngineByName("JavaScript");

  private final CacheManager cacheManager;

  private final LoanApplicationItemService loanApplicationItemService;
  private final CollateralService collateralService;

  public static String getZipNameForLoanApplication(LoanApplication loanApplication) {
    String zipName = null;
    try {
      String fullName = loanApplication.getFullName();
      // Remove whitespace
      fullName = fullName.replaceAll("\\s+", "");
      fullName = removeAccent(fullName).toUpperCase();
      fullName = fullName.replaceAll("Đ", "D");
      // Remove special characters
      fullName = fullName.replaceAll("[^a-zA-Z0-9]", "");
      zipName = fullName + "_" + loanApplication.getIdNo() + ".zip";
    } catch (Exception ex) {
      log.error("[LoanApplicationUtil] getZipNameForLoanApplication error: ", ex);
    }
    return zipName;
  }

  public static String getZipNameForBlock(ZipBlock zipBlock) {
    String zipPrefix = zipBlock.getDownloadFileList().get(0).getZipPrefix();
    String zipName = zipPrefix + "_" + getOrderNumberFormat(zipBlock.getOrderNumber()) + ".zip";
    return zipName;
  }

  public static String removeAccent(String s) {
    String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    temp = pattern.matcher(temp).replaceAll("");
    return temp.replaceAll("đ", "d");
  }

  public static String getOrderNumberFormat(int number) {
    return String.format("%03d", number);
  }

  public static byte[] exportFilePdf(String loanApplicationId, String htmlContent) {

    htmlContent = Normalizer.normalize(htmlContent, Normalizer.Form.NFC);

    ConverterProperties converterProperties = new ConverterProperties();

    FontProvider fontProvider = new DefaultFontProvider(false, false, false);
    fontProvider.addFont("/font/times.ttf");
    fontProvider.addFont("/font/timesbd.ttf");
    fontProvider.addFont("/font/timesbi.ttf");
    fontProvider.addFont("/font/timesi.ttf");

    converterProperties.setFontProvider(fontProvider);
    converterProperties.setImmediateFlush(false);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(byteArrayOutputStream);
    PdfDocument pdfDocument = new PdfDocument(writer);
    pdfDocument.setDefaultPageSize(PageSize.A4);
    Document document = HtmlConverter.convertToDocument(htmlContent, pdfDocument,
        converterProperties);
    if (document != null) {
      document.close();
    }
    return byteArrayOutputStream.toByteArray();
  }

  public byte[] downloadFileFromS3(String fullPath) {
    ResponseEntity<byte[]> downloadFileResponse;
    DownloadPresignedUrlRequest downloadPresignedUrlRequest = DownloadPresignedUrlRequest.builder()
        .clientCode(environmentProperties.getClientCode())
        .scopes(environmentProperties.getClientScopes())
        .path(fullPath)
        .build();
    DownloadPresignedUrlResponse downloadPresignedUrlResponse = fileService.generatePreSignedUrlToDownload(
        downloadPresignedUrlRequest);

    downloadFileResponse = this.restTemplateUtil.requestDownloadFile(
        downloadPresignedUrlResponse.getData().getUrl(),
        HttpMethod.GET, HttpEntity.EMPTY, byte[].class);

    if (downloadFileResponse == null) {
      return null;
    }
    return downloadFileResponse.getBody();
  }

  public static boolean evalExpression(HashMap<String, Object> hm, String exp) {
    try {
      for (String key : hm.keySet()) {
        if (hm.get(key) instanceof Float || hm.get(key) instanceof Long || hm.get(
            key) instanceof Integer) {
          exp = exp.replaceAll(key, String.valueOf(hm.get(key)));
        }
        if (hm.get(key) instanceof String) {
          exp = exp.replaceAll(key, "'" + hm.get(key) + "'");
        }
        if (hm.get(key) == null) {
          exp = exp.replaceAll(key, "'" + null + "'");
        }
      }

      log.info("evalExpression: {}", exp);

      Object result = engine.eval(exp);
      if (result instanceof Boolean && (Boolean) result == true) {
        return true;
      }
    } catch (Exception ex) {

    }
    return false;
  }

  public static void totalIncome(List<SalaryIncomeEntity> salaryIncomes,
      List<BusinessIncomeEntity> businessIncomes, List<OtherIncomeEntity> otherIncomes,
      AssetEvaluateEntity assetEvaluate, List<OtherEvaluateEntity> otherEvaluates,
      LoanApplication loanApplication) {
    loanApplication.setTotalIncome(
        emptyIfNull(salaryIncomes).stream().filter(salaryIncome -> salaryIncome.getValue() != null)
            .mapToLong(salaryIncome -> salaryIncome.getValue()).sum()
            + emptyIfNull(businessIncomes).stream()
            .filter(businessIncome -> businessIncome.getValue() != null)
            .mapToLong(businessIncome -> businessIncome.getValue()).sum()
            + emptyIfNull(otherIncomes).stream()
            .filter(otherIncome -> otherIncome.getValue() != null)
            .mapToLong(otherIncome -> otherIncome.getValue()).sum());
    loanApplication.setTotalIncomeExchange(
        (assetEvaluate != null && assetEvaluate.getRmInputValue() != null
            ? assetEvaluate.getRmInputValue() : 0)
            + emptyIfNull(otherEvaluates).stream()
            .filter(otherEvaluate -> otherEvaluate.getRmInputValue() != null)
            .mapToLong(otherEvaluate -> otherEvaluate.getRmInputValue()).sum());
  }

  // Tính tổng nghĩa vụ trả nợ
  public static Long computeTotalOfDebits(List<CreditworthinessItem> creditworthinessItems) {
    Long value = 0L;

    for (CreditworthinessItem creditworthinessItem : creditworthinessItems) {

      if (creditworthinessItem.getMonthlyDebtPayment() == null
          && creditworthinessItem.getMonthlyDebtPaymentAuto() == null) {
        return null;
      }

      if (creditworthinessItem.getMonthlyDebtPayment() != null) {
        value += creditworthinessItem.getMonthlyDebtPayment();
      } else if (creditworthinessItem.getMonthlyDebtPaymentAuto() != null) {
        value += creditworthinessItem.getMonthlyDebtPaymentAuto();
      }
    }
    return value;
  }

  // DTI = Tổng nghĩa vụ trả nợ/Tổng thu nhập * 100
  public static Double computeDTI(Long totalOfDebits, Long totalIncomes) {

      if (totalOfDebits == null || totalIncomes == null) {
          return null;
      }

    return totalIncomes != 0 ? (double) totalOfDebits / totalIncomes * 100 : 0;
  }

  // LTV = Số tiền vay * 100 /(Tổng Giá trị Tài sản bảo đảm - Tổng Giá trị đã bảo đảm)
  public static double computeLTV(long loanAmount, long totalValues, long totalGuaranteedValues) {
    return totalValues - totalGuaranteedValues != 0 ?
        (double) loanAmount / (totalValues - totalGuaranteedValues) * 100 : 0;
  }

  public double computeLTV(String loanId) {
    // totalValues
    // hold on -> Ok
    long totalLoanAmount = loanApplicationItemService.totalLoanAmount(loanId);

    long totalValues = collateralService.totalValues(loanId);
    long totalGuaranteedValues = collateralService.totalGuaranteedValues(loanId);

    // LTV
    double ltv = HomeLoanUtil.computeLTV(totalLoanAmount, totalValues, totalGuaranteedValues);
    BigDecimal ltvBD = BigDecimal.valueOf(ltv).setScale(2, RoundingMode.HALF_UP);
    double roundLtv = ltvBD.doubleValue();

    return roundLtv;
  }

  // Set name by code of province
  public String getProvinceNameByCode(String code) {
    Province province = provinceService.findByCode(code);
    return province != null ? province.getName() : null;
  }

  // Set name by code of district
  public String getDistrictNameByCode(String code) {
    District district = districtService.findByCode(code);
    return district != null ? district.getName() : null;
  }

  // Set name by code of ward
  public String getWardNameByCode(String code) {
    Ward ward = wardService.findByCode(code);
    return ward != null ? ward.getName() : null;
  }

  public static List<FileConfig> applyRulesToIncomeFiles(CommonIncome commonIncome,
      List<FileConfig> fileConfigs) {

      if (commonIncome == null) {
          return fileConfigs;
      }

    // luồng ưu tiên, kê khai
    if (ApprovalFlowEnum.PRIORITY.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.DECLARATION.equals(commonIncome.getRecognitionMethod1())) {
      for (FileConfig fileConfig : fileConfigs) {
        if (Constants.ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())) {
          fileConfig.setRequire(false);
        }
      }
      fileConfigs.removeIf(fileConfig -> Constants.EXCHANGE_DIRECT_CATEGORY_CODES.contains(
          fileConfig.getFileConfigCategory().getCode()));
    }

    // luồng ưu tiên, quy đổi
    if (ApprovalFlowEnum.PRIORITY.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())) {
      for (FileConfig fileConfig : fileConfigs) {
        if (Constants.ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())
            || Constants.EXCHANGE_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())) {
          fileConfig.setRequire(false);
        }
      }
    }

    // luồng ưu tiên, thực nhận
    if (ApprovalFlowEnum.PRIORITY.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(commonIncome.getRecognitionMethod1())) {
      for (FileConfig fileConfig : fileConfigs) {
        if (Constants.ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())
            || Constants.EXCHANGE_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())) {
          fileConfig.setRequire(false);
        }
      }
    }

    // luồng nhanh, quy đổi
    if (ApprovalFlowEnum.FAST.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())) {
      for (FileConfig fileConfig : fileConfigs) {
        if (Constants.ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())) {
          fileConfig.setRequire(false);
        }
      }
    }

    // luồng nhanh, thực nhận
    if (ApprovalFlowEnum.FAST.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(commonIncome.getRecognitionMethod1())) {
      fileConfigs.removeIf(fileConfig -> Constants.EXCHANGE_DIRECT_CATEGORY_CODES.contains(
          fileConfig.getFileConfigCategory().getCode()));
    }

    // luồng thông thường, quy đổi
    if (ApprovalFlowEnum.NORMALLY.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())) {
      for (FileConfig fileConfig : fileConfigs) {
        if (Constants.ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES.contains(
            fileConfig.getFileConfigCategory().getCode())) {
          fileConfig.setRequire(false);
        }
      }
    }

    // luồng thông thường, thực nhận
    if (ApprovalFlowEnum.NORMALLY.equals(commonIncome.getApprovalFlow())
        && RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(commonIncome.getRecognitionMethod1())) {
      fileConfigs.removeIf(fileConfig -> Constants.EXCHANGE_DIRECT_CATEGORY_CODES.contains(
          fileConfig.getFileConfigCategory().getCode()));
    }
    return fileConfigs;
  }

  public boolean containRole(String role) {
    try {
      return ((List<Role>) cacheManager.getCache("rolesCache").get(AuthorizationUtil.getEmail())
          .get()).stream().map(Role::getName).collect(Collectors.toList()).contains(role);
    } catch (Exception ex) {
      log.error("containRole error: {}", ex.getMessage());
      return false;
    }
  }

  public static <T extends Enum<T>, E extends Enum<E>> T convertEnum(Class<T> to, E from) {
    if (from.getDeclaringClass() == DebtPaymentMethodEnum.class
        && to == DebtPaymentMethodCWIEnum.class) {
      String code = ((DebtPaymentMethodEnum) from).getCode();
      return (T) DebtPaymentMethodCWIEnum.valueOf(code);
    }
    return null;
  }

  public static int getCollateralIndex(String collateralId, List<Collateral> collaterals) {
    if (CollectionUtils.isEmpty(collaterals) || StringUtils.isEmpty(collateralId)) {
      return 0;
    }
    try {
      int i = IntStream.range(0, collaterals.size())
          .filter(index -> collateralId.equals(collaterals.get(index).getUuid()))
          .findFirst()
          .getAsInt();

      return i + 1;
    } catch (Exception ex) {
      return 0;
    }
  }

  public static <T, K> boolean compare(T obj1, K obj2) {
    if ((obj1 == null && obj2 instanceof String) || (obj2 == null && obj1 instanceof String)) {
      String str1 = (String) obj1;
      String str2 = (String) obj2;
      if (str1 == null && str2 != null && str2.trim().isEmpty()) {
        return true;
      }
      if (str2 == null && str1 != null && str1.trim().isEmpty()) {
        return true;
      }
    }
    return Objects.equals(obj1, obj2);
  }

  public static Collateral getById(List<Collateral> collaterals, String id) {
    if (CollectionUtils.isEmpty(collaterals)) {
      return null;
    }
    List<Collateral> temp = collaterals.stream().filter(x -> x.getUuid().equalsIgnoreCase(id))
        .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(temp)) {
      return null;
    }
    Collateral collateral = temp.get(0);
    int index = HomeLoanUtil.getCollateralIndex(id, collaterals);
    collateral.setIndex(index);
    return collateral;
  }
}
