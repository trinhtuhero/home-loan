package vn.com.msb.homeloan.api.controllers.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSCssResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CmsCssMapper;
import vn.com.msb.homeloan.api.dto.request.CMSCssRequest;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Css;
import vn.com.msb.homeloan.core.service.CssService;
import vn.com.msb.homeloan.core.util.DateUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/css")
@RequiredArgsConstructor
public class CMSCssController {

  private final CssService cssService;

  @PostMapping("/rb/scoreAndRank")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CSS_GET_SCORE_RB + "')")
  public ResponseEntity<ApiResource> getScoreRB(@RequestBody CMSCssRequest request)
      throws ApplicationException, ParseException {
    SimpleDateFormat format = new SimpleDateFormat(DateUtils.REQUEST_TIME_FORMAT);
    Css result = cssService.getScore(CmsCssMapper.INSTANCE.toModel(request),
        format.format(Date.from(Instant.now())));
    ApiResource apiResource = new ApiResource(CMSCssResponseMapper.INSTANCE.toCmsResponse(result),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CSS_GET_CSS_INFO + "')")
  public ResponseEntity<ApiResource> getCssInfo(@PathVariable String loanId) {
    Css result = cssService.getCss(loanId);
    ApiResource apiResource = new ApiResource(CMSCssResponseMapper.INSTANCE.toCmsResponse(result),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
