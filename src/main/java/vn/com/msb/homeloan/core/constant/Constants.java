package vn.com.msb.homeloan.core.constant;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Constants {

  private Constants() {
    throw new IllegalStateException("Constants class");
  }

  public static final String CJ_NAME = "CJ4";
  public static final String SERVICE_NAME = "homeloan_service";
  public static final String UPLOAD_SERVICE_NAME = "file_management_service";
  public static final BigDecimal ONE_MILLION = BigDecimal.valueOf(1000000);
  public static final BigDecimal COEFFICIENT_CREDIT_CARD_CIC = BigDecimal.valueOf(0.05);
  public static final String DU_NO_NGAN_HAN = "DuNoNganHan";
  public static final String DU_NO_TRUNG_HAN = "DuNoTrungHan";
  public static final String DU_NO_DAI_HAN = "DuNoDaiHan";
  public static final String DU_NO_KHAC = "DuNoKhac";
  public static final String DU_NO_BO_SUNG = "DuNoBoSung";
  public static final String MSB_NAME = "Ngân hàng TMCP Hàng hải Việt Nam";
  public static final String CREDIT_INSTITUTION_MSB_CODE = "MSB";

  public static final int TIME_ZONE_GMT_7 = 7;

  // Header
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String HEADER_AUTHORIZATION_BEARER = "Bearer ";
  public static final int STATUS_INACTIVE = 0;
  public static final int STATUS_ACTIVE = 1;

  public static final String SCOPE_PRIVATE = "PRIVATE";
  public static final String SCOPE_PUBLIC = "PUBLIC";

  public static final int UPLOAD_FILE_PRIORITY_TEMP = 0;
  public static final int UPLOAD_FILE_PRIORITY_STRUCTURE = 1;

  public static final String JWT_KEY_USER_ID = "userId";
  public static final String JWT_KEY_PHONE_NUMBER = "phoneNumber";
  public static final String JWT_KEY_IDENTIFICATION_NUMBER = "idNumber";
  public static final String JWT_KEY_UUID = "uuid";

  public static final String PREFIX_SEND_OTP_RATE = "SEND_OTP_RATE_";
  public static final String PREFIX_CONFIRM_OTP_RATE = "CONFIRM_OTP_RATE_";
  public static final String PREFIX_AUTHENTICATION = "AUTHENTICATION_";
  public static final String PREFIX_BLACKLIST = "BLACKLIST_";
  public static final String FILE_NAME_DNVV = "de_nghi_vay_von.pdf";
  public static final String FILE_NAME_CIC = "thong_tin_cic.pdf";
  public static final String FILE_NAME_DMHS = "danh_muc_ho_so_can_cung_cap.pdf";
  public static final String ZIP_PREFIX_HSPHUONGAN = "HSPHUONGAN";

  public static final String DD_MM_YYYY_FLASH = "dd/MM/yyyy";
  public static final String MM_DD_YYYY_FLASH = "MM-dd-yyyy";

  public static final List<String> ACTUAL_RECEIVED_DIRECT_CATEGORY_CODES = Arrays.asList("002-001",
      "002-002", "002-003", "002-004");
  public static final List<String> EXCHANGE_DIRECT_CATEGORY_CODES = Arrays.asList("002-005",
      "002-006");
  public static final String FILE_CONFIG_CODE_DNVV = "004-006";
  public static final String VIET_NAM = "Việt Nam";

  public static final String TAB_COLLATERAL_INVALID_CODE_1 = "001";
  public static final String TAB_COLLATERAL_INVALID_CODE_2 = "002";

  // Tài sản tích lũy
  public static final List<String> TSTL_FILE_CONFIG_CODES = Arrays.asList("002-005-001",
      "002-005-002", "002-005-003");

  public static final List<String> FILE_CATEGORIES_NEED_TO_COPY = Arrays.asList(
      "001-001" // Thông tin khách hàng
      , "001-002" // Thông tin Vợ/Chồng KH
      , "002-001" //Nguồn thu từ lương
      , "002-002" // Nguồn thu từ doanh nghiệp
      , "002-003" // Nguồn thu từ hộ kinh doanh
      , "002-004" // Nguồn thu khác
      , "002-005" // Nguồn thu từ tài sản tích lũy
      , "002-006" // Nguồn thu giả định khác
  );

  // Configuration
  public static final String HOME_PAGE_CONFIG = "homepage";
  public static final String BAT_DONG_SAN_CONFIG = "batdongsan";
  public static final String TIEU_DUNG_CONFIG = "tieudung";
  public static final String XAY_SUA_NHA_CONFIG = "xaysuanha";

  public static final String HOUSEHOLD = "household";
  public static final String HEADER_FOOTER_CONFIG = "header-footer";
  public static final String LOAN_CALCULATION_CONFIG = "loan-calculation";

  public static class ImportCmsUser {

    public static final String SHEET_NAME = "CMS_USER";

    public static final String STT = "STT";
    public static final String EMPL_ID = "Mã nhân viên";
    public static final String FULL_NAME = "Họ và tên";
    public static final String EMAIL = "Địa chỉ email";
    public static final String PHONE_NUMBER = "Số điện thoại";
    public static final String BRANCH_CODE = "Chi nhánh";
    public static final String LEADER = "Quản lý";
    public static final String ROLE = "Quyền";
    public static final String ERROR = "Lỗi";

    public static final Integer MAX_LENGTH_EMPL_ID = 10;
    public static final Integer MAX_LENGTH_FULL_NAME = 150;
    public static final Integer MAX_LENGTH_EMAIL = 100;

    public static final String ERROR_EMPL_ID_NOT_EMPTY = "Mã nhân viên không được để trống";
    public static final String ERROR_EMPL_ID_EXCEED_MAX_LENGTH = "Mã nhân viên không được vượt quá %s ký tự";

    public static final String ERROR_FULL_NAME_NOT_EMPTY = "Họ và tên không được để trống";
    public static final String ERROR_FULL_NAME_EXCEED_MAX_LENGTH = "Họ và tên không được vượt quá %s ký tự";

    public static final String ERROR_EMAIL_NOT_EMPTY = "Địa chỉ email không được để trống";
    public static final String ERROR_EMAIL_EXCEED_MAX_LENGTH = "Địa chỉ email không được vượt quá %s ký tự";
    public static final String ERROR_EMAIL_INVALID = "Địa chỉ email không hợp lệ";
    public static final String ERROR_EMAIL_EXIST = "Địa chỉ email %s đã tồn tại";

    public static final String ERROR_LEADER_EMAIL_NOT_EMPTY = "Quản lý không được để trống";
    public static final String ERROR_LEADER_EMAIL_EXCEED_MAX_LENGTH = "Quản lý không được vượt quá %s ký tự";

    public static final String ERROR_EMPLID_DUPLICATE = "Mã nhân viên bị trùng với dòng thứ %s";

    public static final String ERROR_EMAIL_DUPLICATE = "Email bị trùng với dòng thứ %s";
    public static final String ERROR_LEADER_EMAIL_INVALID = "Quản lý không hợp lệ";


    public static final String ERROR_LEADER_EMAIL_NOT_EXIT = "Quản lý %s không tồn tại";

    public static final String ERROR_PHONE_NOT_EMPTY = "Số điện thoại không được để trống";
    public static final String ERROR_PHONE_INVALID = "Số điện thoại không hợp lệ";

    public static final String ERROR_BRANCH_CODE_NOT_EMPTY = "Chi nhánh không được để trống";
    public static final String ERROR_BRANCH_CODE_NOT_FOUND = "Chi nhánh %s không tồn tại";

    public static final String ERROR_ROLE_NOT_EMPTY = "Quyền không được để trống";
    public static final String ERROR_ROLE_INVALID = "Quyền %s không hợp lệ. Chỉ bao gồm các giá trị sau CJ5-ADMIN/CJ5-RM/CJ5-BM";
  }

  public static class ExportLoanApplication {

    public static final String SHEET_NAME = "LOAN_APPLICATION_INFO";

    public static final String STT = "STT";
    public static final String LOAN_CODE = "Mã hồ sơ";
    public static final String FULL_NAME = "Họ và tên";
    public static final String PHONE_NUMBER = "Số điện thoại";
    public static final String ID_NO = "CMND/CCCD";
    public static final String LOAN_PURPOSE = "Mục đích vay";
    public static final String LOAN_AMOUNT = "Số tiền";
    public static final String PIC_RM = "Cán bộ xử lý";
    public static final String BRANCH_CODE = "Chi nhánh";
    public static final String STATUS = "Trạng thái hồ sơ";
    public static final String RECEIVE_DATE = "Ngày tiếp nhận";
  }

  public static class ExportReportTAT {

    public static final String SHEET_NAME = "REPORT_TAT";

    public static final String STT = "STT";
    public static final String LOAN_CODE = "Mã hồ sơ";
    public static final String BRANCH_CODE = "Mã phòng giao dịch";
    public static final String BRANCH_NAME = "Phòng giao dịch";
    public static final String LOAN_PURPOSE = "Mục đích ";
    public static final String FULL_NAME = "Tên khách hàng";
    public static final String ID_NO = "Số CMND/CCCD";
    public static final String LOAN_AMOUNT = "Số tiền vay";
    public static final String PIC_RM = "RM xử lý";
    public static final String PIC_RM_EMAIL = "Email RM xử lý hồ sơ";
    public static final String REQUEST_DATE = "Ngày khách hàng đề nghị";
    public static final String RECEIVE_DATE = "Ngày tiếp nhận phân bổ";
    public static final String SUBMITTED_DATE = "Ngày hoàn thành tờ trình";
    public static final String STATUS = "Trạng thái hồ sơ";
  }

  public static class ExportReportFeedback {

    public static final String SHEET_NAME = "REPORT_FEEDBACK";

    public static final String STT = "STT";
    public static final String LOAN_CODE = "Mã hồ sơ";
    public static final String BRANCH_CODE = "Mã phòng giao dịch";
    public static final String BRANCH_NAME = "Phòng giao dịch";
    public static final String LOAN_PURPOSE = "Mục đích ";
    public static final String FULL_NAME = "Tên khách hàng";
    public static final String ID_NO = "Số CMND/CCCD";
    public static final String LOAN_AMOUNT = "Số tiền vay";
    public static final String PIC_RM = "RM xử lý";
    public static final String PIC_RM_EMAIL = "Email RM xử lý hồ sơ";
    public static final String RATE = "Đánh giá của Khách hàng";
    public static final String ADDITIONAL_COMMENT = "Chi tiết đánh giá";
    public static final String STATUS = "Trạng thái hồ sơ";
  }

  public static class ZipLoanApplication {

    // Dictionary
    public static final String HSPL_LEVEL_1 = "RB001. Ho so phap ly";
    public static final String HSPL_LEVEL_2 = "RPL21. Giay to cu tru";
    public static final String HSPL_ZIP_FOLDER = HSPL_LEVEL_1 + "/" + HSPL_LEVEL_2 + "/";
    public static final String HSPL_ZIP_PREFIX_1 = "HSPL_KHACHHANG";
    public static final String HSPL_ZIP_PREFIX_2 = "HSPL_NGUOIHONPHOI";
    public static final String HSPL_ZIP_PREFIX_3 = "HSPL_NGUOIBAOLANH";

    public static final String HSNT_LEVEL_1 = "RB004. Ho so nguon thu";
    public static final String HSNT_LEVEL_2 = "RTC00. Ho so thu nhap";
    public static final String HSNT_ZIP_FOLDER = HSNT_LEVEL_1 + "/" + HSNT_LEVEL_2 + "/";
    public static final String HSNT_ZIP_PREFIX_1 = "HSNT_LUONG";
    public static final String HSNT_ZIP_PREFIX_2 = "HSNT_DOANHNGHIEP";
    public static final String HSNT_ZIP_PREFIX_3 = "HSNT_KINHDOANH";
    public static final String HSNT_ZIP_PREFIX_4 = "HSNT_KHAC";
    public static final String HSNT_ZIP_PREFIX_5 = "HSNT_TICHLUY";
    public static final String HSNT_ZIP_PREFIX_6 = "HSNT_GIADINHKHAC";

    public static final String HSTSBĐ_LEVEL_1 = "RB006. Ho so TSBD";
    public static final String HSTSBĐ_LEVEL_2 = "RBDS1. Ho so Bat dong san";
    public static final String HSTSBĐ_ZIP_FOLDER = HSTSBĐ_LEVEL_1 + "/" + HSTSBĐ_LEVEL_2 + "/";
    public static final String HSTSBĐ_ZIP_PREFIX_1 = "HSTSBĐ_TAISAN";
    public static final String HSTSBĐ_ZIP_PREFIX_2 = "HSTSBĐ_PHAPLY";

    public static final String HSVV_LEVEL_1 = "RB003. Ho so vay von";
    public static final String HSVV_LEVEL_2 = "RVV01. Ho so muc dich su dung von";
    public static final String HSVV_ZIP_FOLDER = HSVV_LEVEL_1 + "/" + HSVV_LEVEL_2 + "/";
    public static final String HSVV_ZIP_PREFIX_1 = "HSPHUONGAN";

    public static final String HSQHTD_LEVEL_1 = "RB002. Ho so QHTD";
    public static final String HSQHTD_LEVEL_2 = "RQH01.Ho so QHTD";
    public static final String HSQHTD_ZIP_FOLDER = HSQHTD_LEVEL_1 + "/" + HSQHTD_LEVEL_2 + "/";
    public static final String HSQHTD_ZIP_PREFIX_1 = "HSQHTD_NGHIAVUTRANO";
    public static final String HSQHTD_ZIP_PREFIX_2 = "HSQHTD_CIC";

    public static final String HSK_LEVEL_1 = "RB011. Ho so khac";
    public static final String HSK_LEVEL_2 = "RHSK1. Ho so khac";
    public static final String HSK_ZIP_FOLDER = HSK_LEVEL_1 + "/" + HSK_LEVEL_2 + "/";
    public static final String HSK_ZIP_PREFIX_1 = "HSKHAC_THUCDIA";
    public static final String HSK_ZIP_PREFIX_2 = "HSKHAC_HSKHAC";
  }


  public static class ExportExcel {

    public static final String BAT_DONG_SAN = "Bất động sản";
    public static final String PTVT = "Phương tiện vận tải";
    public static final String GTCG = "Giấy tờ có giá";
    public static final String CONG_AN = "Công an";
    public static final String THU_NHAP_KHAC = "Thu nhập khác";
    public static final String THU_NHAP_TU_LUONG = "Lương";
    public static final String TU_DOANH = "Tự doanh";
    public static final String NGANH_NONG_LAM_NGHIEP = "A001 - NGANH NONG LAM NGHIEP";
    public static final String CCCD = "CCCD";
    public static final String CMND = "CMND";
    public static final String TRINH_SO_BO = "Trình sơ bộ";
    public static final String CHUA_CO = "Chưa có";
  }

  public static class CJ4 {

    public static final String PRODUCT_ID = "PRU_FLEX_INVEST";
    public static final long YEARLY_FEE = 50000000;
    public static final boolean IS_GENILLUSTRATE = false;
  }
}

