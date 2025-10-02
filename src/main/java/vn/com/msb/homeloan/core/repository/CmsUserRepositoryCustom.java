package vn.com.msb.homeloan.core.repository;

import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;

@Repository
public interface CmsUserRepositoryCustom {

  CmsUserSearch cmsUserSearch(CmsUserSearchParam param);
}
