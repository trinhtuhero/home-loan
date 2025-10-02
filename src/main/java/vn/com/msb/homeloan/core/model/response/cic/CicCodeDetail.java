package vn.com.msb.homeloan.core.model.response.cic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CIC code information
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class CicCodeDetail {

  @JsonProperty("cicCode")
  private String cicCode;
  @JsonProperty("customerName")
  private String customerName;
  private String address;
  @JsonProperty("uniqueId")
  private String uniqueId;
  @JsonProperty("taxCode")
  private String taxCode;
  @JsonProperty("businessRegNum")
  private String businessRegNum;

  public CicCodeDetail(String cicCode, String customerName, String address) {
    this.cicCode = cicCode;
    this.customerName = customerName;
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof CicCodeDetail)) {
      return false;
    }
    final CicCodeDetail detail = (CicCodeDetail) o;
    if (detail.cicCode == null) {
      return false;
    }
    return this.cicCode.equals(detail.cicCode);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
