package vn.com.msb.homeloan.core.model.response.cic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;


/**
 * CIC query information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CicQueryData {

  @NotNull
  private Integer code;
  //  private String message;
//  private CicQueryErrorCode category;
  private CicQueryDetail value;
  @JsonProperty("transactionTime")
  private long transactionTime;
//  private Boolean active;
//  private String status;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CicQueryDetail {

    @JsonProperty("requestTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate requestTime;

    @NotNull
    @JsonProperty("clientQuestionId")
    private Integer clientQuestionId;

    @NotNull
    @JsonProperty("uniqueId")
    private String uniqueId;

    private String content;

    @NotNull
    private CicQueryStatusEnum status;

    @JsonProperty("h2hResponseTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate h2hResponseTime;

    private CicGroupEnum typeDebt;
    private CicGroupEnum typeDebt12;
    private CicGroupEnum typeDebt24;
  }
}
