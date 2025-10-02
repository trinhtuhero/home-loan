package vn.com.msb.homeloan.api.controllers.internal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.model.CardPolicy;
import vn.com.msb.homeloan.core.service.CardPolicyService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/card-policy")
@RequiredArgsConstructor
public class CMSCardPolicyController {

  private final CardPolicyService cardPolicyService;

  @GetMapping("")
  public ResponseEntity<ApiResource> getCardPolicies() {
    List<CardPolicy> models = cardPolicyService.getCardPolicies();
    ApiResource apiResource = new ApiResource(models, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
