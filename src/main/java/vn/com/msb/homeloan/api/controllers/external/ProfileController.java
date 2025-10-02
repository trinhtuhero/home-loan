package vn.com.msb.homeloan.api.controllers.external;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.ProfileResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.UpdateProfileRequestMapper;
import vn.com.msb.homeloan.api.dto.request.UpdateProfileRequest;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.jwt.JwtTokenProvider;
import vn.com.msb.homeloan.core.model.Profile;
import vn.com.msb.homeloan.core.service.ProfileService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  private final JwtTokenProvider jwtTokenProvider;

  @PutMapping("")
  public ResponseEntity<ApiResource> updateProfile(@Valid @RequestBody UpdateProfileRequest request,
      HttpServletRequest httpRequest) {

    if (!jwtTokenProvider.getUserTokenFromJWT(
            httpRequest.getHeader("Authorization").replace(Constants.HEADER_AUTHORIZATION_BEARER, ""))
        .getUserId().equals(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.ACCESS_DENIED);
    }

    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    Profile profile = UpdateProfileRequestMapper.INSTANCE.toProfileModel(request);
    ApiResource apiResource = new ApiResource(ProfileResponseMapper.INSTANCE.toDto(
        profileService.updateProfile(profile)), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("")
  public ResponseEntity<ApiResource> getProfileById(@RequestParam String profileId) {
    ApiResource apiResource = new ApiResource(ProfileResponseMapper.INSTANCE.toDto(
        profileService.getProfileById(profileId)), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
