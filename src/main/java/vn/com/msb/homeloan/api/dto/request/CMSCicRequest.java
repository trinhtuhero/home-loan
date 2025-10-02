package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSCicRequest {

  @Valid
  @NotNull
  private List<Info> infos;

  @NotBlank
  String loanApplicationId;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Info {

    @NotNull
    List<@NotBlank String> identityCards;

    @NotNull
    CICRelationshipTypeEnum relationshipType;

    @NotBlank
    String customerName;
  }
}
