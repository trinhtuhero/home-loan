package vn.com.msb.homeloan.api.controllers.internal;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CMSTabAction;
import vn.com.msb.homeloan.core.service.CMSTabActionService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/tab-action")
@RequiredArgsConstructor
public class CMSTabActionController {

  private final CMSTabActionService cmsTabActionService;

  @GetMapping("/{loanId}/list")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_TAB_ACTION_GET_BY_LOANID
          + "')")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<CMSTabAction> tabActionList = cmsTabActionService.getByLoanId(loanId);
    ApiResource apiResource = new ApiResource(tabActionList, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

}
