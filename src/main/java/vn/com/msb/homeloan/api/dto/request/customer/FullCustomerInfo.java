package vn.com.msb.homeloan.api.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullCustomerInfo {

  // Số cif của khách hàng
  private String cifNumber;
  // Trạng thái của cif
  private String status;
  // Mã ngân hàng
  private String bankCode;
  // Mã chi nhánh gắn với cif khách hàng
  private String branchCode;
  // Tên ngắn của khách hàng
  private String shortName;
  // Tên của KH
  private String customerName;
  // Tên đầy đủ của khách hàng
  private String addCustomerName;
  // Tên thuở nhỏ
  private String motherMaidenName;
  // Có phải địa chỉ nước ngoài không
  private String foreignAddress;
  // Địa chỉ của KH
  private String addressLine1;
  // Địa chỉ của KH
  private String addressLine2;
  // Địa chỉ của KH
  private String addressLine3;
  // Địa chỉ của KH
  private String addressLine4;
  // Địa chỉ của KH
  private String addressLine5;
  // Mã bưu điện tỉnh/thành phố
  private String provincePostalCode;
  // Cờ đánh dấu cá nhân hay doanh nghiệp
  private String individual;
  // Số giấy tờ tùy thân của khách hàng
  private String idNo;
  // Loại giấy tờ tùy thân
  private String idType;
  // Ngày cấp giấy tờ tùy thân
  private String issuedDate;
  // Nơi cấp giấy tờ tùy thân
  private String issuedPlace;
  // Quốc gia cấp giấy tờ tùy thân
  private String issuedCountry;
  // Ngày thành lập doanh nghiêp
  private String dateOfBirthOrIncorp;
  // Nơi đăng ký
  private String placeOfBirthOrRegistration;
  // Quốc gia
  private String nationality;
  // Mã dân tộc
  private String raceCode;
  // Giới tính
  private String gender;
  // Tình trạng hôn nhân
  private String maritalStatus;
  // Ngày kết hôn
  private String maritalDate;
  // Điện thoại nhà
  private String homePhoneNo;
  // Điện thoại văn phòng
  private String offcicePhoneNo;
  // Điện thoại cầm tay
  private String handPhoneNo;
  // Mã cư trú
  private String residentCode;
  // Email
  private String email;
  // Điện thoại di động
  private String mobilePhone;
  // Số fax
  private String fax;
  // Mã số thuế
  private String tax;
  // Ngày review mở CIF
  private String reviewDate;
  // Loại hình doanh nghiệp
  private String constOrBusinessType;
  //
  private String combineCycle;
  //
  private String inquiryIDCode;
  // Ngân hàng quản lý tín dụng
  private String sfTemp1;
  // Khách hàng tổ chức-RB
  private String sfTemp2;
  // Doanh nghiệp vừa và nhỏ
  private String sfTemp3;
  // Tổ chức tài chính và ngân hàng
  private String sfTemp4;
  // Khách hàng FDI
  private String sfTemp5;
  // Doanh nghiệp lớn
  private String sfTemp6;
  // Doanh nghiệp siêu nhỏ (SSE)
  private String sfTemp7;
  // Ngân hàng đại chúng
  private String sfTemp8;

  private String sfCode1;

  private String sfCode2;

  private String sfCode4;

  private String sfCode5;

  private String sfCode6;

  private String sfCode7;

  private String sfCode8;
  // Khách hàng doanh nghiệp vừa (MC)
  private String cusInfTem1;
  // Ban KHDNNN (SOE)
  private String cusInfTem2;
  // Ngành khách hàng LC
  private String cusInfTem3;
  // Lĩnh vực kinh tế khách hàng SME
  private String cusInfTem4;
  // Hình thức sở hữu
  private String cusInfTem5;
  // Doanh thu thực tế khách hàng (tỷ đồng)
  private String cusInfTem6;

  private String cusInfTem7;

  private String cusInfTem8;

  private String cusInfCode1;

  private String cusInfCode2;

  private String cusInfCode3;

  private String cusInfCode4;

  private String cusInfCode5;

  private String cusInfCode6;

  private String cusInfCode7;

  private String cusInfCode8;

  private String religionCode;

  private String langIndentifier;

  private String customerRatingCode;

  private String customerStatus;

  private String vipCode;

  private String insiderCode;

  private String salutation;

  private String businessOrOccuptionCode;

  private String dateOfBirthOrIncorpDate8;

}
