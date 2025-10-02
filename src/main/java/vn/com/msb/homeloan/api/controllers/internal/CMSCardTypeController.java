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
import vn.com.msb.homeloan.core.model.CardType;
import vn.com.msb.homeloan.core.service.CardTypeService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/card-type")
@RequiredArgsConstructor
public class CMSCardTypeController {

  private final CardTypeService cardTypeService;

  @GetMapping("")
  public ResponseEntity<ApiResource> getCardTypes() {
    List<CardType> models = cardTypeService.getCardTypes();
    ApiResource apiResource = new ApiResource(models, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
