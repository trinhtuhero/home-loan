package vn.com.msb.homeloan.core.service;

import java.io.IOException;
import java.util.List;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;

public interface CommonCMSService {

  List<Role> getAuthorInfo(String token) throws IOException;
}
