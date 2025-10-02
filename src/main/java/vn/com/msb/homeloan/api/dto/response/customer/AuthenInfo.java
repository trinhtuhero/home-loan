package vn.com.msb.homeloan.api.dto.response.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenInfo {

  // Số ID của yêu cầu giao dịch, là duy nhất trong mỗi yêu cầu giao dịch, phục vụ trace log
  @JsonProperty("req_id")
  private String reqId;
  // Mã của hệ thống sử dụng dịch vụ trên ESB
  @JsonProperty("req_app")
  private String reqApp;
  // Mã dịch vụ trên ESB
  @JsonProperty("srv")
  private String srv;
  // Thời gian gửi yêu cầu sang ESB
  @JsonProperty("req_time")
  private String reqTime;
  // User xác thực trên hệ thống ESB
  @JsonProperty("authorizer")
  private String authorizer;
  // Pasword của User trên hệ thống ESB
  @JsonProperty("password")
  private String password;

}
