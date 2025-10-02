package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OrganizationStatusEnum;
import vn.com.msb.homeloan.core.constant.OrganizationTypeEnum;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Organization;
import vn.com.msb.homeloan.core.model.mapper.OrganizationMapper;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.service.OrganizationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  @Override
  public List<Organization> getByType(String type) {
    if (StringUtils.isEmpty(type)
        || (!type.equalsIgnoreCase(OrganizationTypeEnum.DVKD.getCode())
        && !type.equalsIgnoreCase(OrganizationTypeEnum.AREA.getCode()))) {
      throw new ApplicationException(ErrorEnum.ORGANIZATION_MUST_MATCH_PATTERN);
    }
    return OrganizationMapper.INSTANCE.toModels(
        organizationRepository.findByTypeAndStatus(type, OrganizationStatusEnum.ACTIVE.getCode()));
  }

  @Override
  public List<Organization> getDVKD(String area, List<OrganizationEntity> returnLst) {
    // danh sách childs trực tiếp
    List<OrganizationEntity> directChilds = organizationRepository.findByAreaCodeAndStatus(area,
        OrganizationStatusEnum.ACTIVE.getCode());
    for (OrganizationEntity org : directChilds) {
      if ("DVKD".equals(org.getType())) {
        returnLst.add(org);
      } else {
        getDVKD(org.getCode(), returnLst);
      }
    }
    return OrganizationMapper.INSTANCE.toModels(returnLst);
  }

}
