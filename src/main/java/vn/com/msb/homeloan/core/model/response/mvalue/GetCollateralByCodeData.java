package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCollateralByCodeData {

  @JsonProperty("response")
  private Response response;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Response {

    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("assetInfo")
    private List<AssetInfo> assetInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssetInfo {

      @JsonProperty("date")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
      private Date date;

      @JsonProperty("owner")
      private String owner;

      @JsonProperty("legalPaper")
      private LegalPaper legalPaper;

      @JsonProperty("geoDistrict")
      private IdName geoDistrict;

      @JsonProperty("statusZ")
      private String statusZ;

      @JsonProperty("valuer")
      private String valuer;

      @JsonProperty("geoProvince")
      private IdName geoProvince;

      @JsonProperty("geoWard")
      private IdName geoWard;

      @JsonProperty("ownerRelationship")
      private String ownerRelationship;

      @JsonProperty("fileLink")
      private String fileLink;

      @JsonProperty("valuation")
      private Long valuation;

      @JsonProperty("valuationNotice")
      private String valuationNotice;

      @JsonProperty("assetName")
      private String assetName;

      @JsonProperty("geoStreet")
      private IdName geoStreet;

      @JsonProperty("id")
      private Long id;

      @JsonProperty("detail")
      private String detail;

      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      public static class LegalPaper {

        @JsonProperty("entryNo")
        private String entryNo;

        @JsonProperty("issuedDate")
        private String issuedDate;

        @JsonProperty("ownershipCertificateNO")
        private String ownershipCertificateNO;

        @JsonProperty("issuedBy")
        private String issuedBy;
      }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdName {

      @JsonProperty("id")
      private Long id;

      @JsonProperty("name")
      private String name;
    }
  }
}
