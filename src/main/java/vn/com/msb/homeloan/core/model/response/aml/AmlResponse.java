package vn.com.msb.homeloan.core.model.response.aml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
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
public class AmlResponse {

  @JsonProperty("status")
  private String status;

  @JsonProperty("data")
  private DataAml dataAml;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class DataAml {

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("inforError")
    private String inforError;
    @JsonProperty("data")
    private List<DataCheckAml> data;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class DataCheckAml {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("dataSource")
    private String dataSource;
    @JsonProperty("idPassport")
    private String idPassport;
    @JsonProperty("name")
    private String name;
    @JsonProperty("remark")
    private String remark;
  }
}
