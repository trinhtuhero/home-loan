package vn.com.msb.homeloan.core.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.msb.homeloan.core.model.CustomUserDetails;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;

@Slf4j
public class AuthorizationUtil {

  public static boolean containPermission(List<Role> roles, String permissionStr) {
    Set<String> permissionSet = new HashSet<>();
    roles.forEach(role -> {
      role.getPermissions().forEach(permission -> permissionSet.add(permission.getName()));
    });
    if (permissionSet.contains(permissionStr)) {
      return true;
    }
    return false;
  }

  public static boolean containRole(List<Role> roles, String roleStr) {
    if (CollectionUtils.isEmpty(roles)) {
      return false;
    }
    Set<String> roleSet = new HashSet<>();
    roles.forEach(role -> roleSet.add(role.getName()));
    if (roleSet.contains(roleStr)) {
      return true;
    }
    return false;
  }

  public static String getEmail() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String email = null;
    if (principal instanceof UserDetails) {
      email = ((CustomUserDetails) principal).getUser().getEmail();
    }
    return email;
  }

  public static String getToken() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String token = null;
    if (principal instanceof UserDetails) {
      token = ((CustomUserDetails) principal).getUser().getToken();
    }
    return token;
  }

  public static String getUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = null;
    if (principal instanceof UserDetails) {
      userId = ((CustomUserDetails) principal).getUser().getUserId();
    }
    return userId;
  }
}
