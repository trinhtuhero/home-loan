package vn.com.msb.homeloan.core.util;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.service.CommonCMSService;

@Component
@AllArgsConstructor
public class CustomPreAuthorizer {

  private HttpServletRequest request;

  private CommonCMSService commonCMSService;

  private CacheManager cacheManager;

  public boolean hasPermission(String permissionStr) throws IOException {
    List<Role> roles = commonCMSService.getAuthorInfo(request.getHeader("Authorization"));
    String email = AuthorizationUtil.getEmail();
    // Put to cache
    roles.forEach(role -> cacheManager.getCache("rolesCache").put(email, roles));
    if (AuthorizationUtil.containPermission(roles, permissionStr)) {
      return true;
    } else {
      throw new ApplicationException(ErrorEnum.ACCESS_DENIED);
    }
  }
}
