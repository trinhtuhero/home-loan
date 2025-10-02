package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OrganizationStatusEnum;
import vn.com.msb.homeloan.core.constant.OrganizationTypeEnum;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Organization;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.service.OrganizationService;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

  OrganizationService organizationService;

  @Mock
  OrganizationRepository organizationRepository;

  @BeforeEach
  void setUp() {
    this.organizationService = new OrganizationServiceImpl(
        organizationRepository);
  }

  @Test
  void givenValidInput_ThenGetByType_shouldReturnSuccess() {
    List<OrganizationEntity> organizationEntities = new ArrayList<>();
    organizationEntities.add(OrganizationEntity.builder().build());
    organizationEntities.add(OrganizationEntity.builder().build());
    organizationEntities.add(OrganizationEntity.builder().build());
    doReturn(organizationEntities).when(organizationRepository)
        .findByTypeAndStatus(OrganizationTypeEnum.DVKD.getCode(),
            OrganizationStatusEnum.ACTIVE.getCode());

    List<Organization> results = organizationService.getByType(OrganizationTypeEnum.DVKD.getCode());
    assertEquals(results.size(), 3);
  }

  @Test
  void givenInValidInput_ThenGetByType_shouldReturnFail() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      organizationService.getByType("Test");
    });

    assertEquals(exception.getCode().intValue(),
        ErrorEnum.ORGANIZATION_MUST_MATCH_PATTERN.getCode());
  }

  @Test
  void givenValidInput_ThenGetByArea_shouldReturnSuccess() {
    String area = "AREA";
    List<OrganizationEntity> list1 = new ArrayList<>();
    List<OrganizationEntity> list2 = new ArrayList<>();
    list1.add(OrganizationEntity.builder().type("DVCN").code("002").build());
    list1.add(OrganizationEntity.builder().type("DVKD").build());
    doReturn(list1).when(organizationRepository)
        .findByAreaCodeAndStatus(area, OrganizationStatusEnum.ACTIVE.getCode());
    list2.add(OrganizationEntity.builder().type("DVKD").build());
    list2.add(OrganizationEntity.builder().type("DVKD").build());
    list2.add(OrganizationEntity.builder().type("").build());
    doReturn(list2).when(organizationRepository)
        .findByAreaCodeAndStatus("002", OrganizationStatusEnum.ACTIVE.getCode());
    doReturn(new ArrayList<>()).when(organizationRepository)
        .findByAreaCodeAndStatus(null, OrganizationStatusEnum.ACTIVE.getCode());
    assertEquals(3, organizationService.getDVKD(area, new ArrayList<>()).size());
  }

}