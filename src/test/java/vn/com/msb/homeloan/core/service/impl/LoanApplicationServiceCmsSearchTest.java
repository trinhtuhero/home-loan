package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.model.CmsLoanApplication;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearchParam;
import vn.com.msb.homeloan.core.model.Paging;

class LoanApplicationServiceCmsSearchTest extends LoanApplicationServiceBaseTest {

  @Mock
  MockHttpServletRequest request;

  @Test
  @DisplayName("LoanApplicationService Test cmsSearch Success")
  void givenValidInput_ThenCmsSearch_shouldReturnSuccess() throws IOException {
    String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
    LoanApplicationEntity mockEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .fullName("Nguyen van A")
        .build();

    CmsLoanApplicationSearchParam searchParam = new CmsLoanApplicationSearchParam();
    searchParam.setKeyWord("0906130895");
    searchParam.setPaging(new Paging(0, 10, null));

    CmsLoanApplication cmsLoanApplication = new CmsLoanApplication();
    cmsLoanApplication.setUuid(LOAN_ID);
    List<CmsLoanApplication> lstCmsLoanApplication = new ArrayList<>();
    lstCmsLoanApplication.add(cmsLoanApplication);
    CmsLoanApplicationSearch mockCmsLoanApplicationSearch = new CmsLoanApplicationSearch();
    mockCmsLoanApplicationSearch.setContents(lstCmsLoanApplication);

    doReturn(mockCmsLoanApplicationSearch).when(loanApplicationRepository)
        .cmsLoanApplicationSearch(searchParam, Collections.EMPTY_LIST);

    CmsLoanApplicationSearch cmsLoanApplicationSearch = loanApplicationService.cmsSearch(
        searchParam, request);
    assertTrue(cmsLoanApplicationSearch.getContents().get(0).getUuid()
        .equalsIgnoreCase(mockEntity.getUuid()), String.valueOf(true));
  }
}