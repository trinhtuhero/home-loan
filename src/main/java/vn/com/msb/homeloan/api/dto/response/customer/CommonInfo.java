package vn.com.msb.homeloan.api.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonInfo {

  // Trạng thái ngày của core
  private String sysMode;
  // Mã chi nhánh được gắn với teller thực hiện giao dịch
  private String branchCode;
  // Kênh thực hiện giao dịch (tương ứng có thể là tên của hệ thống client)
  private String channel;
  // Tên hệ thống client
  private String hostName;
  // User thực hiện approve giao dịch
  private String manager;
  // Mã phòng giao dịch gắn với teller thực hiện giao dịch
  private String subBranchCode;
  // Teller thực hiện giao dịch
  private String teller;
  // Mã giao dịch trên core được gắn chân ghi có, ghi nợ
  private String tranCode;
  // Số sequence của giao dịch, số này duy nhất trong ngày
  private String tranSeq;
  //Ngày thực hiện giao dịch
  private String tranDate;

}
