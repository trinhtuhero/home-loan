package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.model.CmsUserInfo;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;
import vn.com.msb.homeloan.core.model.Paging;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CommonCMSService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class CmsUserServiceCmsUserSearchTest {

  CmsUserService cmsUserService;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  CommonCMSService commonCMSService;

  @Mock
  EnvironmentProperties environmentProperties;

  @BeforeEach
  void setUp() {
    this.cmsUserService = new CmsUserServiceImpl(
        cmsUserRepository,
        null,
        null,
        commonCMSService,
        environmentProperties
    );
  }

  @Test
  @DisplayName("CmsUserService Test cmsUserSearch Success")
  void givenValidInput_ThenCmsUserSearch_shouldReturnSuccess() {
    String email = "trinhvantu93@gmail.com";
    List<CmsUserInfo> contents = new ArrayList<>();
    CmsUserInfo cmsUserInfo = CmsUserInfo.builder()
        .fullName("tu")
        .email(email)
        .phone("3564536")
        .branchCode("11000")
        .branchName("MSB Sở Giao Dịch")
        .leader("43534")
        .updatedAt("2022-08-10 06:26:45")
        .updatedBy("476576")
        .build();
    contents.add(cmsUserInfo);
    CmsUserSearch cmsUserSearch = CmsUserSearch.builder()
        .contents(contents).build();

    CmsUserSearchParam cmsUserSearchParam = new CmsUserSearchParam();
    Paging paging = Paging.builder()
        .page(0)
        .size(10)
        .build();
    cmsUserSearchParam.setPaging(paging);
    cmsUserSearchParam.setKeyWord("tu");

    doReturn(cmsUserSearch).when(cmsUserRepository).cmsUserSearch(cmsUserSearchParam);
    CmsUserSearch cmsUserSearchResult = cmsUserService.cmsUserSearch(cmsUserSearchParam);

    assertTrue(email.equalsIgnoreCase(cmsUserSearchResult.getContents().get(0).getEmail()),
        String.valueOf(true));
  }
}