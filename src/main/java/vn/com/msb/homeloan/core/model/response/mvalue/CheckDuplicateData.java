package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckDuplicateData {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private List<Asset> asset;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Asset {

    @JsonProperty("maTS")
    private String maTS;

    @JsonProperty("dvthdg")
    private String dvthdg;

    @JsonProperty("dvkd_id")
    private String dvkdId;

    @JsonProperty("maKH")
    private String maKH;

    @JsonProperty("dvkd")
    private String dvkd;

    @JsonProperty("statusz")
    private String statusz;

    @JsonProperty("soPhuLuc")
    private String soPhuLuc;

    @JsonProperty("tenKH")
    private String tenKH;

    @JsonProperty("soTBDG")
    private String soTBDG;

    @JsonProperty("tenTSDG")
    private String tenTSDG;
  }
}
