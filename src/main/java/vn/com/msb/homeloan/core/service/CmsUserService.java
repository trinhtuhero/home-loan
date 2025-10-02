package vn.com.msb.homeloan.core.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;

public interface CmsUserService {

  CmsUser findByEmail(String email);

  CmsUserSearch cmsUserSearch(CmsUserSearchParam cmsUserSearchParam);

  byte[] importCmsUsers(MultipartFile multipartFile) throws IOException;

  CmsUser createCmsUser(CmsUser cmsUser);

  List<CmsUser> getAllCmsUser();

  List<String> getAllChildrenUser();

  List<String> getAllChildrenUser(HttpServletRequest httpRequest) throws IOException;
}
