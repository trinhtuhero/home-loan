package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import vn.com.msb.homeloan.core.client.MvalueClient;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.model.Mvalue;
import vn.com.msb.homeloan.core.model.request.mvalue.GetCollateralByCodeRequest;
import vn.com.msb.homeloan.core.model.response.mvalue.GetCollateralByCodeData;
import vn.com.msb.homeloan.core.model.response.mvalue.GetCollateralByCodeResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.MvalueService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@ExtendWith(MockitoExtension.class)
class MvalueServiceTest {

  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String MVALUE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CLIENT_CODE = "CLIENT_CODE";
  private final String ASSET_CODE = "ASSET_CODE";

  MvalueService mvalueService;

  @Mock
  MvalueClient mvalueClient;

  @Mock
  EnvironmentProperties envProperties;

  @Mock
  MvalueRepository mvalueRepository;

  @Mock
  MvalueAssetInfoRepository mvalueAssetInfoRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  CollateralRepository collateralRepository;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  CollateralOwnerMapRepository collateralOwnerMapRepository;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  RequestPricingRepository requestPricingRepository;

  @Mock
  OrganizationRepository organizationRepository;

  @Mock
  ProvinceRepository provinceRepository;

  @Mock
  DistrictRepository districtRepository;

  @Mock
  WardRepository wardRepository;

  @Mock
  MvalueUploadFilesRepository mvalueUploadFilesRepository;

  @BeforeEach
  void setUp() {
    this.envProperties = new EnvironmentProperties();
    this.envProperties.setClientCode(CLIENT_CODE);
    this.mvalueService =
        new MvalueServiceImpl(
            mvalueClient,
            envProperties,
            mvalueRepository,
            mvalueAssetInfoRepository,
            loanApplicationRepository,
            collateralRepository,
            collateralOwnerRepository,
            collateralOwnerMapRepository,
            marriedPersonRepository,
            loanPayerRepository,
            cmsUserRepository,
            requestPricingRepository,
            organizationRepository,
            provinceRepository,
            districtRepository,
            wardRepository,
          mvalueUploadFilesRepository);
  }

  @Test
  void givenValidInput_ThenGetByCode_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    GetCollateralByCodeRequest getCollateralByCode = GetCollateralByCodeRequest.builder()
        .assetCode(ASSET_CODE).build();
    GetCollateralByCodeData.Response.AssetInfo assetInfo = new GetCollateralByCodeData.Response.AssetInfo();
    assetInfo.setAssetName("asset_name");
    assetInfo.setLegalPaper(new GetCollateralByCodeData.Response.AssetInfo.LegalPaper());
    assetInfo.setGeoDistrict(new GetCollateralByCodeData.Response.IdName());
    assetInfo.setGeoProvince(new GetCollateralByCodeData.Response.IdName());
    assetInfo.setGeoWard(new GetCollateralByCodeData.Response.IdName());
    assetInfo.setGeoStreet(new GetCollateralByCodeData.Response.IdName());
    GetCollateralByCodeData getCollateralByCodeData = new GetCollateralByCodeData();
    GetCollateralByCodeData.Response response = new GetCollateralByCodeData.Response();
    response.setAssetInfo(Arrays.asList(assetInfo));
    getCollateralByCodeData.setResponse(response);
    GetCollateralByCodeResponse getCollateralByCodeResponse = new GetCollateralByCodeResponse();
    getCollateralByCodeResponse.setData(getCollateralByCodeData);
    doReturn(ResponseEntity.ok(getCollateralByCodeResponse)).when(mvalueClient)
        .getCollateralByCode(getCollateralByCode, CLIENT_CODE);

    Mvalue result = mvalueService.getByCode(getCollateralByCode, LOAN_APPLICATION_ID);

    assertEquals(result.getLoanApplicationId(), LOAN_APPLICATION_ID);
  }
}
