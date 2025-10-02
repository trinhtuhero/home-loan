package vn.com.msb.homeloan.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class CssRequest {

  @JsonProperty(value = "common")
  private CssCommon common;

  @JsonProperty(value = "info")
  private CssInfo info;
}
