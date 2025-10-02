package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.model.Organization;

public interface OrganizationService {

  List<Organization> getByType(String type);

  List<Organization> getDVKD(String area, List<OrganizationEntity> list);
}
