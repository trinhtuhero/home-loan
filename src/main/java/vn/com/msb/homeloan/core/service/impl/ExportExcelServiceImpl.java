package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanPurposeDetailEnum;
import vn.com.msb.homeloan.core.constant.collateral.LoanSupplementingBusinessCapitalEnum;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.entity.ProductCodeConfigEntity;
import vn.com.msb.homeloan.core.entity.ProvinceEntity;
import vn.com.msb.homeloan.core.model.*;
import vn.com.msb.homeloan.core.model.mapper.ProductCodeConfigMapper;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.repository.CreditInstitutionRepository;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.repository.ProductCodeConfigRepository;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.service.ExportExcelService;
import vn.com.msb.homeloan.core.service.ProductCodeConfigService;
import vn.com.msb.homeloan.core.util.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
public class ExportExcelServiceImpl implements ExportExcelService {

  private final PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;
  private final ProvinceRepository provinceRepository;
  private final CreditInstitutionRepository creditInstitutionRepository;
  private final ProductCodeConfigRepository productCodeConfigRepository;
  private final HomeLoanUtil homeLoanUtil;
  private final ProductCodeConfigService productCodeConfigService;

  private final int MAX_LOAN_PAYER = 2;
  private final int MAX_SALARY_INCOME = 4;
  private final int MAX_BUSINESS_INCOME = 2;
  private final int MAX_OTHER_INCOME = 2;
  private final int MAX_COLLATERAL = 5;

  private final String Address = "Address";
  private final String Province = "Province";
  private final String District = "District";
  private final String Ward = "Ward";

  @Override
  public XSSFWorkbook exportProposalLetter(CMSLoanApplicationReview data, XSSFWorkbook workbook) {
    XSSFSheet sheet = workbook.getSheet("TT");
    fillCustomerInfo(sheet, data);
    fillMarriedPerson(sheet, data);

    // hold on --> ok
    fillApprovalFlow(sheet, data);

    fillCicInfo(sheet, data);
    fillAssetEvaluate(sheet, data);
    fillOtherEvaluate(sheet, data);
    fillFieldSurvey(sheet, data);
    fillDeXuatDVKD(sheet, data);
    int indexItem = 0;
    if (!CollectionUtils.isEmpty(data.getLoanApplicationItems())) {
      int countItem = data.getLoanApplicationItems().size();
      indexItem = countItem;
      for (int i = 0; i < countItem; i++) {
        fillLoanApplicationItem(sheet, data, data.getLoanApplicationItems().get(i), 16 * i);
      }
    }

    if (!CollectionUtils.isEmpty(data.getOverdrafts())) {
      for (int i = 0; i < data.getOverdrafts().size(); i++) {
        if (data.getOverdrafts()
            .get(i)
            .getFormOfCredit()
            .equals(FormOfCreditEnum.UNSECURED_OVERDRAFT)) {
          fillOverdraftItem(sheet, data.getOverdrafts().get(i), i * 3, data);
        } else {
          fillOverdraftItem(sheet, data.getOverdrafts().get(i), indexItem * 16, data);
          indexItem++;
        }
      }
    }

    XSSFRow row = sheet.getRow(1088);
    updateCellValue(row, 14, Long.valueOf(indexItem));

    fillExceptionItem(sheet, data);
    fillCreditCard(sheet, data);
    if (!CollectionUtils.isEmpty(data.getLoanPayers())) {
      int countLoanPayer = Math.min(data.getLoanPayers().size(), MAX_LOAN_PAYER);
      row = sheet.getRow(44);
      updateCellValue(row, 14, Long.valueOf(countLoanPayer));
      for (int i = 0; i < countLoanPayer; i++) {
        fillLoanPayer(sheet, data.getLoanPayers().get(i), 13 * i, data);
      }
    } else {
      row = sheet.getRow(44);
      updateCellValue(row, 14, 0L);
    }

    // hold on --> ok
    fillIncome(sheet, data);

    if (!CollectionUtils.isEmpty(data.getSalaryIncomes())) {
      int countSalaryIncome = Math.min(data.getSalaryIncomes().size(), MAX_SALARY_INCOME);
      row = sheet.getRow(97);
      updateCellValue(row, 14, Long.valueOf(countSalaryIncome));
      for (int i = 0; i < countSalaryIncome; i++) {
        fillSalaryIncome(sheet, data.getSalaryIncomes().get(i), 11 * i, data);
      }
    } else {
      row = sheet.getRow(97);
      updateCellValue(row, 14, 0L);
    }
    if (!CollectionUtils.isEmpty(data.getBusinessIncomes())) {
      List<BusinessIncome> lstIndividual =
          data.getBusinessIncomes().stream()
              .filter(
                  x ->
                      BusinessTypeEnum.WITH_REGISTRATION
                          .getCode()
                          .equalsIgnoreCase(x.getBusinessType().getCode())
                          || BusinessTypeEnum.WITHOUT_REGISTRATION
                          .getCode()
                          .equalsIgnoreCase(x.getBusinessType().getCode()))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstIndividual)) {
        int countBusinessIncome = Math.min(lstIndividual.size(), MAX_BUSINESS_INCOME);
        row = sheet.getRow(177);
        updateCellValue(row, 14, Long.valueOf(countBusinessIncome));
        for (int i = 0; i < countBusinessIncome; i++) {
          fillBusinessIncomeIndividual(sheet, lstIndividual.get(i), 29 * i, data);
        }
      } else {
        row = sheet.getRow(177);
        updateCellValue(row, 14, 0L);
      }

      List<BusinessIncome> lstEnterprise =
          data.getBusinessIncomes().stream()
              .filter(
                  x ->
                      BusinessTypeEnum.ENTERPRISE
                          .getCode()
                          .equalsIgnoreCase(x.getBusinessType().getCode()))
              .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(lstEnterprise)) {
        int countBusinessIncome = Math.min(lstEnterprise.size(), MAX_BUSINESS_INCOME);
        row = sheet.getRow(235);
        updateCellValue(row, 14, Long.valueOf(countBusinessIncome));
        for (int i = 0; i < countBusinessIncome; i++) {
          fillBusinessIncomeEnterprise(sheet, lstEnterprise.get(i), 31 * i, data);
        }
      } else {
        row = sheet.getRow(235);
        updateCellValue(row, 14, 0L);
      }
    } else {
      row = sheet.getRow(177);
      updateCellValue(row, 14, 0L);

      row = sheet.getRow(235);
      updateCellValue(row, 14, 0L);
    }

    if (!CollectionUtils.isEmpty(data.getOtherIncomes())) {
      List<OtherIncome> otherIncomes =
          data.getOtherIncomes().stream()
              .filter(x -> !IncomeFromEnum.RENTAL_PROPERTIES.equals(x.getIncomeFrom()))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(otherIncomes)) {
        int countOtherIncome = Math.min(otherIncomes.size(), MAX_OTHER_INCOME);
        row = sheet.getRow(297);
        updateCellValue(row, 14, Long.valueOf(countOtherIncome));
        for (int i = 0; i < countOtherIncome; i++) {
          fillOthersIncome(sheet, otherIncomes.get(i), 6 * i);
        }
      }
    } else {
      row = sheet.getRow(297);
      updateCellValue(row, 14, 0L);
    }

    if (!CollectionUtils.isEmpty(data.getOtherIncomes())) {
      List<OtherIncome> otherIncomes =
          data.getOtherIncomes().stream()
              .filter(x -> x.getIncomeFrom().equals(IncomeFromEnum.RENTAL_PROPERTIES))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(otherIncomes)) {
        int countOtherIncome = Math.min(otherIncomes.size(), 4);
        row = sheet.getRow(141);
        updateCellValue(row, 14, Long.valueOf(countOtherIncome));
        for (int i = 0; i < countOtherIncome; i++) {
          fillRentalProperties(sheet, otherIncomes.get(i), 9 * i, data);
        }
      }
    } else {
      row = sheet.getRow(141);
      updateCellValue(row, 14, 0L);
    }

    if (!CollectionUtils.isEmpty(data.getCollaterals())) {
      int countCollateral = Math.min(data.getCollaterals().size(), MAX_COLLATERAL);
      row = sheet.getRow(356);
      updateCellValue(row, 14, Long.valueOf(countCollateral));
      for (int i = 0; i < countCollateral; i++) {
        fillCollateral(sheet, data.getCollaterals().get(i), 143 * i, data);
      }
      row = sheet.getRow(1071);
      // hold on

      updateCellValue(row, 7, homeLoanUtil.computeLTV(data.getLoanApplication().getUuid()) / 100);

    } else {
      row = sheet.getRow(356);
      updateCellValue(row, 14, 0L);
    }
    fillCustomerInfo(sheet, data);
    fillBangTinhDTI(data, workbook);
    fillPhanBoTSDB(data, workbook);
    return workbook;
  }

  private void fillBangTinhDTI(CMSLoanApplicationReview data, XSSFWorkbook workbook) {
    if (CollectionUtils.isEmpty(data.getCreditworthinessItems())) {
      return;
    }
    XSSFSheet sheet = workbook.getSheet("Bảng tính DTI");
    XSSFRow row = sheet.getRow(8);
    if (data.getCreditAppraisal() != null && data.getCreditAppraisal().getDti() != null) {
      updateCellValue(
          row, 18, String.valueOf(Math.round(data.getCreditAppraisal().getDti() * 100) / 100));
    }
    int startRow = 4;
    int count = 0;
    Long sum = 0L;
    for (CreditworthinessItem item : data.getCreditworthinessItems()) {
      if (count > 20) {
        return;
      }
      row = sheet.getRow(startRow + count);
      // doi tuong

      if (item.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
        int payerIndex = getLoanPayerIndex(item.getTarget(), data.getLoanPayers());
        if (payerIndex > 2) {
          continue;
        }

        updateCellValue(row, 2, String.format("%s %s", item.getOwnerType().getName(), payerIndex));
      } else {
        // Chu nguon thu la
        updateCellValue(row, 2, item.getOwnerType().getName());
      }
      if (!StringUtils.isEmpty(item.getCreditInstitution())) {
        Optional<CreditInstitutionEntity> creditInstitutionEntity =
            creditInstitutionRepository.findByCode(item.getCreditInstitution());
        if (creditInstitutionEntity.isPresent()) {
          updateCellValue(row, 3, creditInstitutionEntity.get().getShortName());
        }
      }

      if (item.getFormOfCredit() != null) {
        updateCellValue(row, 4, item.getFormOfCredit().getName());
      }

      if (item.getDebtPaymentMethod() != null) {
        updateCellValue(row, 5, item.getDebtPaymentMethod().getName());
      }

      updateCellValue(row, 6, item.getFirstLimit());

      updateCellValue(row, 7, item.getCurrentBalance());

      if (item.getFirstPeriod() != null) {
        updateCellValue(row, 8, item.getFirstPeriod().longValue());
      }

      if (item.getRemainingPeriod() != null) {
        updateCellValue(row, 9, item.getRemainingPeriod().longValue());
      }

      if (item.getInterestRate() != null) {
        updateCellValue(row, 10, item.getInterestRate().floatValue() / 100);
      }
      item.setMonthlyDebtPaymentAuto();
      if (item.getG() != null) {
        updateCellValue(row, 12, item.getG().floatValue());
      }
      if (item.getL() != null) {
        updateCellValue(row, 13, item.getL().floatValue());
      }
      if (item.getMonthlyDebtPayment() != null && item.getMonthlyDebtPayment() > 0) {
        updateCellValue(row, 14, item.getMonthlyDebtPayment());
        sum = sum + item.getMonthlyDebtPayment();
      } else {
        updateCellValue(row, 14, item.getMonthlyDebtPaymentAuto());
        sum =
            sum
                + (item.getMonthlyDebtPaymentAuto() == null
                ? 0L
                : item.getMonthlyDebtPaymentAuto());
      }

      count++;
    }

    row = sheet.getRow(24);
    updateCellValue(row, 14, sum);

    row = sheet.getRow(6);
    updateCellValue(row, 18, sum);

    row = sheet.getRow(5);
    updateCellValue(row, 18, sum);

    row = sheet.getRow(8);
    updateCellValue(row, 18, sum);

    if (data.getCreditAppraisal() != null && data.getCreditAppraisal().getDti() != null) {
      row = sheet.getRow(9);
      updateCellValue(row, 18, data.getCreditAppraisal().getDti().floatValue());
    }
  }

  private void fillPhanBoTSDB(CMSLoanApplicationReview data, XSSFWorkbook workbook) {
    if (CollectionUtils.isEmpty(data.getCollaterals())
        || (CollectionUtils.isEmpty(data.getLoanApplicationItems()) && CollectionUtils.isEmpty(
        data.getOverdrafts()))) {
      return;
    }
    XSSFSheet sheet = workbook.getSheet("Phân bổ TSBĐ");
    int startRow = 2;
    int count = 0;
    for (Collateral item : data.getCollaterals()) {
      if (count > 4) {
        return;
      }

      // Phan Bo theo %
      if (ObjectUtil.isNotEmpty(data.getLoanApplicationItems())) {
        for (int i = 0; i < data.getLoanApplicationItems().size(); i++) {
          XSSFRow row = sheet.getRow(startRow + count);
          LoanApplicationItem loanApplicationItem = data.getLoanApplicationItems().get(i);
          if (!CollectionUtils.isEmpty(
              loanApplicationItem.getLoanItemCollateralDistributions())) {
            List<LoanItemCollateralDistribution> temp =
                loanApplicationItem.getLoanItemCollateralDistributions().stream()
                    .filter(x -> item.getUuid().equalsIgnoreCase(x.getCollateralId()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(temp)) {
              updateCellValue(workbook, row, 2 + i, temp.get(0).getPercent() / 100);
            }
          }
        }
      }
      // Phan Bo theo %
      int overdraftIndex = 0;
      int size = 0;
      if (ObjectUtil.isNotEmpty(data.getLoanApplicationItems())) {
        size = data.getLoanApplicationItems().size();
      }
      if (ObjectUtil.isNotEmpty(data.getOverdrafts())) {
        for (int i = size; i < data.getOverdrafts().size() + size; i++) {
          XSSFRow row = sheet.getRow(startRow + count);
          Overdraft overdraft = data.getOverdrafts().get(overdraftIndex);
          log.info("Start distribution collateral overdraft: {}", overdraft);
          overdraftIndex++;
          if (overdraft.getFormOfCredit().equals(FormOfCreditEnum.SECURED_OVERDRAFT)) {
            if (!CollectionUtils.isEmpty(overdraft.getLoanItemCollateralDistributions())) {
              List<LoanItemCollateralDistribution> temp =
                  overdraft.getLoanItemCollateralDistributions().stream()
                      .filter(x -> item.getUuid().equalsIgnoreCase(x.getCollateralId()))
                      .collect(Collectors.toList());
              if (!CollectionUtils.isEmpty(temp)) {
                log.info("Insert to row: {}", 2 + i);
                updateCellValue(workbook, row, 2 + i, temp.get(0).getPercent() / 100);
              }
            }
          }
        }
      }
      count++;
    }
  }

  private void fillCustomerInfo(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(3);
    updateCellValue(row, 14, data.getLoanApplication().getLoanCode());
    // Thong tin cua toi
    row = sheet.getRow(15);
    // Ho va ten
    updateCellValue(row, 4, data.getLoanApplication().getFullName());
    // so CIF
    updateCellValue(row, 11, data.getLoanApplication().getCifNo());
    // Gioi tinh
    updateCellValue(row, 8, data.getLoanApplication().getGender().getName());

    row = sheet.getRow(16);
    // Sdt
    updateCellValue(row, 4, data.getLoanApplication().getPhone());
    // ngay sinh
    updateCellValue(
        row,
        7,
        DateUtils.convertToSimpleFormat(data.getLoanApplication().getBirthday(), "dd/MM/yyyy"));

    row = sheet.getRow(17);
    String ttnn = getNguonThu(data, OwnerTypeEnum.ME, null);
    // Tinh trang nghe nghiep
    updateCellValue(row, 4, ttnn);
    // Ma nganh kinh te
    updateCellValue(row, 7, Constants.ExportExcel.NGANH_NONG_LAM_NGHIEP);
    // Ma nhan vien msb
    if (!CollectionUtils.isEmpty(data.getSalaryIncomes())) {
      List<SalaryIncome> mes =
          data.getSalaryIncomes().stream()
              .filter(
                  x ->
                      x.getOwnerType().equals(OwnerTypeEnum.ME)
                          && !StringUtils.isEmpty(x.getEmplCode()))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(mes)) {
        updateCellValue(row, 5, mes.get(0).getEmplCode());
      }
    }
    // trinh do hoc van
    if (data.getLoanApplication().getEducation() != null) {
      updateCellValue(row, 11, data.getLoanApplication().getEducation().getName());
    }

    row = sheet.getRow(18);
    // Tinh trang hon nhan
    updateCellValue(row, 4, data.getLoanApplication().getMaritalStatus().getName());
    // Ma doi tac
    updateCellValue(row, 6, data.getLoanApplication().getPartnerCode());
    // QUoc tich
    updateCellValue(row, 8, Constants.VIET_NAM);

    // Phan khuc
    if (data.getLoanApplication().getCustomerSegment() != null) {
      updateCellValue(row, 11, data.getLoanApplication().getCustomerSegment().getTt());
    }

    row = sheet.getRow(19);
    // loai giay to
    if (!StringUtils.isEmpty(data.getLoanApplication().getIdNo())
        && data.getLoanApplication().getIdNo().length() == 12) {
      updateCellValue(row, 3, Constants.ExportExcel.CCCD);
    } else {
      updateCellValue(row, 3, Constants.ExportExcel.CMND);
    }
    // CCCD/CMD
    updateCellValue(row, 4, data.getLoanApplication().getIdNo());
    // Ngay cap
    updateCellValue(
        row,
        9,
        DateUtils.convertToSimpleFormat(data.getLoanApplication().getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(20);
    // Noi cap
    Optional<PlaceOfIssueIdCardEntity> place =
        placeOfIssueIdCardRepository.findByCode(data.getLoanApplication().getPlaceOfIssue());
    if (place.isPresent()) {
      if (place.get().getName().contains(Constants.ExportExcel.CONG_AN)) {
        updateCellValue(row, 4, Constants.ExportExcel.CONG_AN);
        String province = place.get().getName().replaceAll(Constants.ExportExcel.CONG_AN, "");
        province = province.trim();
        updateCellValue(row, 8, province);
      } else {
        updateCellValue(row, 4, place.get().getName());
      }
    }
    // Ngay cap
    updateCellValue(
        row,
        9,
        DateUtils.convertToSimpleFormat(data.getLoanApplication().getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(23);
    // Giay to dinh danh khac
    updateCellValue(
        row,
        5,
        getOtherIdentify(
            data.getLoanApplication().getOldIdNo(),
            data.getLoanApplication().getOldIdNo2(),
            data.getLoanApplication().getOldIdNo3()));
    // Email
    updateCellValue(row, 9, data.getLoanApplication().getEmail());

    row = sheet.getRow(24);
    // Ho khau thuong tru
    updateCellValue(row, 4, data.getLoanApplication().getResidenceAddress());
    updateCellValue(row, 6, data.getLoanApplication().getResidenceWardName());
    updateCellValue(row, 8, data.getLoanApplication().getResidenceDistrictName());
    updateCellValue(row, 10, data.getLoanApplication().getResidenceProvinceName());

    row = sheet.getRow(25);
    // Ho khau thuong tru
    updateCellValue(row, 4, data.getLoanApplication().getAddress());
    updateCellValue(row, 6, data.getLoanApplication().getWardName());
    updateCellValue(row, 8, data.getLoanApplication().getDistrictName());
    updateCellValue(row, 10, data.getLoanApplication().getProvinceName());

    Map<String, String> map = getDiaChiCoQuan(data, OwnerTypeEnum.ME);
    if (map != null) {
      row = sheet.getRow(26);
      // Ho khau thuong tru
      updateCellValue(row, 4, map.get(Address));
      updateCellValue(row, 6, map.get(Ward));
      updateCellValue(row, 8, map.get(District));
      updateCellValue(row, 10, map.get(Province));
    }
  }

  private String getOtherIdentify(String old1, String old2, String old3) {
    List<String> strings = Arrays.asList(old1, old2, old3);
    strings = strings.stream().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());
    return String.join(";", strings);
  }

  private void fillCicInfo(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(74);
    if (CollectionUtils.isEmpty(data.getCics())) {
      updateCellValue(row, 14, 0L);
      return;
    }
    int startRow = 74;

    updateCellValue(row, 14, Long.valueOf(Math.min(data.getCics().size(), 6)));
    int count = 0;
    for (CMSGetCicInfo cic : data.getCics()) {
      CMSCicItem item = getCicItem(cic.getCicItems());
      if (item == null) {
        continue;
      }
      if (count > 6) {
        return;
      }
      row = sheet.getRow(startRow + count);
      if (cic.getRelationshipType() != null) {
        updateCellValue(row, 3, cic.getRelationshipType().getName());
      }

      if (item.getTypeDebt() != null) {
        updateCellValue(row, 5, item.getTypeDebt().getDescription());
      }

      if (item.getTypeDebt12() != null) {
        updateCellValue(row, 7, item.getTypeDebt12().getDescription());
      }

      if (item.getTypeDebt24() != null) {
        updateCellValue(row, 9, item.getTypeDebt24().getDescription());
      }

      if (item.getPass() != null && item.getPass()) {
        updateCellValue(row, 10, "Thỏa mãn");
      } else if (item.getPass() != null && !item.getPass()) {
        updateCellValue(row, 10, "Không thỏa mãn");
      }
      count++;
    }
  }

  private CMSCicItem getCicItem(List<CMSCicItem> cicItems) {
    if (CollectionUtils.isEmpty(cicItems)) {
      return null;
    }
    CMSCicItem cmsCicItem = cicItems.get(0);
    CMSCicItem cmsCicItem2;
    if (cicItems.size() > 1) {
      cmsCicItem2 = cicItems.get(1);
      cmsCicItem.setTypeDebt(getTypeDebt(cmsCicItem.getTypeDebt(), cmsCicItem2.getTypeDebt()));
      cmsCicItem.setTypeDebt12(
          getTypeDebt(cmsCicItem.getTypeDebt12(), cmsCicItem2.getTypeDebt12()));
      cmsCicItem.setTypeDebt24(
          getTypeDebt(cmsCicItem.getTypeDebt24(), cmsCicItem2.getTypeDebt24()));
    }
    return cmsCicItem;
  }

  private CicGroupEnum getTypeDebt(CicGroupEnum enum1, CicGroupEnum enum2) {
    if (enum1 == null && enum2 != null) {
      return enum2;
    } else if (enum1 != null && enum2 != null) {
      if (enum1.getValue() < enum2.getValue()) {
        return enum2;
      }
    }
    return enum1;
  }

  private void fillAssetEvaluate(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(309);
    if (data.getAssetEvaluate() == null) {
      // GIa tri an hien
      updateCellValue(row, 14, 0L);
      return;
    }

    // GIa tri an hien
    updateCellValue(row, 14, 1L);

    // Chu nguon thu la
    if (data.getAssetEvaluate().getOwnerType() != null
        && OwnerTypeEnum.PAYER_PERSON.equals(data.getAssetEvaluate().getOwnerType())) {
      int payerIndex =
          getLoanPayerIndex(data.getAssetEvaluate().getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }

      updateCellValue(
          row,
          9,
          String.format("%s %s", data.getAssetEvaluate().getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(
          row,
          9,
          data.getAssetEvaluate().getOwnerType() != null
              ? data.getAssetEvaluate().getOwnerType().getName()
              : "Khách hàng");
    }

    int startRow = 313;
    int count = 0;
    for (AssetEvaluateItem item : data.getAssetEvaluate().getAssetEvaluateItems()) {
      if (count > 20) {
        return;
      }
      row = sheet.getRow(startRow + count);
      // Mo ta tai san
      updateCellValue(row, 4, item.getAssetDescription());
      // Loai tai san
      if (item.getAssetType() != null) {
        updateCellValue(row, 6, item.getAssetType().getName());
      }
      // Giay to can cu
      updateCellValue(row, 8, item.getLegalRecord());

      // Gia tri tai san
      updateCellValue(row, 10, item.getValue());

      count++;
    }
    row = sheet.getRow(336);
    // Tong gia tri tai san tich luy
    updateCellValue(row, 5, data.getAssetEvaluate().getTotalValue());

    row = sheet.getRow(337);
    // Tong du no
    updateCellValue(row, 5, data.getAssetEvaluate().getDebitBalance());

    row = sheet.getRow(338);
    // thu nhap cua khach hang
    updateCellValue(row, 5, data.getAssetEvaluate().getIncomeValue());

    row = sheet.getRow(339);
    // danh gia cua dvkd
    updateCellValue(row, 5, data.getAssetEvaluate().getRmReview());

    row = sheet.getRow(340);
    // tong thu nhap ghi nhan
    updateCellValue(row, 5, data.getAssetEvaluate().getRmInputValue());
  }

  private void fillOtherEvaluate(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(342);
    if (CollectionUtils.isEmpty(data.getOtherEvaluates())) {
      updateCellValue(row, 14, 0L);
      return;
    }

    OtherEvaluate otherEvaluate = data.getOtherEvaluates().get(0);
    updateCellValue(row, 14, 1L);

    if (otherEvaluate
        .getOwnerType()
        .getCode()
        .equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      int payerIndex = getLoanPayerIndex(otherEvaluate.getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }

      updateCellValue(
          row, 9, String.format("%s %s", otherEvaluate.getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(row, 9, otherEvaluate.getOwnerType().getName());
    }

    row = sheet.getRow(344);
    // Phuong phap ghi nhan
    if (data.getCommonIncome() != null) {
      updateCellValue(
          row,
          4,
          data.getCommonIncome().getRecognitionMethod2() != null
              ? data.getCommonIncome().getRecognitionMethod2().getName()
              : "");
    }
    // Chung tu ghi nhan
    String legalRecord = LegalRecordEnum.getByCode(otherEvaluate.getLegalRecord());
    updateCellValue(
        row, 9, StringUtils.isEmpty(legalRecord) ? otherEvaluate.getLegalRecord() : legalRecord);

    row = sheet.getRow(345);
    // Dien giái
    updateCellValue(row, 4, otherEvaluate.getRmReview());

    row = sheet.getRow(346);
    // Thu nhap ghi nhan
    updateCellValue(row, 4, otherEvaluate.getRmInputValue());
  }

  // hold on --> ok
  private void fillApprovalFlow(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(2);
    // Luong phe duyet
    if (data.getCommonIncome() != null && data.getCommonIncome().getApprovalFlow() != null) {
      updateCellValue(row, 4, data.getCommonIncome().getApprovalFlow().getName());
    }

    CreditAppraisal creditAppraisal = data.getCreditAppraisal();
    if (creditAppraisal != null) {

      row = sheet.getRow(10);
      // Ngay trinh ho so
      // OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
      updateCellValue(row, 9, DateUtils.convertToSimpleFormat(new Date(), "dd/MM/yyyy"));

      row = sheet.getRow(6);
      // Khu vuc/vung
      if (StringUtils.isEmpty(creditAppraisal.getBusinessAreaName())) {
        updateCellValue(row, 4, creditAppraisal.getBusinessArea());
      } else {
        updateCellValue(row, 4, creditAppraisal.getBusinessAreaName());
      }

      // Ten don vi kinh doanh
      updateCellValue(row, 9, creditAppraisal.getBusinessName());

      row = sheet.getRow(7);
      // Can bo ban hang
      updateCellValue(row, 4, creditAppraisal.getSaleFullName());
      // SDT Can bo ban hang
      updateCellValue(row, 9, creditAppraisal.getSalePhone());

      row = sheet.getRow(8);

      // hold on --> ok
      // ma officer code
      updateCellValue(
          row,
          0,
          data.getCreditAppraisal() != null ? data.getCreditAppraisal().getOfficerCode() : "");

      // Giam doc DVKD
      updateCellValue(row, 4, creditAppraisal.getManagerFullName());
      // SDT Giam doc DVKD
      updateCellValue(row, 9, creditAppraisal.getManagerPhone());

      row = sheet.getRow(9);
      // Ma XHTD (CSS) 1
      updateCellValue(row, 4, creditAppraisal.getCssProfileId());
      // KQ XHTD 1
      updateCellValue(row, 6, creditAppraisal.getCssGrade());
      // ngay XHTD 1
      if (creditAppraisal.getScoringDate() != null) {
        updateCellValue(
            row,
            9,
            DateUtils.convertToSimpleFormat(creditAppraisal.getScoringDate(), "dd/MM/yyyy"));
      }

      // Danh gia kha nang tra no cua khach hang
      row = sheet.getRow(350);
      // Tong thu nhap
      updateCellValue(row, 4, creditAppraisal.getTotalIncome());

      // Tong nghia vu tra no
      updateCellValue(row, 9, creditAppraisal.getTotalOfDebits());

      row = sheet.getRow(352);
      // DTI
      if (creditAppraisal.getDti() != null) {
        updateCellValue(row, 4,
            String.valueOf(Math.round(creditAppraisal.getDti() * 100) / 100));
      }

      row = sheet.getRow(1177);
      // DTI
      if (creditAppraisal.getLtv() != null) {
        updateCellValue(row, 9,
            String.valueOf(Math.round(creditAppraisal.getLtv() * 100) / 100));
      }

      row = sheet.getRow(353);
      // Danh gia
      if (creditAppraisal.getCreditEvaluationResult() != null) {
        updateCellValue(row, 4, creditAppraisal.getCreditEvaluationResult().getName());
      }

      // dieu kien cap tin dung
      row = sheet.getRow(1229);
      // truoc khi mo han muc
      updateCellValue(row, 5, creditAppraisal.getBeforeOpenLimitCondition());

      row = sheet.getRow(1230);
      // Truoc khi giai ngan
      updateCellValue(row, 5, creditAppraisal.getBeforeDisbursementCondition());

      row = sheet.getRow(1231);
      // Sau giai ngan
      updateCellValue(row, 5, creditAppraisal.getAfterDisbursementCondition());
    }
  }

  private void fillMarriedPerson(XSSFSheet sheet, CMSLoanApplicationReview data) {
    MarriedPerson marriedPerson = data.getMarriedPerson();
    LoanApplication loanApplication = data.getLoanApplication();
    if (loanApplication.getMaritalStatus() == null
        || !loanApplication
        .getMaritalStatus()
        .getCode()
        .equalsIgnoreCase(MaritalStatusEnum.MARRIED.getCode())
        || marriedPerson == null) {
      XSSFRow row = sheet.getRow(27);
      // Ho va ten
      updateCellValue(row, 14, 0L);
      return;
    }

    // Gioi tinh
    GenderEnum genderEnum;
    ContactPersonTypeEnum contactPersonTypeEnum;
    if (loanApplication.getGender().getCode().equalsIgnoreCase(GenderEnum.FEMALE.getCode())) {
      genderEnum = GenderEnum.MALE;
      contactPersonTypeEnum = ContactPersonTypeEnum.HUSBAND;
    } else {
      genderEnum = GenderEnum.FEMALE;
      contactPersonTypeEnum = ContactPersonTypeEnum.WIFE;
    }
    XSSFRow row = sheet.getRow(28);
    updateCellValue(row, 10, contactPersonTypeEnum.getName());

    row = sheet.getRow(29);
    // Ho va ten
    updateCellValue(row, 4, marriedPerson.getFullName());

    updateCellValue(row, 8, genderEnum.getName());

    updateCellValue(row, 11, marriedPerson.getCifNo());

    row = sheet.getRow(30);
    // Sdt
    updateCellValue(row, 4, marriedPerson.getPhone());
    // ngay sinh
    updateCellValue(
        row, 7, DateUtils.convertToSimpleFormat(marriedPerson.getBirthday(), "dd/MM/yyyy"));

    row = sheet.getRow(31);
    // Quoc tich
    updateCellValue(row, 4, Constants.VIET_NAM);

    row = sheet.getRow(32);
    // Trinh do hoc van
    if (marriedPerson.getEducation() != null) {
      updateCellValue(row, 11, marriedPerson.getEducation().getName());
    }

    String ttnn = getNguonThu(data, OwnerTypeEnum.MARRIED_PERSON, null);
    // Tinh trang nghe nghiep
    updateCellValue(row, 4, ttnn);
    // Ma nganh kinh te
    updateCellValue(row, 7, "A001 - NGANH NONG LAM NGHIEP");

    row = sheet.getRow(33);
    // Tinh trang hon nhan
    updateCellValue(row, 4, MaritalStatusEnum.MARRIED.getName());

    row = sheet.getRow(34);
    // loai giay to
    if (!StringUtils.isEmpty(marriedPerson.getIdNo()) && marriedPerson.getIdNo().length() == 12) {
      updateCellValue(row, 3, Constants.ExportExcel.CCCD);
    } else {
      updateCellValue(row, 3, Constants.ExportExcel.CMND);
    }
    // CCCD/CMD
    updateCellValue(row, 4, marriedPerson.getIdNo());
    // Ngay cap
    updateCellValue(
        row, 9, DateUtils.convertToSimpleFormat(marriedPerson.getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(35);
    // Noi cap
    Optional<PlaceOfIssueIdCardEntity> place =
        placeOfIssueIdCardRepository.findByCode(marriedPerson.getPlaceOfIssue());
    if (place.isPresent()) {
      if (place.get().getName().contains(Constants.ExportExcel.CONG_AN)) {
        updateCellValue(row, 4, Constants.ExportExcel.CONG_AN);
        String province = place.get().getName().replaceAll(Constants.ExportExcel.CONG_AN, "");
        province = province.trim();
        updateCellValue(row, 8, province);
      } else {
        updateCellValue(row, 4, place.get().getName());
      }
    }
    // Ngay cap
    updateCellValue(
        row, 9, DateUtils.convertToSimpleFormat(marriedPerson.getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(38);
    // Giay to dinh danh khac
    updateCellValue(
        row,
        5,
        getOtherIdentify(
            marriedPerson.getOldIdNo(), marriedPerson.getOldIdNo2(), marriedPerson.getOldIdNo3()));
    // Email
    updateCellValue(row, 9, marriedPerson.getEmail());

    row = sheet.getRow(39);
    // Ho khau thuong tru
    updateCellValue(row, 4, marriedPerson.getResidenceAddress());
    updateCellValue(row, 6, marriedPerson.getResidenceWardName());
    updateCellValue(row, 8, marriedPerson.getResidenceDistrictName());
    updateCellValue(row, 10, marriedPerson.getResidenceProvinceName());

    row = sheet.getRow(40);
    // Dia chi sinh song hien tai
    updateCellValue(row, 4, marriedPerson.getAddress());
    updateCellValue(row, 6, marriedPerson.getWardName());
    updateCellValue(row, 8, marriedPerson.getDistrictName());
    updateCellValue(row, 10, marriedPerson.getProvinceName());

    Map<String, String> map = getDiaChiCoQuan(data, OwnerTypeEnum.MARRIED_PERSON);
    if (map != null) {
      row = sheet.getRow(41);
      // Ho khau thuong tru
      updateCellValue(row, 4, map.get(Address));
      updateCellValue(row, 6, map.get(Ward));
      updateCellValue(row, 8, map.get(District));
      updateCellValue(row, 10, map.get(Province));
    }
  }

  private void fillLoanPayer(
      XSSFSheet sheet, LoanPayer loanPayer, int distance, CMSLoanApplicationReview data) {
    if (loanPayer == null) {
      return;
    }
    XSSFRow row = sheet.getRow(44 + distance);
    if (loanPayer.getRelationshipType() != null) {
      updateCellValue(row, 10, loanPayer.getRelationshipType().getName());
    }

    row = sheet.getRow(45 + distance);
    // Ho va ten
    updateCellValue(row, 4, loanPayer.getFullName());
    // Gioi tinh
    updateCellValue(row, 8, loanPayer.getGender().getName());
    // so cif
    updateCellValue(row, 11, loanPayer.getCifNo());

    row = sheet.getRow(46 + distance);
    // Sdt
    updateCellValue(row, 4, loanPayer.getPhone());
    // ngay sinh
    updateCellValue(row, 7, DateUtils.convertToSimpleFormat(loanPayer.getBirthday(), "dd/MM/yyyy"));

    row = sheet.getRow(47 + distance);
    // trinh do hoc van
    if (loanPayer.getEducation() != null) {
      updateCellValue(row, 11, loanPayer.getEducation().getName());
    }

    String ttnn = getNguonThu(data, OwnerTypeEnum.PAYER_PERSON, loanPayer.getUuid());
    // Tinh trang nghe nghiep
    updateCellValue(row, 4, ttnn);
    // Ma nganh kinh te
    updateCellValue(row, 7, "A001 - NGANH NONG LAM NGHIEP");

    row = sheet.getRow(48 + distance);
    // Tinh trang hon nhan
    if (loanPayer.getMaritalStatus() != null) {
      updateCellValue(row, 4, loanPayer.getMaritalStatus().getName());
    }
    // QUoc tich
    if (loanPayer.getNationality() != null) {
      updateCellValue(row, 8, Constants.VIET_NAM);
    }

    row = sheet.getRow(49 + distance);
    // loai giay to
    if (!StringUtils.isEmpty(loanPayer.getIdNo()) && loanPayer.getIdNo().length() == 12) {
      updateCellValue(row, 3, Constants.ExportExcel.CCCD);
    } else {
      updateCellValue(row, 3, Constants.ExportExcel.CMND);
    }
    // CCCD/CMD
    updateCellValue(row, 4, loanPayer.getIdNo());
    // Ngay cap
    updateCellValue(row, 9, DateUtils.convertToSimpleFormat(loanPayer.getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(50 + distance);
    // Noi cap
    Optional<PlaceOfIssueIdCardEntity> place =
        placeOfIssueIdCardRepository.findByCode(loanPayer.getPlaceOfIssue());
    if (place.isPresent()) {
      if (place.get().getName().contains(Constants.ExportExcel.CONG_AN)) {
        updateCellValue(row, 4, Constants.ExportExcel.CONG_AN);
        String province = place.get().getName().replaceAll(Constants.ExportExcel.CONG_AN, "");
        province = province.trim();
        updateCellValue(row, 8, province);
      } else {
        updateCellValue(row, 4, place.get().getName());
      }
    }
    // Ngay cap
    updateCellValue(row, 9, DateUtils.convertToSimpleFormat(loanPayer.getIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(53 + distance);
    // Giay to dinh danh khac
    updateCellValue(
        row,
        5,
        getOtherIdentify(loanPayer.getOldIdNo(), loanPayer.getOldIdNo2(), loanPayer.getOldIdNo3()));
    // Email
    updateCellValue(row, 9, loanPayer.getEmail());

    // Noi cu tru
    row = sheet.getRow(54 + distance);
    updateCellValue(row, 4, loanPayer.getResidenceAddress());
    updateCellValue(row, 6, loanPayer.getResidenceWardName());
    updateCellValue(row, 8, loanPayer.getResidenceDistrictName());
    updateCellValue(row, 10, loanPayer.getResidenceProvinceName());

    // Dia chi hien tai
    row = sheet.getRow(55 + distance);
    updateCellValue(row, 4, loanPayer.getAddress());
    updateCellValue(row, 6, loanPayer.getWardName());
    updateCellValue(row, 8, loanPayer.getDistrictName());
    updateCellValue(row, 10, loanPayer.getProvinceName());
  }

  // hold on -> ok
  private void fillIncome(XSSFSheet sheet, CMSLoanApplicationReview data) {
    if (data.getCommonIncome() == null) {
      return;
    }

    XSSFRow row = sheet.getRow(95);
    // Phuong phap ghi nhan thu nhap
    if (data.getCommonIncome().getRecognitionMethod1() != null) {
      updateCellValue(row, 6, data.getCommonIncome().getRecognitionMethod1().getName());
    }
    if (data.getCommonIncome().getRecognitionMethod2() != null) {
      updateCellValue(row, 7, data.getCommonIncome().getRecognitionMethod2().getName());
    }
  }

  private void fillSalaryIncome(
      XSSFSheet sheet, SalaryIncome income, int distance, CMSLoanApplicationReview data) {
    if (income == null) {
      return;
    }
    XSSFRow row = sheet.getRow(97 + distance);
    if (income.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      int payerIndex = getLoanPayerIndex(income.getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }

      updateCellValue(row, 9, String.format("%s %s", income.getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(row, 9, income.getOwnerType().getName());
    }

    row = sheet.getRow(98 + distance);
    // Ap dung he so thu nhap
    updateCellValue(row, 6, "Không");

    row = sheet.getRow(99 + distance);
    // Co quan cong tac
    updateCellValue(row, 4, income.getOfficeName());
    // Chuc vu
    updateCellValue(row, 10, income.getOfficeTitle());

    row = sheet.getRow(100 + distance);
    // Dia chi co quan
    String address =
        String.format(
            "%s, %s, %s, %s",
            income.getOfficeAddress(),
            income.getOfficeWardName(),
            income.getOfficeDistrictName(),
            income.getOfficeProvinceName());
    updateCellValue(row, 4, address);
    // SDT
    updateCellValue(row, 10, income.getOfficePhone());

    row = sheet.getRow(101 + distance);
    // Ngay vao lam viec
    if (income.getStartWorkDate() != null) {
      updateCellValue(
          row, 4, DateUtils.convertToSimpleFormat(income.getStartWorkDate(), "dd/MM/yyyy"));
    }

    row = sheet.getRow(102 + distance);
    // Phan nhom DVCT
    if (income.getWorkGrouping() != null) {
      updateCellValue(row, 4, income.getWorkGrouping().getTt());
    }
    // Ma so thue ca nhan
    updateCellValue(row, 6, income.getTaxCode());
    // Bao hiem xa hoi
    updateCellValue(row, 10, income.getInsuranceNo());

    row = sheet.getRow(103 + distance);
    // Cap bac
    if (income.getBand() != null) {
      updateCellValue(row, 4, income.getBand().getName());
    }
    // Xep loai KPI
    if (income.getKpiRate() != null) {
      updateCellValue(row, 7, income.getKpiRate().getTt());
    }
    // Nhom dac biet
    if (income.getSpecialGroup() != null) {
      updateCellValue(row, 10, income.getSpecialGroup().getTt());
    }

    row = sheet.getRow(104 + distance);
    // Hinh thuc tra luong
    if (income.getPaymentMethod() != null) {
      updateCellValue(row, 5, income.getPaymentMethod().getName());
    }
    // Loai hop dong
    if (income.getKindOfContract() != null) {
      updateCellValue(row, 10, income.getKindOfContract().getName());
    }

    row = sheet.getRow(105 + distance);
    // Dien giai (danh gia cua RM)
    updateCellValue(row, 4, income.getRmReview());

    row = sheet.getRow(106 + distance);
    // Hinh thuc tra luong
    updateCellValue(row, 4, income.getValue());
  }

  private void fillBusinessIncomeIndividual(
      XSSFSheet sheet, BusinessIncome income, int distance, CMSLoanApplicationReview data) {
    if (income == null) {
      return;
    }

    XSSFRow row = sheet.getRow(177 + distance);
    if (income.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      int payerIndex = getLoanPayerIndex(income.getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }

      updateCellValue(row, 9, String.format("%s %s", income.getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(row, 9, income.getOwnerType().getName());
    }

    row = sheet.getRow(180 + distance);
    // Ten co so kinh doanh
    updateCellValue(row, 5, income.getName());

    row = sheet.getRow(181 + distance);
    // Nganh nghe kinh doanh
    updateCellValue(row, 5, income.getBusinessActivity());

    row = sheet.getRow(182 + distance);
    // Lĩnh vực kinh doanh (nhập LOS)
    updateCellValue(row, 5, income.getBusinessLine().getName());
    // Hình thức sở hữu địa điểm kinh doanh
    if (income.getOwnershipType() != null) {
      updateCellValue(row, 10, income.getOwnershipType().getName());
    }

    row = sheet.getRow(183 + distance);
    // Loai hinh kinh doanh (nhập LOS)
    updateCellValue(row, 5, "Khác");

    row = sheet.getRow(184 + distance);
    // So DK kinh doanh
    updateCellValue(
        row,
        4,
        StringUtils.isEmpty(income.getBusinessCode()) ? "Không có ĐKKD" : income.getBusinessCode());
    // ngay cap
    Date issuedDate =
        BusinessTypeEnum.WITH_REGISTRATION.equals(income.getBusinessType())
            ? income.getIssuedDate()
            : income.getBusinessStartDate();
    if (issuedDate != null) {
      updateCellValue(row, 9, DateUtils.convertToSimpleFormat(issuedDate, "dd/MM/yyyy"));
    }

    row = sheet.getRow(185 + distance);
    Optional<ProvinceEntity> province = provinceRepository.findByCode(income.getPlaceOfIssue());
    // Noi cap
    if (province.isPresent()) {
      updateCellValue(row, 4, province.get().getName());
    }

    // ty le gop von cua khach hang
    if (income.getCapitalRatio() != null) {
      updateCellValue(row, 9, income.getCapitalRatio().floatValue() / 100);
    }

    row = sheet.getRow(186 + distance);
    // dia chi
    updateCellValue(
        row,
        5,
        String.format(
            "%s, %s, %s", income.getAddress(), income.getWardName(), income.getDistrictName()));
    updateCellValue(row, 9, income.getProvinceName());

    row = sheet.getRow(189 + distance);
    // quy trinh san suat kinh doanh
    updateCellValue(row, 4, income.getProductionProcess());

    row = sheet.getRow(190 + distance);
    // Phuong thuc ghi chep
    updateCellValue(row, 4, income.getRecordingMethod());

    row = sheet.getRow(191 + distance);
    // nguon hang vao
    updateCellValue(row, 4, income.getInputSource());

    row = sheet.getRow(192 + distance);
    // Dau ra
    updateCellValue(row, 4, income.getOutputSource());

    row = sheet.getRow(193 + distance);
    // Quy mô kinh doanh
    updateCellValue(row, 4, income.getBusinessScale());

    row = sheet.getRow(194 + distance);
    // Hàng tồn kho, phải thu, phải trả
    updateCellValue(row, 4, income.getInventoryReceivablePayable());

    row = sheet.getRow(197 + distance);
    // KY danh gia ket qua
    if (income.getEvaluationPeriod() != null) {
      updateCellValue(row, 5, income.getEvaluationPeriod().getName());
    }

    row = sheet.getRow(198 + distance);
    // doanh thu thang
    updateCellValue(row, 5, income.getRevenue());

    row = sheet.getRow(199 + distance);
    // chi phi thang
    updateCellValue(row, 5, income.getCost());

    row = sheet.getRow(200 + distance);
    // loi nhuan thang
    updateCellValue(row, 5, income.getProfit());

    row = sheet.getRow(201 + distance);
    // Bien loi nhuan
    if (income.getProfitMargin() != null) {
      updateCellValue(row, 5, income.getProfitMargin().longValue());
    }

    row = sheet.getRow(202 + distance);
    // Loi nhuan ghi nhan cho khach hang
    updateCellValue(row, 5, income.getProfitsYourself());

    row = sheet.getRow(203 + distance);
    // Danh gia ket qua kinh doanh
    updateCellValue(row, 5, income.getBusinessPerformance());

    row = sheet.getRow(204 + distance);
    // Thu nhập ghi nhận (tháng)
    updateCellValue(row, 5, income.getValue());
  }

  private void fillBusinessIncomeEnterprise(
      XSSFSheet sheet, BusinessIncome income, int distance, CMSLoanApplicationReview data) {
    if (income == null) {
      return;
    }

    XSSFRow row = sheet.getRow(235 + distance);
    if (income.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      int payerIndex = getLoanPayerIndex(income.getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }

      updateCellValue(row, 9, String.format("%s %s", income.getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(row, 9, income.getOwnerType().getName());
    }

    row = sheet.getRow(238 + distance);
    // Ten co so kinh doanh
    updateCellValue(row, 4, income.getName());

    row = sheet.getRow(239 + distance);
    // dia chi
    updateCellValue(
        row,
        4,
        String.format(
            "%s, %s, %s, %s",
            income.getAddress(),
            income.getWardName(),
            income.getDistrictName(),
            income.getProvinceName()));

    row = sheet.getRow(240 + distance);
    // So DK kinh doanh
    updateCellValue(row, 4, income.getBusinessCode());
    // dang ky lan dau
    updateCellValue(row, 6, DateUtils.convertToSimpleFormat(income.getIssuedDate(), "dd/MM/yyyy"));
    // ngay thay doi gan nhat
    updateCellValue(
        row, 10, DateUtils.convertToSimpleFormat(income.getRegistrationChangeDate(), "dd/MM/yyyy"));

    row = sheet.getRow(241 + distance);
    // Linh vuc kinh doanh
    if (income.getBusinessLine() != null) {
      updateCellValue(row, 5, income.getBusinessLine().getName());
    }
    // Hinh thuc so huu dia diem kinh doanh
    if (income.getOwnershipType() != null) {
      updateCellValue(row, 10, income.getOwnershipType().getName());
    }

    row = sheet.getRow(242 + distance);
    // Loai hinh kinh doanh
    updateCellValue(row, 5, "Khác");

    row = sheet.getRow(243 + distance);
    // Lĩnh vực kinh doanh, sản phẩm dịch vụ chính
    updateCellValue(row, 6, income.getBusinessActivity());

    row = sheet.getRow(244 + distance);
    // Nhom no hien tai
    CMSCicItem cmsCicItem = getBusinessCic(data, income.getBusinessCode());
    if (cmsCicItem != null && cmsCicItem.getTypeDebt() != null) {
      updateCellValue(row, 4, cmsCicItem.getTypeDebt().getDescription());
    }

    row = sheet.getRow(245 + distance);
    // Von dieu le
    updateCellValue(row, 4, income.getCharterCapital());

    // Ty le gop von
    if (income.getCapitalRatio() != null) {
      updateCellValue(row, 10, income.getCapitalRatio().floatValue() / 100);
    }

    row = sheet.getRow(249 + distance);
    // quy trinh san suat kinh doanh
    updateCellValue(row, 4, income.getProductionProcess());

    row = sheet.getRow(250 + distance);
    // Phuong thuc ghi chep
    updateCellValue(row, 4, income.getRecordingMethod());

    row = sheet.getRow(251 + distance);
    // nguon hang vao
    updateCellValue(row, 4, income.getInputSource());

    row = sheet.getRow(252 + distance);
    // Dau ra
    updateCellValue(row, 4, income.getOutputSource());

    row = sheet.getRow(253 + distance);
    // Quy mô kinh doanh
    updateCellValue(row, 4, income.getBusinessScale());

    row = sheet.getRow(254 + distance);
    // Hàng tồn kho, phải thu, phải trả
    updateCellValue(row, 4, income.getInventoryReceivablePayable());

    row = sheet.getRow(257 + distance);
    // KY danh gia ket qua
    if (income.getEvaluationPeriod() != null) {
      updateCellValue(row, 5, income.getEvaluationPeriod().getName());
    }

    row = sheet.getRow(258 + distance);
    // doanh thu thang
    updateCellValue(row, 5, income.getRevenue());

    row = sheet.getRow(259 + distance);
    // chi phi thang
    updateCellValue(row, 5, income.getCost());

    row = sheet.getRow(260 + distance);
    // loi nhuan thang
    updateCellValue(row, 5, income.getProfit());

    row = sheet.getRow(261 + distance);
    // Bien loi nhuan
    if (income.getProfitMargin() != null) {
      updateCellValue(row, 5, income.getProfitMargin().longValue());
    }

    row = sheet.getRow(262 + distance);
    // Loi nhuan ghi nhan cho khach hang
    updateCellValue(row, 5, income.getProfitsYourself());

    row = sheet.getRow(263 + distance);
    // Danh gia ket qua kinh doanh
    updateCellValue(row, 5, income.getBusinessPerformance());

    row = sheet.getRow(264 + distance);
    // Thu nhập ghi nhận (tháng)
    updateCellValue(row, 5, income.getValue());
  }

  private void fillOthersIncome(XSSFSheet sheet, OtherIncome income, int distance) {
    if (income == null) {
      return;
    }

    XSSFRow row = sheet.getRow(297 + distance);
    // Chu nguon thu
    String ownerType = income.getOwnerType().getName();
    if (income.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      ownerType = String.format("%s %s", OwnerTypeEnum.PAYER_PERSON.getName(), "1");
    }
    updateCellValue(row, 9, ownerType);

    row = sheet.getRow(299 + distance);
    // Thong tin nguon thu
    updateCellValue(row, 4, income.getIncomeFrom().getName());
    // Chung tu lam co so nguon thu
    updateCellValue(row, 9, income.getIncomeFrom().getName());

    row = sheet.getRow(300 + distance);
    // dien giai
    updateCellValue(row, 4, income.getRmReview());

    row = sheet.getRow(301 + distance);
    // Nganh nghe kinh doanh
    updateCellValue(row, 4, income.getValue());
    if (IncomeFromEnum.GOM_XAY.equals(income.getIncomeFrom())) {
      int index = 1201;
      row = sheet.getRow(1199);
      updateCellValue(row, 14, Boolean.TRUE.toString().toUpperCase());
      row = sheet.getRow(index);
      updateCellValue(row, 14, Long.valueOf(income.getLandTransactions().size()));
      for (LandTransactionRequest landTransaction : income.getLandTransactions()) {
        row = sheet.getRow(index++);
        updateCellValue(row, 3,
            DateUtils.convertTimestampToString(landTransaction.getTransactionTime()));
        updateCellValue(row, 4, landTransaction.getPropertySale());
        updateCellValue(row, 5, landTransaction.getBuyer());
        if (landTransaction.getTransactionValue() != null) {
          updateCellValue(row, 6, landTransaction.getTransactionValue().toPlainString());
        }
        if (landTransaction.getInitialCapital() != null) {
          updateCellValue(row, 7, landTransaction.getInitialCapital().toPlainString());
        }
        if (landTransaction.getBrokerageFees() != null) {
          updateCellValue(row, 8, landTransaction.getBrokerageFees().toPlainString());
        }
        if (landTransaction.getFeeTransfer() != null) {
          updateCellValue(row, 9, landTransaction.getFeeTransfer().toPlainString());
        }
        if (landTransaction.getProfit() != null) {
          updateCellValue(row, 10, landTransaction.getProfit().toPlainString());
        }
      }

    }
  }

  private void fillRentalProperties(
      XSSFSheet sheet, OtherIncome income, int distance, CMSLoanApplicationReview data) {
    if (income == null) {
      return;
    }

    XSSFRow row = sheet.getRow(141 + distance);
    if (income.getOwnerType().getCode().equalsIgnoreCase(OwnerTypeEnum.PAYER_PERSON.getCode())) {
      int payerIndex = getLoanPayerIndex(income.getPayerId(), data.getLoanPayers());
      if (payerIndex > 2) {
        return;
      }
      updateCellValue(row, 9, String.format("%s %s", income.getOwnerType().getName(), payerIndex));
    } else {
      // Chu nguon thu la
      updateCellValue(row, 9, income.getOwnerType().getName());
    }

    row = sheet.getRow(143 + distance);
    // Ten chu so huu
    updateCellValue(row, 9, getFullNameRentalOwner(income, data));

    row = sheet.getRow(144 + distance);
    // Địa chỉ tài sản/Loại xe
    updateCellValue(row, 4, income.getRentProperty());

    row = sheet.getRow(145 + distance);
    // Ben thue
    updateCellValue(row, 4, income.getTenant());
    // SDT Ben thue
    updateCellValue(row, 9, income.getTenantPhone());

    row = sheet.getRow(146 + distance);
    // Muc dich thue
    updateCellValue(row, 4, income.getTenantPurpose());
    // Gia cho thue
    updateCellValue(row, 9, income.getTenantPrice());

    row = sheet.getRow(147 + distance);
    // Dien giai chi tiet
    updateCellValue(row, 4, income.getRmReview());

    row = sheet.getRow(148 + distance);
    // Thu nhap ghi nhan
    updateCellValue(row, 4, income.getValue());
  }

  private String getFullNameRentalOwner(OtherIncome income, CMSLoanApplicationReview data) {
    if (OwnerTypeEnum.ME.equals(income.getOwnerType())) {
      return data.getLoanApplication().getFullName();
    }

    if (OwnerTypeEnum.MARRIED_PERSON.equals(income.getOwnerType())
        && data.getMarriedPerson() != null) {
      return data.getMarriedPerson().getFullName();
    }

    if (OwnerTypeEnum.PAYER_PERSON.equals(income.getOwnerType())
        && !StringUtils.isEmpty(income.getPayerId())
        && !CollectionUtils.isEmpty(data.getLoanPayers())) {
      List<LoanPayer> loanPayers =
          data.getLoanPayers().stream()
              .filter(loanPayer -> income.getPayerId().equalsIgnoreCase(loanPayer.getUuid()))
              .collect(Collectors.toList());

      return CollectionUtils.isEmpty(loanPayers) ? "" : loanPayers.get(0).getFullName();
    }

    return "";
  }

  private void fillCollateral(
      XSSFSheet sheet, Collateral collateral, int distance, CMSLoanApplicationReview data) {
    if (collateral == null) {
      return;
    }

    XSSFRow row = sheet.getRow(356 + distance);
    // Mvalue
    updateCellValue(row, 8, collateral.getMvalueId());

    // Id tai san cu
    updateCellValue(row, 10, collateral.getCoreId());

    row = sheet.getRow(357 + distance);
    // chung thu dinh gia so
    if (StringUtils.isEmpty(collateral.getValuationCert())) {
      updateCellValue(row, 8, Constants.ExportExcel.TRINH_SO_BO);
    } else {
      updateCellValue(row, 8, collateral.getValuationCert());
    }

    // Loai tai san
    // Mo ta tai san tren LOS
    String moTaLos = "";
    if (CollateralTypeEnum.ND.equals(collateral.getType())
        || CollateralTypeEnum.CC.equals(collateral.getType())) {
      row = sheet.getRow(358 + distance);
      updateCellValue(row, 4, Constants.ExportExcel.BAT_DONG_SAN);
      if (collateral.getAssetCategory() != null) {
        // Loại tai san LV2
        updateCellValue(row, 6, AssetCategoryEnum.valueOf(collateral.getAssetCategory()).getName());
        // Ma tai san
        updateCellValue(row, 10, collateral.getAssetCategory());
      }

      updateCellValue(row, 20, VNCharacterUtils.removeAccent(Constants.ExportExcel.BAT_DONG_SAN));

      row = sheet.getRow(360 + distance);
      // Dia chi
      updateCellValue(
          row,
          4,
          StringUtils.isEmpty(collateral.getAddress()) ? "Không có" : collateral.getAddress());
      updateCellValue(
          row,
          6,
          StringUtils.isEmpty(collateral.getWardName()) ? "Không có" : collateral.getWardName());
      updateCellValue(
          row,
          8,
          StringUtils.isEmpty(collateral.getDistrictName())
              ? "Không có"
              : collateral.getDistrictName());
      updateCellValue(row, 10, collateral.getProvinceName());
      moTaLos =
          String.format("%s %s %s", "BĐS tại", collateral.getAddress(), collateral.getWardName());

      row = sheet.getRow(363 + distance);
      // GIay to phap ly
      updateCellValue(
          row,
          4,
          String.format(
              "%s, nơi cấp %s, ngày cấp %s",
              collateral.getLegalDoc(),
              collateral.getDocPlaceOfIssueName(),
              DateUtils.convertToSimpleFormat(collateral.getDocIssuedOn(), "dd/MM/yyyy")));
    } else if (CollateralTypeEnum.PTVT.equals(collateral.getType())) {
      row = sheet.getRow(358 + distance);
      updateCellValue(row, 4, Constants.ExportExcel.PTVT);
      if (collateral.getAssetCategory() != null) {
        // Loại tai san LV2
        updateCellValue(row, 6, AssetCategoryEnum.valueOf(collateral.getAssetCategory()).getName());
        // Ma tai san
        updateCellValue(row, 10, collateral.getAssetCategory());
      }
      updateCellValue(row, 20, VNCharacterUtils.removeAccent(Constants.ExportExcel.PTVT));

      row = sheet.getRow(361 + distance);
      // xe nhan hieu
      updateCellValue(row, 4, collateral.getDescription());
      updateCellValue(row, 5, collateral.getChassisNo());
      updateCellValue(row, 6, collateral.getEngineNo());

      row = sheet.getRow(365 + distance);
      updateCellValue(
          row, 4, collateral.getTypeOfDoc() != null ? collateral.getTypeOfDoc().getName() : "");
      updateCellValue(row, 5, collateral.getRegistrationOrContractNo());
      updateCellValue(
          row,
          6,
          String.format(
              "%s %s",
              "Ngày", DateUtils.convertToSimpleFormat(collateral.getDocIssuedOn(), "dd/MM/yyyy")));

      row = sheet.getRow(367 + distance);
      updateCellValue(
          row, 4, collateral.getStatusUsing() != null ? collateral.getStatusUsing().getName() : "");
      updateCellValue(
          row,
          6,
          collateral.getDurationOfUsed() != null
              ? collateral.getDurationOfUsed().longValue()
              : null);
      updateCellValue(row, 8, "Tháng");
      moTaLos = collateral.getDescription();
      row = sheet.getRow(363 + distance);
      // GIay to phap ly
      updateCellValue(
          row,
          4,
          String.format(
              "%s, %s, %s",
              collateral.getTypeOfDoc() != null ? collateral.getTypeOfDoc().getName() : "",
              collateral.getRegistrationOrContractNo(),
              DateUtils.convertToSimpleFormat(collateral.getDocIssuedOn(), "dd/MM/yyyy")));
    } else if (CollateralTypeEnum.GTCG.equals(collateral.getType())) {
      row = sheet.getRow(358 + distance);
      updateCellValue(row, 4, Constants.ExportExcel.GTCG);
      if (collateral.getAssetCategory() != null) {
        // Loại tai san LV2
        updateCellValue(row, 6, AssetCategoryEnum.valueOf(collateral.getAssetCategory()).getName());
        // Ma tai san
        updateCellValue(row, 10, collateral.getAssetCategory());
      }
      updateCellValue(row, 20, VNCharacterUtils.removeAccent(Constants.ExportExcel.GTCG));

      row = sheet.getRow(362 + distance);

      // Dien so so tiet kiem
      // 30/03/2023
      updateCellValue(
          row,
          4,
          !StringUtils.isEmpty(collateral.getSavingBookNo())
              ? collateral.getSavingBookNo()
              : collateral.getDescription());

      row = sheet.getRow(366 + distance);
      // So tai khoan
      updateCellValue(row, 4, collateral.getDescription());
      // Ngay den han
      updateCellValue(
          row, 6, DateUtils.convertToSimpleFormat(collateral.getMaturityDate(), "dd/MM/yyyy"));
      // Chi nhanh phat hanh
      updateCellValue(row, 8, collateral.getIssuedBranch());
      // Lai suat tiet kiem
      updateCellValue(row, 10, collateral.getInterestRate());
      DecimalFormat df = new DecimalFormat("###,###,###");

      // 30/03/2023
      moTaLos =
          String.format("%s - %s", collateral.getDescription(), df.format(collateral.getValue()));

      row = sheet.getRow(363 + distance);
      // GIay to phap ly
      // 30/03/2023
      updateCellValue(
          row,
          4,
          String.format(
              "%s, %s, %s, %s",
              collateral.getDescription(),
              DateUtils.convertToSimpleFormat(collateral.getMaturityDate(), "dd/MM/yyyy"),
              collateral.getIssuedBranch(),
              collateral.getInterestRate()));
    }

    row = sheet.getRow(370 + distance);
    // Ten tai san tren LOS
    updateCellValue(
        row,
        6,
        String.format(
            "%s - %s",
            data.getLoanApplication().getCifNo(),
            data.getCreditAppraisal() == null ? "" : data.getCreditAppraisal().getBusinessName()));

    row = sheet.getRow(371 + distance);
    if (!StringUtils.isEmpty(moTaLos) && moTaLos.length() > 40) {
      moTaLos = moTaLos.substring(0, 40);
    }
    updateCellValue(row, 4, moTaLos);

    row = sheet.getRow(373 + distance);
    // Loại dat
    updateCellValue(row, 4, "DAT O DO THI");
    // Loai tai san dam bao
    updateCellValue(row, 8, "NHA SU DUNG DE O");

    row = sheet.getRow(374 + distance);
    // remask
    updateCellValue(
        row, 4, String.format("TT - %s - HL - RB", data.getLoanApplication().getFullName()));

    row = sheet.getRow(375 + distance);
    Date pricingDate = collateral.getPricingDate();
    // Ngay dinh gia
    if (collateral.getPricingDate() != null) {
      updateCellValue(
          row, 4, DateUtils.convertToSimpleFormat(collateral.getPricingDate(), "dd/MM/yyyy"));
    } else {
      Date currentDate = new Date(DateUtils.addToDate(new Date(), Calendar.HOUR, 7));
      pricingDate = currentDate;
      updateCellValue(row, 4, DateUtils.convertToSimpleFormat(currentDate, "dd/MM/yyyy"));
    }

    // Ngay dinh gia tiep theo
    if (collateral.getNextPricingDate() != null) {
      updateCellValue(
          row, 7, DateUtils.convertToSimpleFormat(collateral.getNextPricingDate(), "dd/MM/yyyy"));
    } else {
      Date nextPricingDate = new Date(DateUtils.addToDate(pricingDate, Calendar.YEAR, 1));
      updateCellValue(row, 7, DateUtils.convertToSimpleFormat(nextPricingDate, "dd/MM/yyyy"));
    }

    row = sheet.getRow(376 + distance);
    // Ten can bo dinh gia
    updateCellValue(
        row,
        4,
        StringUtils.isEmpty(collateral.getOfficerNamePricing())
            ? Constants.ExportExcel.CHUA_CO
            : collateral.getOfficerNamePricing());

    row = sheet.getRow(364 + distance);
    // Giay chung nhan so
    updateCellValue(row, 4, collateral.getLegalDoc());
    Optional<ProvinceEntity> province =
        provinceRepository.findByCode(collateral.getDocPlaceOfIssue());
    if (province.isPresent()) {
      // noi cap
      updateCellValue(row, 5, province.get().getName());
    }
    // ngay cap
    updateCellValue(
        row, 8, DateUtils.convertToSimpleFormat(collateral.getDocIssuedOn(), "dd/MM/yyyy"));

    row = sheet.getRow(368 + distance);
    // Gia tri tai san dam bao
    updateCellValue(row, 4, collateral.getValue());
    // Ty le LTV
    if (collateral.getLtvRate() != null) {
      updateCellValue(row, 7, collateral.getLtvRate() / 100);
    }

    row = sheet.getRow(369 + distance);
    // Gia tri tai san dam bao
    updateCellValue(row, 4, collateral.getGuaranteedValue());

    row = sheet.getRow(372 + distance);
    // so giay to phap ly
    updateCellValue(row, 4, collateral.getLegalDoc());

    // Noi dang ky
    updateCellValue(row, 8, collateral.getProvinceName());

    row = sheet.getRow(377 + distance);
    // Thong tin chu tai san
    updateCellValue(row, 6, collateral.getStatus().getName());

    row = sheet.getRow(378 + distance);
    // Moi quan he
    if (!StringUtils.isEmpty(collateral.getRelationship())) {
      updateCellValue(
          row, 10, ContactPersonTypeEnum.valueOf(collateral.getRelationship()).getName());
    }

    if (!CollectionUtils.isEmpty(collateral.getCollateralOwners())) {
      List<CollateralOwner> collateralOwners =
          getCollateralOwner(collateral.getCollateralOwners(), data);
      if (!CollectionUtils.isEmpty(collateralOwners)) {
        row = sheet.getRow(378 + distance);
        // so luong chu so huu
        updateCellValue(row, 14, Long.valueOf(collateralOwners.size()));
        for (int i = 0; i < collateralOwners.size(); i++) {
          fillCollateralOwner(sheet, collateralOwners.get(i), distance + (12 * i));
        }
      }
    } else {
      row = sheet.getRow(378 + distance);
      // so luong chu so huu
      updateCellValue(row, 14, 0L);
    }
  }

  private List<CollateralOwner> getCollateralOwner(
      List<CollateralOwner> collateralOwners, CMSLoanApplicationReview data) {
    if (CollectionUtils.isEmpty(collateralOwners)) {
      return Collections.EMPTY_LIST;
    }

    List<CollateralOwner> results = new ArrayList<>();
    for (CollateralOwner collateralOwner : collateralOwners) {
      if (CollateralOwnerMapTypeEnum.THIRD_PARTY.equals(collateralOwner.getMapType())) {
        results.add(collateralOwner);
      }

      if (CollateralOwnerMapTypeEnum.ME.equals(collateralOwner.getMapType())) {
        results.add(new CollateralOwner(data.getLoanApplication()));
      }

      if (CollateralOwnerMapTypeEnum.COUPLE.equals(collateralOwner.getMapType())) {
        results.add(new CollateralOwner(data.getMarriedPerson()));
      }

      if (CollateralOwnerMapTypeEnum.LOAN_PAYER.equals(collateralOwner.getMapType())) {
        List<LoanPayer> loanPayers =
            data.getLoanPayers().stream()
                .filter(x -> x.getUuid().equalsIgnoreCase(collateralOwner.getUuid()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(loanPayers)) {
          results.add(new CollateralOwner(loanPayers.get(0)));
        }
      }
    }

    return results;
  }

  private void fillCollateralOwner(XSSFSheet sheet, CollateralOwner collateralOwner, int distance) {
    XSSFRow row = sheet.getRow(378 + distance);
    // Moi quan he
    if (!StringUtils.isEmpty(collateralOwner.getRelationship())) {
      updateCellValue(
          row, 10, ContactPersonTypeEnum.valueOf(collateralOwner.getRelationship()).getName());
    }

    row = sheet.getRow(379 + distance);
    // Ho va ten
    updateCellValue(row, 4, collateralOwner.getFullName());
    // Gioi tinh
    if (collateralOwner.getGender() != null) {
      updateCellValue(row, 8, collateralOwner.getGender().getName());
    }

    row = sheet.getRow(380 + distance);
    // Sdt
    updateCellValue(row, 4, collateralOwner.getPhone());
    // ngay sinh
    if (collateralOwner.getBirthday() != null) {
      updateCellValue(
          row, 7, DateUtils.convertToSimpleFormat(collateralOwner.getBirthday(), "dd/MM/yyyy"));
    }

    row = sheet.getRow(381 + distance);
    // Tinh trang nghe nghiep
    updateCellValue(row, 4, "Thu nhập khác");
    // Ma nganh kinh te
    updateCellValue(row, 7, "A001 - NGANH NONG LAM NGHIEP");
    // tinh trang hoc van
    if (collateralOwner.getEducationLevel() != null) {
      updateCellValue(row, 11, collateralOwner.getEducationLevel().getName());
    }

    row = sheet.getRow(382 + distance);
    // Tinh trang hon nhan
    if (collateralOwner.getMaritalStatus() != null) {
      updateCellValue(row, 4, collateralOwner.getMaritalStatus().getName());
    }
    // QUoc tich
    if (collateralOwner.getNationality() != null) {
      updateCellValue(row, 8, Constants.VIET_NAM);
    }
    row = sheet.getRow(383 + distance);
    if (!StringUtils.isEmpty(collateralOwner.getIdNo())
        && collateralOwner.getIdNo().length() == 12) {
      updateCellValue(row, 3, Constants.ExportExcel.CCCD);
    } else {
      updateCellValue(row, 3, Constants.ExportExcel.CMND);
    }

    // CCCD/CMD
    updateCellValue(row, 4, collateralOwner.getIdNo());
    // Ngay cap
    if (collateralOwner.getIssuedOn() != null) {
      updateCellValue(
          row, 9, DateUtils.convertToSimpleFormat(collateralOwner.getIssuedOn(), "dd/MM/yyyy"));
    }

    row = sheet.getRow(384 + distance);
    // Noi cap
    if (collateralOwner.getPlaceOfIssue() != null) {
      Optional<PlaceOfIssueIdCardEntity> place =
          placeOfIssueIdCardRepository.findByCode(collateralOwner.getPlaceOfIssue());
      if (place.isPresent()) {
        if (!StringUtils.isEmpty(place.get().getName())
            && place.get().getName().contains(Constants.ExportExcel.CONG_AN)) {
          updateCellValue(row, 4, Constants.ExportExcel.CONG_AN);
          String province = place.get().getName().replaceAll(Constants.ExportExcel.CONG_AN, "");
          province = province.trim();
          updateCellValue(row, 8, province);
        } else {
          updateCellValue(row, 4, place.get().getName());
        }
      }
    }

    // Ngay cap
    if (collateralOwner.getIssuedOn() != null) {
      updateCellValue(
          row, 9, DateUtils.convertToSimpleFormat(collateralOwner.getIssuedOn(), "dd/MM/yyyy"));
    }

    row = sheet.getRow(387 + distance);
    // Giay to dinh danh khac
    updateCellValue(
        row,
        5,
        getOtherIdentify(
            collateralOwner.getOldIdNo(),
            collateralOwner.getOldIdNo2(),
            collateralOwner.getOldIdNo3()));
    // Email
    updateCellValue(row, 9, collateralOwner.getEmail());

    row = sheet.getRow(388 + distance);
    // Ho khau thuong tru
    updateCellValue(row, 4, collateralOwner.getAddress());
    updateCellValue(row, 6, collateralOwner.getWardName());
    updateCellValue(row, 8, collateralOwner.getDistrictName());
    updateCellValue(row, 10, collateralOwner.getProvinceName());

    row = sheet.getRow(389 + distance);
    // Dia chi lien lac
    updateCellValue(row, 4, collateralOwner.getContactAddress());
    updateCellValue(row, 6, collateralOwner.getContactWardName());
    updateCellValue(row, 8, collateralOwner.getContactDistrictName());
    updateCellValue(row, 10, collateralOwner.getContactProvinceName());
  }

  private void fillFieldSurvey(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(1076);
    if (CollectionUtils.isEmpty(data.getFieldSurveyItems())) {
      updateCellValue(row, 14, 0L);
      return;
    }
    int startRow = 1076;
    updateCellValue(row, 14, Long.valueOf(Math.min(data.getFieldSurveyItems().size(), 10)));
    int count = 0;
    for (FieldSurveyItem item : data.getFieldSurveyItems()) {
      if (count > 10) {
        return;
      }
      row = sheet.getRow(startRow + count);

      updateCellValue(row, 3, DateUtils.convertToSimpleFormat(item.getTime(), "dd/MM/yyyy"));
      if (item.getLocationType() != null) {
        updateCellValue(row, 4, item.getLocationType().getName());
      }
      if (item.getRelationshipType() != null) {
        updateCellValue(row, 5, item.getRelationshipType().getName());
      }
      updateCellValue(
          row,
          6,
          String.format(
              "%s, %s, %s, %s",
              StringUtils.isEmpty(item.getAddress()) ? "" : item.getAddress(),
              item.getWardName(),
              item.getDistrictName(),
              item.getProvinceName()));

      updateCellValue(row, 8, item.getFieldGuidePerson());
      if (item.getEvaluationResult() != null) {
        updateCellValue(row, 10, item.getEvaluationResult().getName());
      }
      count++;
    }
  }

  private void fillLoanApplicationItem(
      XSSFSheet sheet, CMSLoanApplicationReview data, LoanApplicationItem item, int distance) {

    XSSFRow row = sheet.getRow(1088 + distance);
    // Khoan vay
    updateCellValue(row, 4, "Thế chấp");

    row = sheet.getRow(1089 + distance);
    // tong nhu cau vay von
    updateCellValue(row, 4, item.getLoanAssetValue());
    // So tien vay
    updateCellValue(row, 7, item.getLoanAmount());

    row = sheet.getRow(1090 + distance);
    // muc dich vay von LOS
    if (LoanPurposeEnum.LAND.equals(item.getLoanPurpose())) {
      updateCellValue(row, 5, "73 - CHO VAY MUA NHA DE O");
    } else if (LoanPurposeEnum.TIEU_DUNG.equals(item.getLoanPurpose())) {
      updateCellValue(row, 5, "46 - TIEU DUNG CA NHAN KHAC");
    } else if (LoanPurposeEnum.XAY_SUA_NHA.equals(item.getLoanPurpose())) {
      updateCellValue(row, 5, "75 - CHO VAY XAY MOI,SUA CHUA NHA DE O");
    } else if (LoanPurposeEnum.HO_KINH_DOANH.equals(item.getLoanPurpose())) {
      if (LoanPurposeDetailEnum.VAY_BO_SUNG_VON_KINH_DOANH.equals(item.getLoanPurposeDetail())) {
        updateCellValue(row, 5, "55 - BO SUNG VON LUU DONG KHAC");
      } else if (LoanPurposeDetailEnum.VAY_DAU_TU_TAI_SAN_CO_DINH.equals(
          item.getLoanPurposeDetail())) {
        updateCellValue(row, 5, "50 - DAU TU KINH DOANH CA THE KHAC");
      }
    }

    row = sheet.getRow(1091 + distance);
    // muc dich vay von
    updateCellValue(row, 4, item.getRmReview());

    row = sheet.getRow(1092 + distance);
    // Hinh thuc cap tin dung
    if (LoanPurposeDetailEnum.VAY_BO_SUNG_VON_KINH_DOANH.equals(item.getLoanPurposeDetail())
        && LoanSupplementingBusinessCapitalEnum.VAY_HAN_MUC.equals(
        item.getLoanSupplementingBusinessCapital())) {
      updateCellValue(row, 5, "Hạn mức quay vòng");
    } else {
      updateCellValue(row, 5, "Cho vay từng lần");
    }
    // Thoi gian vay
    if (item.getLoanTime() != null) {
      updateCellValue(row, 10, item.getLoanTime().longValue());
    }

    row = sheet.getRow(1093 + distance);
    // Phuong thuc tra no
    if (item.getDebtPaymentMethod() != null) {
      updateCellValue(row, 4, item.getDebtPaymentMethod().getName());
    }
    // KUNN
    if (item.getDebtAcknowledgmentContractPeriod() != null) {
      updateCellValue(row, 10, String.valueOf(item.getDebtAcknowledgmentContractPeriod()));
    }
    row = sheet.getRow(1094 + distance);
    // Ma van ban san pham 1
    updateCellValue(row, 5, "QĐ.DF.006");
    if (item.getProductTextCode() != null) {
      updateCellValue(row, 6, item.getProductTextCode().getName());
    }

    updateCellValue(row, 10, item.getDocumentNumber2());

    row = sheet.getRow(1095 + distance);
    if (LoanPurposeEnum.LAND.equals(item.getLoanPurpose())) {
      List<OtherIncome> landsTransactions = data.getOtherIncomes().stream()
          .filter(otherIncome -> IncomeFromEnum.GOM_XAY.equals(otherIncome.getIncomeFrom()))
          .collect(
              Collectors.toList());
      if (!landsTransactions.isEmpty()) {
        if (ApprovalFlowEnum.NORMALLY.equals(
            data.getCommonIncome().getApprovalFlow())
            && RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(
            data.getCommonIncome().getRecognitionMethod1())) {
          if (item.getLoanTime() <= 12) {
            updateCellValue(row, 5, "RLNSTDFGX - GOM XÂY ONLINE - NGẮN HẠN");
          } else if (item.getLoanTime() <= 60) {
            updateCellValue(row, 5, "RLNMTDFGX - GOM XÂY ONLINE - TRUNG HẠN");
          } else {
            updateCellValue(row, 5, "RLNLTDFGX - GOM XÂY ONLINE - DÀI HẠN");
          }
        }
      } else {
        ProductCodeConfigEntity productCodeConfig = getProductCode(data, item);
        updateCellValue(row, 5,
            productCodeConfig == null ? "" : productCodeConfig.getProductCode());
      }
    } else {
      ProductCodeConfigEntity productCodeConfig = getProductCode(data, item);
      updateCellValue(row, 5, productCodeConfig == null ? "" : productCodeConfig.getProductCode());
    }
    // Ma san pham LOS
    // hold on -> OK

    row = sheet.getRow(1096 + distance);
    // Phuong thuc giai ngan
    if (item.getDisbursementMethod() != null) {
      updateCellValue(
          row,
          5,
          DisbursementMethodEnum.OTHER.equals(item.getDisbursementMethod())
              ? item.getDisbursementMethodOther()
              : item.getDisbursementMethod().getName());
    }

    row = sheet.getRow(1097 + distance);
    // Chương trình áp dụng lãi suất
    if (item.getInterestRateProgram() != null) {
      updateCellValue(row, 5, item.getInterestRateProgram().getName());
    }

    // Thoi gian an han no goc
    if (item.getGracePeriod() != null) {
      updateCellValue(row, 10, item.getGracePeriod().longValue());
    }

    row = sheet.getRow(1098 + distance);
    // Ma lai suat
    updateCellValue(row, 10, item.getInterestCode());

    int startRow = 1099;

    if (!CollectionUtils.isEmpty(item.getLoanItemCollateralDistributions())) {
      List<Collateral> collaterals = new ArrayList<>();
      for (LoanItemCollateralDistribution temp : item.getLoanItemCollateralDistributions()) {
        int index = HomeLoanUtil.getCollateralIndex(temp.getCollateralId(), data.getCollaterals());
        Collateral collateral = data.getCollaterals().get(index - 1);
        Collateral collateralTemp =
            Collateral.builder().fullName(String.format("Tài sản đảm bảo %s", index)).build();

        if (CollateralTypeEnum.PTVT.equals(collateral.getType())) {
          // Xe + Số khung + Số máy
          collateralTemp.setAddress(
              String.format(
                  "%s, %s, %s",
                  collateral.getDescription(),
                  collateral.getChassisNo(),
                  collateral.getEngineNo()));
        } else if (CollateralTypeEnum.GTCG.equals(collateral.getType())) {
          // Sổ tiết kiệm số <Số sổ tiết kiệm>
          collateralTemp.setAddress(
              String.format("Số tài khoản sổ tiết kiệm: %s", collateral.getDescription()));
        } else {
          collateralTemp.setAddress(
              String.format(
                  "%s, %s, %s, %s",
                  collateral.getAddress(),
                  collateral.getWardName(),
                  collateral.getDistrictName(),
                  collateral.getProvinceName()));
        }

        collaterals.add(collateralTemp);
      }

      collaterals =
          collaterals.stream()
              .sorted(Comparator.comparing(Collateral::getFullName))
              .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(collaterals)) {
        for (int i = 0; i < collaterals.size(); i++) {
          if (i > 4) {
            break;
          }
          row = sheet.getRow(startRow + i + distance);
          updateCellValue(row, 4, collaterals.get(i).getFullName());
          updateCellValue(row, 6, collaterals.get(i).getAddress());
        }
      }
    }
  }

  private void fillOverdraftItem(
      XSSFSheet sheet, Overdraft overdraft, int distance, CMSLoanApplicationReview data) {
    if (overdraft.getFormOfCredit().equals(FormOfCreditEnum.UNSECURED_OVERDRAFT)) {
      overdraftUnsecured(sheet, overdraft, distance);
    } else {
      overdraftSecured(sheet, overdraft, distance, data);
    }
  }

  private void overdraftSecured(
      XSSFSheet sheet, Overdraft overdraft, int distance, CMSLoanApplicationReview data) {
    log.info("Start fill overdraftSecured: {} from row {}", overdraft, 1088 + distance);
    XSSFRow row = sheet.getRow(1088 + distance);
    // Khoan vay
    updateCellValue(row, 4, "Thấu chi có TSBĐ");

    row = sheet.getRow(1089 + distance);
    // tong nhu cau vay von
    updateCellValue(row, 4, overdraft.getLoanAssetValue());
    // So tien vay
    updateCellValue(row, 7, overdraft.getLoanAmount());

    row = sheet.getRow(1090 + distance);
    updateCellValue(row, 5, "46 - TIEU DUNG CA NHAN KHAC");
    row = sheet.getRow(1091 + distance);
    // muc dich vay von
    updateCellValue(row, 4, overdraft.getOverdraftPurpose().getName());

    row = sheet.getRow(1092 + distance);
    // Hinh thuc cap tin dung

    updateCellValue(row, 5, "Cho vay từng lần");

    // Thoi gian vay
    if (overdraft.getLoanTime() != null) {
      updateCellValue(row, 10, overdraft.getLoanTime().longValue());
    }

    row = sheet.getRow(1093 + distance);
    // Phuong thuc tra no
    updateCellValue(row, 4, "Gốc trả cuối kỳ lãi trả hàng tháng");
    row = sheet.getRow(1094 + distance);
    // Ma van ban san pham 1
    updateCellValue(row, 5, "QĐ.DF.006");
    if (overdraft.getProductTextCode() != null) {
      updateCellValue(row, 6, overdraft.getProductTextCode().getName());
    }

    updateCellValue(row, 10, overdraft.getDocumentNumber2());

    // Ma san pham LOS

    log.info("fill account number for overdraft secured");
    row = sheet.getRow(1095 + distance);
    updateCellValue(row, 10, overdraft.getPaymentAccountNumber());

    log.info("fill product text code for overdraft secured");
    updateCellValue(row, 5, "THẤU CHI CÓ TSBĐ ONLINE");

    row = sheet.getRow(1096 + distance);
    // Phuong thuc giai ngan

    updateCellValue(row, 5, "Tiền mặt/Chuyển khoản");

    row = sheet.getRow(1097 + distance);
    // Chương trình áp dụng lãi suất
    updateCellValue(row, 5, "Theo quy định của MSB");

    row = sheet.getRow(1098 + distance);
    // Ma lai suat
    updateCellValue(row, 10, overdraft.getInterestCode());

    int startRow = 1099;

    if (!CollectionUtils.isEmpty(overdraft.getLoanItemCollateralDistributions())) {
      List<Collateral> collaterals = new ArrayList<>();
      for (LoanItemCollateralDistribution temp : overdraft.getLoanItemCollateralDistributions()) {
        int index = HomeLoanUtil.getCollateralIndex(temp.getCollateralId(), data.getCollaterals());
        Collateral collateral = data.getCollaterals().get(index - 1);
        Collateral collateralTemp =
            Collateral.builder().fullName(String.format("Tài sản đảm bảo %s", index)).build();

        if (CollateralTypeEnum.PTVT.equals(collateral.getType())) {
          // Xe + Số khung + Số máy
          collateralTemp.setAddress(
              String.format(
                  "%s, %s, %s",
                  collateral.getDescription(),
                  collateral.getChassisNo(),
                  collateral.getEngineNo()));
        } else if (CollateralTypeEnum.GTCG.equals(collateral.getType())) {
          // Sổ tiết kiệm số <Số sổ tiết kiệm>
          collateralTemp.setAddress(
              String.format("Số tài khoản sổ tiết kiệm: %s", collateral.getDescription()));
        } else {
          collateralTemp.setAddress(
              String.format(
                  "%s, %s, %s, %s",
                  collateral.getAddress(),
                  collateral.getWardName(),
                  collateral.getDistrictName(),
                  collateral.getProvinceName()));
        }

        collaterals.add(collateralTemp);
      }

      collaterals =
          collaterals.stream()
              .sorted(Comparator.comparing(Collateral::getFullName))
              .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(collaterals)) {
        for (int i = 0; i < collaterals.size(); i++) {
          if (i > 4) {
            break;
          }
          row = sheet.getRow(startRow + i + distance);
          updateCellValue(row, 4, collaterals.get(i).getFullName());
          updateCellValue(row, 6, collaterals.get(i).getAddress());
        }
      }
    }
  }

  private void overdraftUnsecured(XSSFSheet sheet, Overdraft overdraft, int distance) {
    log.info("Start fillOverdraftItem: {} from row: {}", overdraft, 1169 + distance);
    XSSFRow row = sheet.getRow(1168);
    updateCellValue(row, 14, 1L);
    row = sheet.getRow(1169 + distance);
    log.info("fill loan amount the column");
    updateCellValue(row, 4, overdraft.getLoanAmount());
    log.info("fill loan time the column");
    updateCellValue(row, 10, Long.valueOf(overdraft.getLoanTime()));
    row = sheet.getRow(1170 + distance);
    log.info("fill overdraft purpose the column");
    updateCellValue(row, 4, overdraft.getOverdraftPurpose().getName());
    log.info("fill document number 2 the column");
    updateCellValue(row, 10, "QĐ.DF.006");
    row = sheet.getRow(1171 + distance);
    log.info("fill InterestCode the column");
    updateCellValue(row, 4, overdraft.getInterestCode());
    log.info("fill Margin the column");
    updateCellValue(row, 6, overdraft.getMargin());
    log.info("fill PaymentAccountNumber the column");
    updateCellValue(row, 10, overdraft.getPaymentAccountNumber());
  }

  private void fillDeXuatDVKD(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(1193);
    // Cap ky de xuat tin dung
    if (data.getCreditAppraisal() != null
        && data.getCreditAppraisal().getSignatureLevel() != null) {
      updateCellValue(row, 5, data.getCreditAppraisal().getSignatureLevel().getName());
    }

    // Danh gia them cua don vi
    if (data.getCreditAppraisal() != null
        && !StringUtils.isEmpty(data.getCreditAppraisal().getBusinessReview())) {
      if (data.getCreditAppraisal().getBusinessReview().length() > 5000) {
        row = sheet.getRow(1197);
        updateCellValue(row, 3, data.getCreditAppraisal().getBusinessReview().substring(0, 5000));
        row = sheet.getRow(1198);
        updateCellValue(
            row,
            3,
            data.getCreditAppraisal()
                .getBusinessReview()
                .substring(5001, data.getCreditAppraisal().getBusinessReview().length()));
      } else {
        row = sheet.getRow(1197);
        updateCellValue(row, 3, data.getCreditAppraisal().getBusinessReview());
      }
    }
  }

  // hold on -> OK
  private ProductCodeConfigEntity getProductCode(
      CMSLoanApplicationReview data, LoanApplicationItem item) {

    if (data == null || item == null) {
      return null;
    }

    log.info("Start getProductCode: {}", data);
    int loanTime = item.getLoanTime() == null ? 0 : item.getLoanTime();
    String approvalFlow = "";
    if (data.getCommonIncome() != null && data.getCommonIncome().getApprovalFlow() != null) {
      approvalFlow = data.getCommonIncome().getApprovalFlow().getCode();
    }

    String incomeMethod = "";
    if (data.getCommonIncome() != null && data.getCommonIncome().getRecognitionMethod1() != null) {
      incomeMethod = data.getCommonIncome().getRecognitionMethod1().getCode();
    }
    String productType = "";
    if (item.getProductTextCode() != null) {
      productType = item.getProductTextCode().getCode();
    }

    if (LoanPurposeEnum.HO_KINH_DOANH.equals(item.getLoanPurpose())) {
      return getProductCodeHousehold(item.getLoanApplicationId(), item.getUuid());
    } else {
      log.info(
          "Start get productCodeConfig by productType: {}, loanTime: {}, approvalFlow: {}, incomeMethod: {}",
          productType,
          loanTime,
          approvalFlow,
          incomeMethod);
      List<ProductCodeConfigEntity> productCodeConfigs =
          productCodeConfigRepository.getProductConfig(
              productType, loanTime, loanTime, approvalFlow, incomeMethod);
      if (!CollectionUtils.isEmpty(productCodeConfigs)) {
        return productCodeConfigs.get(0);
      }
    }
    return null;
  }

  private void fillExceptionItem(XSSFSheet sheet, CMSLoanApplicationReview data) {
    XSSFRow row = sheet.getRow(1182);
    if (CollectionUtils.isEmpty(data.getExceptionItems())) {
      updateCellValue(row, 14, 0L);
      return;
    }
    int startRow = 1182;
    updateCellValue(row, 14, Long.valueOf(Math.min(data.getExceptionItems().size(), 10)));
    int count = 0;
    for (ExceptionItem item : data.getExceptionItems()) {
      if (count > 10) {
        return;
      }
      row = sheet.getRow(startRow + count);

      if (item.getCriterionGroup1() != null) {
        updateCellValue(row, 3, item.getCriterionGroup1().getName());
      }

      if (item.getCriteriaGroup2() != null) {
        updateCellValue(row, 4, item.getCriteriaGroup2().getName());
      }

      updateCellValue(row, 5, item.getDetail());
      updateCellValue(row, 6, item.getRegulation());
      updateCellValue(row, 7, item.getRecommendation());
      count++;
    }
  }

  private void fillCreditCard(XSSFSheet sheet, CMSLoanApplicationReview data) {
    List<CreditCard> creditCards = data.getCreditCards();
    XSSFRow row = sheet.getRow(1173);
    if (CollectionUtils.isEmpty(creditCards)) {
      updateCellValue(row, 14, 0L);
      return;
    }
    updateCellValue(row, 14, 1L);

    // Han muc the chinh
    List<CreditCard> mainCards =
        creditCards.stream()
            .filter(
                x ->
                    x.getCardPriority() != null
                        && x.getCardPriority().equals(CardPriorityEnum.PRIMARY_CARD))
            .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(mainCards)) {
      row = sheet.getRow(1174);
      CreditCard mainCard = mainCards.get(0);
      // han muc the chinh
      updateCellValue(row, 4, mainCard.getCreditLimit());
      // Thoi gian duy tri han muc
      updateCellValue(
          row, 11, mainCard.getTimeLimit() == null ? null : mainCard.getTimeLimit().longValue());

      row = sheet.getRow(1175);
      if (!StringUtils.isEmpty(mainCard.getCardPolicyCode())) {
        updateCellValue(row, 4, mainCard.getCardPolicyName());
      }

      if (!StringUtils.isEmpty(mainCard.getType())) {
        updateCellValue(row, 7, mainCard.getTypeName());
      }
    }

    // Han muc the phu
    List<CreditCard> subCards =
        creditCards.stream()
            .filter(
                x ->
                    x.getCardPriority() != null
                        && x.getCardPriority().equals(CardPriorityEnum.SECONDARY_CARD))
            .collect(Collectors.toList());

    if (!CollectionUtils.isEmpty(subCards)) {
      Optional<CreditCard> subCardMaxLimit =
          subCards.stream().max(Comparator.comparing(CreditCard::getCreditLimit));
      if (subCardMaxLimit.isPresent()) {
        row = sheet.getRow(1174);
        // Han muc the phu
        updateCellValue(row, 7, subCardMaxLimit.get().getCreditLimit());
      }
    }
  }

  private void updateCellValue(XSSFRow row, int col, String value) {
    Cell cell = row.getCell(col);
    if (cell == null) {
      return;
    }
    cell.setCellValue(value != null ? value : "");
    cell.setAsActiveCell();
  }

  private void updateCellValue(XSSFRow row, int col, Long value) {
    Cell cell = row.getCell(col);
    if (cell == null) {
      return;
    }
    cell.setCellValue(value != null ? value : 0);
    cell.setAsActiveCell();
  }

  private void updateCellValue(XSSFRow row, int col, Float value) {
    Cell cell = row.getCell(col);
    if (cell == null) {
      return;
    }
    cell.setCellValue(value != null ? value : 0);
    cell.setAsActiveCell();
  }

  private void updateCellValue(XSSFRow row, int col, Double value) {
    Cell cell = row.getCell(col);
    if (cell == null) {
      return;
    }
    cell.setCellValue(value != null ? value : 0);
    cell.setAsActiveCell();
  }

  private void updateCellValue(XSSFWorkbook workbook, XSSFRow row, int col, Float value) {
    Cell cell = row.getCell(col);
    if (cell == null) {
      return;
    }
    cell.setCellValue(value != null ? String.valueOf(value) : "");
    cell.setAsActiveCell();
  }

  private int getLoanPayerIndex(String payerId, List<LoanPayer> loanPayers) {
    if (CollectionUtils.isEmpty(loanPayers) || StringUtils.isEmpty(payerId)) {
      return 0;
    }
    try {
      int i =
          IntStream.range(0, loanPayers.size())
              .filter(index -> payerId.equals(loanPayers.get(index).getUuid()))
              .findFirst()
              .getAsInt();

      return i + 1;
    } catch (Exception ex) {
      return 0;
    }
  }

  private String getNguonThu(
      CMSLoanApplicationReview data, OwnerTypeEnum ownerType, String payerId) {
    if (!CollectionUtils.isEmpty(data.getSalaryIncomes())) {
      List<SalaryIncome> lstCheck =
          data.getSalaryIncomes().stream()
              .filter(
                  x ->
                      x.getOwnerType().equals(ownerType)
                          && (payerId == null || payerId.equals(x.getPayerId())))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstCheck)) {
        return Constants.ExportExcel.THU_NHAP_TU_LUONG;
      }
    }

    if (!CollectionUtils.isEmpty(data.getBusinessIncomes())) {
      List<BusinessIncome> lstCheck2 =
          data.getBusinessIncomes().stream()
              .filter(
                  x ->
                      x.getOwnerType().equals(ownerType)
                          && (payerId == null || payerId.equals(x.getPayerId())))
              .collect(Collectors.toList());

      if (!CollectionUtils.isEmpty(lstCheck2)) {
        return Constants.ExportExcel.TU_DOANH;
      }
    }

    return Constants.ExportExcel.THU_NHAP_KHAC;
  }

  private Map<String, String> getDiaChiCoQuan(
      CMSLoanApplicationReview data, OwnerTypeEnum ownerType) {
    Map<String, String> mapReturn = new HashMap<>();
    if (!CollectionUtils.isEmpty(data.getSalaryIncomes())) {
      List<SalaryIncome> lstCheck =
          data.getSalaryIncomes().stream()
              .filter(x -> x.getOwnerType().equals(ownerType))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstCheck)) {
        mapReturn.put(Address, lstCheck.get(0).getOfficeAddress());
        mapReturn.put(Province, lstCheck.get(0).getOfficeProvinceName());
        mapReturn.put(District, lstCheck.get(0).getOfficeDistrictName());
        mapReturn.put(Ward, lstCheck.get(0).getOfficeWardName());
        return mapReturn;
      }
    }

    if (!CollectionUtils.isEmpty(data.getBusinessIncomes())) {
      List<BusinessIncome> lstCheck2 =
          data.getBusinessIncomes().stream()
              .filter(x -> x.getOwnerType().equals(ownerType))
              .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(lstCheck2)) {
        mapReturn.put(Address, lstCheck2.get(0).getAddress());
        mapReturn.put(Province, lstCheck2.get(0).getProvinceName());
        mapReturn.put(District, lstCheck2.get(0).getDistrictName());
        mapReturn.put(Ward, lstCheck2.get(0).getWardName());
        return mapReturn;
      }
    }

    if (ownerType.equals(OwnerTypeEnum.ME)) {
      mapReturn.put(Address, data.getLoanApplication().getAddress());
      mapReturn.put(Province, data.getLoanApplication().getProvinceName());
      mapReturn.put(District, data.getLoanApplication().getDistrictName());
      mapReturn.put(Ward, data.getLoanApplication().getWardName());
      return mapReturn;
    }

    if (ownerType.equals(OwnerTypeEnum.MARRIED_PERSON)) {
      mapReturn.put(Address, data.getMarriedPerson().getAddress());
      mapReturn.put(Province, data.getMarriedPerson().getProvinceName());
      mapReturn.put(District, data.getMarriedPerson().getDistrictName());
      mapReturn.put(Ward, data.getMarriedPerson().getWardName());
      return mapReturn;
    }

    return null;
  }

  private CMSCicItem getBusinessCic(CMSLoanApplicationReview data, String identity) {
    if (StringUtils.isEmpty(identity)) {
      return null;
    }

    List<CMSGetCicInfo> cics = data.getCics();
    if (CollectionUtils.isEmpty(cics)) {
      return null;
    }

    List<CMSCicItem> cicItems = new ArrayList<>();
    for (CMSGetCicInfo cmsGetCicInfo : cics) {
      if (!CollectionUtils.isEmpty(cmsGetCicInfo.getCicItems())) {
        cicItems.addAll(cmsGetCicInfo.getCicItems());
      }
    }

    if (CollectionUtils.isEmpty(cicItems)) {
      return null;
    }

    List<CMSCicItem> cicItemTemps =
        cicItems.stream()
            .filter(x -> identity.equalsIgnoreCase(x.getIdentityCard()))
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(cicItemTemps)) {
      return null;
    }
    return cicItemTemps.get(0);
  }

  private ProductCodeConfigEntity getProductCodeHousehold(String loanId, String loanItemId) {
    List<ProductCodeConfig> productCodeConfigs =
        productCodeConfigService.getProductCodeConfigs(
            productCodeConfigService.getMapKeys(loanId, loanItemId));
    if (!CollectionUtils.isEmpty(productCodeConfigs)) {
      return ProductCodeConfigMapper.INSTANCE.toEntity(productCodeConfigs.get(0));
    }
    return null;
  }
}
