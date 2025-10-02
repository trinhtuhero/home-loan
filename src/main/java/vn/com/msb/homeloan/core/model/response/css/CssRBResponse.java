package vn.com.msb.homeloan.core.model.response.css;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CssRBResponse {

  @JsonProperty(value = "scoreRM")
  private List<String> scoreRM;
  @JsonProperty(value = "approvalScore")
  private List<String> approvalScore;
  @JsonProperty(value = "rankRM")
  private List<String> rankRM;
  @JsonProperty(value = "approvalRank")
  private List<String> approvalRank;
}
