package vn.com.msb.homeloan.core.service.impl;

import com.google.gson.Gson;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import vn.com.msb.homeloan.api.dto.request.CMSCicRequest;
import vn.com.msb.homeloan.core.client.CicClient;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OrganizationTypeEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;
import vn.com.msb.homeloan.core.constant.cic.CustomerType;
import vn.com.msb.homeloan.core.constant.cic.ProductCode;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CMSGetCicInfo;
import vn.com.msb.homeloan.core.model.request.cic.CicCodeRequest;
import vn.com.msb.homeloan.core.model.request.cic.CicQueryRequest;
import vn.com.msb.homeloan.core.model.response.cic.CicCodeResponse;
import vn.com.msb.homeloan.core.model.response.cic.CicQueryResponse;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CicService;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.core.service.PDFGenerationService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CicServiceTest {

  private final String CIC_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CIC_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String LOAN_APPLICATION_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String IDENTITY_CARD = "010002028";
  private final String IDENTITY_CARD_OLD = "010002029";
  private final String IDENTITY_CARD_OLD_2 = "010002032";
  private final String IDENTITY_CARD_OLD_3 = "010002033";
  private final String CLIENT_CODE = "client-code";
  private final String CUSTOMER_NAME = "Nguyễn Thị 2028";

  CicService cicService;

  @Mock
  CicClient cicClient;

  @Mock
  CicRepository cicRepository;

  @Mock
  CicItemRepository cicItemRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanPayerRepository loanPayerRepository;

  @Mock
  MarriedPersonRepository marriedPersonRepository;

  @Mock
  EnvironmentProperties envProperties;

  @Mock
  CMSTabActionService cmsTabActionService;

  @Mock
  BusinessIncomeRepository businessIncomeRepository;

  @Mock
  CollateralOwnerRepository collateralOwnerRepository;

  @Mock
  CmsUserRepository cmsUserRepository;

  @Mock
  OrganizationRepository organizationRepository;

  @Mock
  PDFGenerationService pdfGenerationService;

  @Mock
  EnvironmentProperties environmentProperties;

  @Mock
  FileService fileService;

  @Mock
  FileConfigRepository fileConfigRepository;

  @Mock
  LoanUploadFileRepository loanUploadFileRepository;

  @BeforeEach
  void setUp() {
    this.envProperties = new EnvironmentProperties();
    this.envProperties.setClientCode(CLIENT_CODE);
    this.cicService = new CicServiceImpl(
        cicClient,
        cicRepository,
        cicItemRepository,
        loanApplicationRepository,
        loanPayerRepository,
        marriedPersonRepository,
        envProperties,
        cmsTabActionService,
        businessIncomeRepository,
        collateralOwnerRepository,
        cmsUserRepository,
        organizationRepository,
      pdfGenerationService,
      environmentProperties,
      fileService,
      fileConfigRepository,
      loanUploadFileRepository
    );
  }

  @Test
  void givenValidInput_ThenCheckTypeDebt_shouldReturnSuccess() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    List<CMSCicRequest.Info> infos = new ArrayList<>();
    CMSCicRequest.Info info = new CMSCicRequest.Info();
    info.setCustomerName(CUSTOMER_NAME);
    info.setRelationshipType(CICRelationshipTypeEnum.CUSTOMER);
    List<String> identityCards = Arrays.asList(IDENTITY_CARD);
    info.setIdentityCards(identityCards);
    infos.add(info);

    String cicCodeStr = "{\n" +
        "    \"data\": [\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        },\n" +
        "        {\n" +
        "            \"cicCode\": \"9939006789\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
        "            \"address\": \"Địa chỉ của khách hàng 2028\",\n" +
        "            \"businessRegNum\": \"\",\n" +
        "            \"taxCode\": \"\",\n" +
        "            \"customerName\": \"Nguyễn Thị 2028\"\n" +
        "        }\n" +
        "    ],\n" +
        "    \"status\": 200\n" +
        "}";
    Gson g = new Gson();
    CicCodeResponse cicCodeResponse = g.fromJson(cicCodeStr, CicCodeResponse.class);
    CicCodeRequest cicCodeRequest = new CicCodeRequest();
    cicCodeRequest.setCustomerUniqueId(IDENTITY_CARD);
    cicCodeRequest.setCustomerType(CustomerType.INDIVIDUAL);
    doReturn(ResponseEntity.ok(cicCodeResponse)).when(cicClient)
        .searchCode(cicCodeRequest, CLIENT_CODE);

    String cicQueryStr = "{\n" +
        "    \"data\": {\n" +
        "        \"code\": 0,\n" +
        "        \"transactionTime\": 1663034867218,\n" +
        "        \"value\": {\n" +
//                "            \"requestTime\": \"20220913\",\n" +
        "            \"clientQuestionId\": \"827620\",\n" +
        "            \"uniqueId\": \"010002028\",\n" +
//                "            \"content\": \"<CicTemplate><Header><MaYeuCauHoiTin>TM.302F5.109</MaYeuCauHoiTin><UserID>h01302001cuong3</UserID><MaTCTD>01302001</MaTCTD><SanPham>TM.302F5.109</SanPham><ThoiGian>11/08/2022</ThoiGian></Header><Body><TTKH><MaCIC>9939006789</MaCIC><MaKH>CICINFO</MaKH><TenKhachHang>Nguyễn Thị 2028</TenKhachHang><CMND-HC>010002028</CMND-HC><GiayToKhac></GiayToKhac><DKKD></DKKD><MST></MST></TTKH><TCTDHoiTin><MaCN>302</MaCN><TruSoChinh-DiaChi>Địa chỉ của khách hàng 2028</TruSoChinh-DiaChi><TGD-DaiDienPL></TGD-DaiDienPL></TCTDHoiTin><TTChung><NgaySL>11/08/2022</NgaySL></TTChung><DSTCTD><TCTD><TTChung><NgaySL>11/08/2022</NgaySL><TenTCTD>Công ty Tài chính Cổ phần Điện lực</TenTCTD><MaTCTD>01826001</MaTCTD></TTChung><ChiTiet><DuNo><DuNoNganHan><NoDuTieuChuanVND>10</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>111110</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoNganHan><DuNoTrungHan><NoDuTieuChuanVND>130</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>222220</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoTrungHan><DuNoDaiHan><NoDuTieuChuanVND>0</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoDaiHan><DuNoKhac><NoDuTieuChuanVND>0</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoKhac><DuNoBoSung><NoXauKhacVND>999999</NoXauKhacVND><NoXauKhacUSD>888888</NoXauKhacUSD><NoTaiNHPTVND></NoTaiNHPTVND><NoTaiNHPTUSD></NoTaiNHPTUSD></DuNoBoSung></DuNo><TSBD><CoTSBD>0</CoTSBD></TSBD></ChiTiet></TCTD><TCTD><TTChung><NgaySL>11/08/2022</NgaySL><TenTCTD>Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam - Chi nhánh huyện Hải Hậu</TenTCTD><MaTCTD>36204012</MaTCTD></TTChung><ChiTiet><DuNo><DuNoNganHan><NoDuTieuChuanVND>150</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoNganHan><DuNoTrungHan><NoDuTieuChuanVND>0</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoTrungHan><DuNoDaiHan><NoDuTieuChuanVND>0</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoDaiHan><DuNoKhac><NoDuTieuChuanVND>0</NoDuTieuChuanVND><NoCanChuYVND>0</NoCanChuYVND><NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND><NoNghiNgoVND>0</NoNghiNgoVND><NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND><NoDuTieuChuanUSD>0</NoDuTieuChuanUSD><NoCanChuYUSD>0</NoCanChuYUSD><NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD><NoNghiNgoUSD>0</NoNghiNgoUSD><NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD></DuNoKhac><DuNoBoSung><NoXauKhacVND>0</NoXauKhacVND><NoXauKhacUSD>0</NoXauKhacUSD><NoTaiNHPTVND></NoTaiNHPTVND><NoTaiNHPTUSD></NoTaiNHPTUSD></DuNoBoSung></DuNo><TSBD><CoTSBD>1</CoTSBD></TSBD></ChiTiet></TCTD></DSTCTD><DoNo12Thang><DuNoChiTiet><TCTD><NgaySL>11/08/2022</NgaySL><TenTCTD>Công ty Tài chính Cổ phần Điện lực</TenTCTD><T1VND></T1VND><T2VND></T2VND><T3VND></T3VND><T4VND>10</T4VND><T5VND>10</T5VND><T6VND>30</T6VND><T7VND>27</T7VND><T8VND>24</T8VND><T9VND>22</T9VND><T10VND>18</T10VND><T11VND>25</T11VND><T12VND></T12VND></TCTD><TCTD><NgaySL>11/08/2022</NgaySL><TenTCTD>Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam - Chi nhánh huyện Hải Hậu</TenTCTD><T1VND>150</T1VND><T2VND>150</T2VND><T3VND>150</T3VND><T4VND>150</T4VND><T5VND>150</T5VND><T6VND>150</T6VND><T7VND>150</T7VND><T8VND>150</T8VND><T9VND>150</T9VND><T10VND>150</T10VND><T11VND>150</T11VND><T12VND></T12VND></TCTD></DuNoChiTiet></DoNo12Thang><NoXau36Thang><NoXauChiTiet><TCTD><NgaySL>11/08/2022</NgaySL><TenTCTD>Ngân hàng TMCP Việt Nam Thịnh Vượng - Chi nhánh Kinh Đô</TenTCTD><NhomNoCaoNhat>03</NhomNoCaoNhat><NhomNo>03</NhomNo><NgayPhatSinhNoXauCuoiCung>09/12/2021</NgayPhatSinhNoXauCuoiCung><SoTienVND>186</SoTienVND><SoTienUSD>0</SoTienUSD></TCTD></NoXauChiTiet></NoXau36Thang><NoCanChuYTrong12Thang><NoCanChuYChiTiet><TCTD><NgaySL>11/08/2022</NgaySL><TenTCTD>Ngân hàng TMCP Hàng Hải Việt Nam - Chi nhánh Tân Bình</TenTCTD><T1VND></T1VND><T2VND></T2VND><T3VND></T3VND><T4VND></T4VND><T5VND>6</T5VND><T6VND></T6VND><T7VND></T7VND><T8VND></T8VND><T9VND></T9VND><T10VND></T10VND><T11VND></T11VND><T12VND></T12VND></TCTD></NoCanChuYChiTiet></NoCanChuYTrong12Thang><NoVAMC><NoVAMCChiTiet></NoVAMCChiTiet></NoVAMC><HDTD><CHITIET><TenTCTD>Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam - Chi nhánh huyện Hải Hậu</TenTCTD><NGAYKY>20/11/2019</NGAYKY><NGAYKT>20/11/2022</NGAYKT></CHITIET><CHITIET><TenTCTD>Công ty Tài chính Cổ phần Điện lực</TenTCTD><NGAYKY>13/05/2021</NGAYKY><NGAYKT>13/05/2023</NGAYKT></CHITIET></HDTD><TRAIPHIEU></TRAIPHIEU><CAMKETNB></CAMKETNB><TCTDTraCuu12Thang><TCTDTraCuu><TenTCTD>Công ty Tài chính TNHH MTV Shinhan Việt Nam</TenTCTD><NgayTraCuu>19/05/2021</NgayTraCuu></TCTDTraCuu><TCTDTraCuu><TenTCTD>Công ty Tài chính TNHH MTV Shinhan Việt Nam</TenTCTD><NgayTraCuu>19/05/2021</NgayTraCuu></TCTDTraCuu><TCTDTraCuu><TenTCTD>Ngân hàng TMCP Việt Nam Thịnh Vượng</TenTCTD><NgayTraCuu>12/10/2021</NgayTraCuu></TCTDTraCuu><TCTDTraCuu><TenTCTD>Ngân hàng TMCP Việt Nam Thịnh Vượng</TenTCTD><NgayTraCuu>12/10/2021</NgayTraCuu></TCTDTraCuu><TCTDTraCuu><TenTCTD>Ngân hàng TMCP Hàng hải Việt Nam</TenTCTD><NgayTraCuu>01/12/2021</NgayTraCuu></TCTDTraCuu></TCTDTraCuu12Thang><DuNoTheTD><DuNoTheTDChiTiet><TCTD><NgaySL>30/11/2021</NgaySL><TenTCTDPhatHanh>Ngân hàng TMCP Việt Nam Thịnh Vượng</TenTCTDPhatHanh><HMTheTD>10</HMTheTD><SoTienPhaiThanhToan>0</SoTienPhaiThanhToan><SoTienChamThanhToan>0</SoTienChamThanhToan><SoNgayChamThanhToan>0</SoNgayChamThanhToan></TCTD><TCTD><NgaySL>30/11/2021</NgaySL><TenTCTDPhatHanh>Ngân hàng TMCP Việt Nam Thịnh Vượng</TenTCTDPhatHanh><HMTheTD>30</HMTheTD><SoTienPhaiThanhToan>0</SoTienPhaiThanhToan><SoTienChamThanhToan>0</SoTienChamThanhToan><SoNgayChamThanhToan>0</SoNgayChamThanhToan></TCTD><TCTD><NgaySL>11/08/2022</NgaySL><TenTCTDPhatHanh>Ngân hàng TMCP Việt Nam Thịnh Vượng</TenTCTDPhatHanh><HMTheTD>40</HMTheTD><SoTienPhaiThanhToan>0</SoTienPhaiThanhToan><SoTienChamThanhToan>0</SoTienChamThanhToan><SoNgayChamThanhToan>0</SoNgayChamThanhToan></TCTD></DuNoTheTDChiTiet></DuNoTheTD><DuNoTheTD12Thang><DoNoTheTDChiTiet></DoNoTheTDChiTiet></DuNoTheTD12Thang><LichSuChamTTTheTD36Thang></LichSuChamTTTheTD36Thang><TTKHAC><NoiDung></NoiDung></TTKHAC></Body></CicTemplate>\",\n" +
        "            \"content\": \"<Header>\n" +
        "    <MaYeuCauHoiTin>TM.302F5.109</MaYeuCauHoiTin>\n" +
        "    <UserID>h01302001cuong3</UserID>\n" +
        "    <MaTCTD>01302001</MaTCTD>\n" +
        "    <SanPham>TM.302F5.109</SanPham>\n" +
        "    <ThoiGian>20/09/2022</ThoiGian>\n" +
        "</Header>\n" +
        "<Body>\n" +
        "    <TTKH>\n" +
        "        <MaCIC>3132444811</MaCIC>\n" +
        "        <MaKH>CICINFO</MaKH>\n" +
        "        <TenKhachHang>NGUY?N KHÁNH HUY?N</TenKhachHang>\n" +
        "        <CMND-HC>112963999</CMND-HC>\n" +
        "        <GiayToKhac></GiayToKhac>\n" +
        "        <DKKD></DKKD>\n" +
        "        <MST></MST>\n" +
        "    </TTKH>\n" +
        "    <TCTDHoiTin>\n" +
        "        <MaCN>302</MaCN>\n" +
        "        <TruSoChinh-DiaChi>NAM ??NH</TruSoChinh-DiaChi>\n" +
        "        <TGD-DaiDienPL></TGD-DaiDienPL>\n" +
        "    </TCTDHoiTin>\n" +
        "    <TTChung>\n" +
        "        <NgaySL>01/12/2021</NgaySL>\n" +
        "    </TTChung>\n" +
        "    <DSTCTD>\n" +
        "        <TCTD>\n" +
        "            <TTChung>\n" +
        "                <NgaySL>31/10/2021</NgaySL>\n" +
        "                <TenTCTD>Ngân hàng TMCP Vi?t Nam Th?nh V??ng - Chi nhánh Hà N?i</TenTCTD>\n"
        +
        "                <MaTCTD>01302003A</MaTCTD>\n" +
        "            </TTChung>\n" +
        "            <ChiTiet>\n" +
        "                <DuNo>\n" +
        "                    <DuNoNganHan>\n" +
        "                        <NoDuTieuChuanVND>0</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>1</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>1</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>0</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>0</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>0</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoNganHan>\n" +
        "                    <DuNoTrungHan>\n" +
        "                        <NoDuTieuChuanVND>0</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>0</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>0</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>0</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>0</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>0</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoTrungHan>\n" +
        "                    <DuNoDaiHan>\n" +
        "                        <NoDuTieuChuanVND>0</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>0</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>0</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>11</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>0</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>0</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoDaiHan>\n" +
        "                    <DuNoKhac>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>0</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>0</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND></NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>0</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>0</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>0</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>0</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoKhac>\n" +
        "                    <DuNoBoSung>\n" +
        "                        <NoXauKhacVND>1</NoXauKhacVND>\n" +
        "                        <NoXauKhacUSD>0</NoXauKhacUSD>\n" +
        "                        <NoTaiNHPTVND>0</NoTaiNHPTVND>\n" +
        "                        <NoTaiNHPTUSD>0</NoTaiNHPTUSD>\n" +
        "                    </DuNoBoSung>\n" +
        "                </DuNo>\n" +
        "                <TSBD>\n" +
        "                    <CoTSBD>0</CoTSBD>\n" +
        "                </TSBD>\n" +
        "            </ChiTiet>\n" +
        "        </TCTD>\n" +
        "        <TCTD>\n" +
        "            <TTChung>\n" +
        "                <NgaySL>31/10/2021</NgaySL>\n" +
        "                <TenTCTD>Công ty B</TenTCTD>\n" +
        "                <MaTCTD>83302001A</MaTCTD>\n" +
        "            </TTChung>\n" +
        "            <ChiTiet>\n" +
        "                <DuNo>\n" +
        "                    <DuNoNganHan>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>3</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>5</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>6</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>1</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>1</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoNganHan>\n" +
        "                    <DuNoTrungHan>\n" +
        "                        <NoDuTieuChuanVND>55</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>15</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>12</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>13</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>5</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>9</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoTrungHan>\n" +
        "                    <DuNoDaiHan>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>1</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>1</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>1</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>1</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>1</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoDaiHan>\n" +
        "                    <DuNoKhac>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>2</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>3</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>4</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>6</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>7</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>8</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>9</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoKhac>\n" +
        "                    <DuNoBoSung>\n" +
        "                        <NoXauKhacVND>0</NoXauKhacVND>\n" +
        "                        <NoXauKhacUSD>0</NoXauKhacUSD>\n" +
        "                        <NoTaiNHPTVND>0</NoTaiNHPTVND>\n" +
        "                        <NoTaiNHPTUSD>0</NoTaiNHPTUSD>\n" +
        "                    </DuNoBoSung>\n" +
        "                </DuNo>\n" +
        "                <TSBD>\n" +
        "                    <CoTSBD>0</CoTSBD>\n" +
        "                </TSBD>\n" +
        "            </ChiTiet>\n" +
        "        </TCTD>\n" +
        "        <TCTD>\n" +
        "            <TTChung>\n" +
        "                <NgaySL>31/10/2021</NgaySL>\n" +
        "                <TenTCTD>Công ty B</TenTCTD>\n" +
        "                <MaTCTD>58302001A</MaTCTD>\n" +
        "            </TTChung>\n" +
        "            <ChiTiet>\n" +
        "                <DuNo>\n" +
        "                    <DuNoNganHan>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>3</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>5</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>6</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>1</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>1</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoNganHan>\n" +
        "                    <DuNoTrungHan>\n" +
        "                        <NoDuTieuChuanVND>55</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>15</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>12</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>13</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>5</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>9</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoTrungHan>\n" +
        "                    <DuNoDaiHan>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>1</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>1</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>1</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>1</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>1</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>1</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>1</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoDaiHan>\n" +
        "                    <DuNoKhac>\n" +
        "                        <NoDuTieuChuanVND>1</NoDuTieuChuanVND>\n" +
        "                        <NoCanChuYVND>2</NoCanChuYVND>\n" +
        "                        <NoDuoiTieuChuanVND>3</NoDuoiTieuChuanVND>\n" +
        "                        <NoNghiNgoVND>4</NoNghiNgoVND>\n" +
        "                        <NoCoKhaNangMatVonVND>0</NoCoKhaNangMatVonVND>\n" +
        "                        <NoDuTieuChuanUSD>6</NoDuTieuChuanUSD>\n" +
        "                        <NoCanChuYUSD>7</NoCanChuYUSD>\n" +
        "                        <NoDuoiTieuChuanUSD>8</NoDuoiTieuChuanUSD>\n" +
        "                        <NoNghiNgoUSD>9</NoNghiNgoUSD>\n" +
        "                        <NoCoKhaNangMatVonUSD>0</NoCoKhaNangMatVonUSD>\n" +
        "                    </DuNoKhac>\n" +
        "                    <DuNoBoSung>\n" +
        "                        <NoXauKhacVND>0</NoXauKhacVND>\n" +
        "                        <NoXauKhacUSD>0</NoXauKhacUSD>\n" +
        "                        <NoTaiNHPTVND>0</NoTaiNHPTVND>\n" +
        "                        <NoTaiNHPTUSD>0</NoTaiNHPTUSD>\n" +
        "                    </DuNoBoSung>\n" +
        "                </DuNo>\n" +
        "                <TSBD>\n" +
        "                    <CoTSBD>0</CoTSBD>\n" +
        "                </TSBD>\n" +
        "            </ChiTiet>\n" +
        "        </TCTD>\n" +
        "    </DSTCTD>\n" +
        "    <DoNo12Thang>\n" +
        "        <DuNoChiTiet>\n" +
        "            <TCTD>\n" +
        "                <NgaySL>31/10/2021</NgaySL>\n" +
        "                <TenTCTD>Công ty Tài chính C? ph?n ?i?n l?c</TenTCTD>\n" +
        "                <T1VND></T1VND>\n" +
        "                <T2VND></T2VND>\n" +
        "                <T3VND></T3VND>\n" +
        "                <T4VND>10</T4VND>\n" +
        "                <T5VND>10</T5VND>\n" +
        "                <T6VND>30</T6VND>\n" +
        "                <T7VND>27</T7VND>\n" +
        "                <T8VND>24</T8VND>\n" +
        "                <T9VND>22</T9VND>\n" +
        "                <T10VND>18</T10VND>\n" +
        "                <T11VND>25</T11VND>\n" +
        "                <T12VND></T12VND>\n" +
        "            </TCTD>\n" +
        "        </DuNoChiTiet>\n" +
        "    </DoNo12Thang>\n" +
        "    <NoXau36Thang>\n" +
        "        <NoXauChiTiet>\n" +
        "        </TCTD>\n" +
        "    </NoXauChiTiet>\n" +
        "</NoXau36Thang>\n" +
        "<NoCanChuYTrong12Thang>\n" +
        "    <NoCanChuYChiTiet></NoCanChuYChiTiet>\n" +
        "</NoCanChuYTrong12Thang>\n" +
        "<NoVAMC>\n" +
        "    <NoVAMCChiTiet></NoVAMCChiTiet>\n" +
        "</NoVAMC>\n" +
        "<HDTD></HDTD>\n" +
        "<TRAIPHIEU></TRAIPHIEU>\n" +
        "<CAMKETNB></CAMKETNB>\n" +
        "<TCTDTraCuu12Thang></TCTDTraCuu12Thang>\n" +
        "<DuNoTheTD>\n" +
        "    <DuNoTheTDChiTiet>\n" +
        "        <TCTD>\n" +
        "            <NgaySL>20/09/2022</NgaySL>\n" +
        "            <TenTCTDPhatHanh>Ngân hàng TMCP Hàng h?i Vi?t Nam</TenTCTDPhatHanh>\n" +
        "            <HMTheTD>50</HMTheTD>\n" +
        "            <SoTienPhaiThanhToan>0</SoTienPhaiThanhToan>\n" +
        "            <SoTienChamThanhToan>0</SoTienChamThanhToan>\n" +
        "            <SoNgayChamThanhToan>0</SoNgayChamThanhToan>\n" +
        "        </TCTD>\n" +
        "        <TCTD>\n" +
        "            <NgaySL>20/09/2022</NgaySL>\n" +
        "            <TenTCTDPhatHanh>Ngân hàng TMCP Hàng h?i Vi?t Nam</TenTCTDPhatHanh>\n" +
        "            <HMTheTD>30</HMTheTD>\n" +
        "            <SoTienPhaiThanhToan>1</SoTienPhaiThanhToan>\n" +
        "            <SoTienChamThanhToan>1</SoTienChamThanhToan>\n" +
        "            <SoNgayChamThanhToan>0</SoNgayChamThanhToan>\n" +
        "        </TCTD>\n" +
        "        <TCTD>\n" +
        "            <NgaySL>30/11/2021</NgaySL>\n" +
        "            <TenTCTDPhatHanh>Ngân hàng TMCP Vi?t Nam Th?nh V??ng</TenTCTDPhatHanh>\n" +
        "            <HMTheTD>40</HMTheTD>\n" +
        "            <SoTienPhaiThanhToan>0</SoTienPhaiThanhToan>\n" +
        "            <SoTienChamThanhToan>0</SoTienChamThanhToan>\n" +
        "            <SoNgayChamThanhToan>0</SoNgayChamThanhToan>\n" +
        "        </TCTD>\n" +
        "    </DuNoTheTDChiTiet>\n" +
        "</DuNoTheTD>\n" +
        "<DuNoTheTD12Thang>\n" +
        "    <DoNoTheTDChiTiet></DoNoTheTDChiTiet>\n" +
        "</DuNoTheTD12Thang>\n" +
        "<LichSuChamTTTheTD36Thang>\n" +
        "    <TCTD>\n" +
        "        <NgaySL>05/07/2021</NgaySL>\n" +
        "        <TenTCTD>Ngân hàng Citibank N.A - Chi nhánh thành ph? H? Chí Minh</TenTCTD>\n" +
        "        <DSChamTTTheTD>\n" +
        "            <ChamTTTheTD>\n" +
        "                <NgayChamTT>12/04/2021</NgayChamTT>\n" +
        "                <SoTienChamTT>2</SoTienChamTT>\n" +
        "                <SoNgayChamTT>154</SoNgayChamTT>\n" +
        "            </ChamTTTheTD>\n" +
        "            <ChamTTTheTD>\n" +
        "                <NgayChamTT>19/09/2021</NgayChamTT>\n" +
        "                <SoTienChamTT>3</SoTienChamTT>\n" +
        "                <SoNgayChamTT>181</SoNgayChamTT>\n" +
        "            </ChamTTTheTD>\n" +
        "        </DSChamTTTheTD>\n" +
        "    </TCTD>\n" +
        "    <TCTD>\n" +
        "        <NgaySL>05/07/2021</NgaySL>\n" +
        "        <TenTCTD>Ngân hàng Citibank N.A - Chi nhánh Hà n?i</TenTCTD>\n" +
        "        <DSChamTTTheTD>\n" +
        "            <ChamTTTheTD>\n" +
        "                <NgayChamTT>27/09/2021</NgayChamTT>\n" +
        "                <SoTienChamTT>0</SoTienChamTT>\n" +
        "                <SoNgayChamTT>36</SoNgayChamTT>\n" +
        "            </ChamTTTheTD>\n" +
        "            <ChamTTTheTD>\n" +
        "                <NgayChamTT>21/09/2021</NgayChamTT>\n" +
        "                <SoTienChamTT>3</SoTienChamTT>\n" +
        "                <SoNgayChamTT>180</SoNgayChamTT>\n" +
        "            </ChamTTTheTD>\n" +
        "        </DSChamTTTheTD>\n" +
        "    </TCTD>\n" +
        "</LichSuChamTTTheTD36Thang>\n" +
        "<TTKHAC>\n" +
        "    <NoiDung></NoiDung>\n" +
        "</TTKHAC>\n" +
        "</Body>\n" +
        "</CicTemplate>\",\n" +
        "            \"status\": 9\n" +
//                "            \"h2hResponseTime\": \"20220815\"\n" +
        "        }\n" +
        "    },\n" +
        "    \"status\": 200\n" +
        "}";
    CicQueryResponse cicQueryResponse = g.fromJson(cicQueryStr, CicQueryResponse.class);
    cicQueryResponse.getData().getValue().setStatus(CicQueryStatusEnum.OTHER);
    CicQueryRequest cicQueryRequest = CicQueryRequest.builder()
        .customerUniqueId(IDENTITY_CARD)
        .cicCode("9939006789")
        .address("Địa chỉ của khách hàng 2028")
        .customerName(CUSTOMER_NAME)
        .customerType(CustomerType.INDIVIDUAL)
        .validityPeriod(30)
        .productCode(ProductCode.TONG_HOP).build();
    doReturn(ResponseEntity.ok(cicQueryResponse)).when(cicClient)
        .search(cicQueryRequest, CLIENT_CODE);
    OrganizationEntity organizationEntity = new OrganizationEntity();
    organizationEntity.setType(OrganizationTypeEnum.DVKD.getCode());
    when(cmsUserRepository.findByEmail(anyString())).thenReturn(Optional.of(new CmsUserEntity()));
    when(organizationRepository.findByCode(any())).thenReturn(organizationEntity);
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        cicService.checkTypeDebt(infos, LOAN_APPLICATION_ID);
      });

      assertEquals(exception.getCode().intValue(), ErrorEnum.CALL_API_CIC_ERROR.getCode());
    }

  }

  @Test
  void givenValidInput_ThenCheckTypeDebt_shouldReturnFailCallApi() throws Exception {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findById(LOAN_APPLICATION_ID);

    List<CMSCicRequest.Info> infos = new ArrayList<>();
    CMSCicRequest.Info info = new CMSCicRequest.Info();
    info.setCustomerName(CUSTOMER_NAME);
    info.setRelationshipType(CICRelationshipTypeEnum.CUSTOMER);
    List<String> identityCards = Arrays.asList(IDENTITY_CARD);
    info.setIdentityCards(identityCards);
    infos.add(info);

    CicCodeRequest cicCodeRequest = new CicCodeRequest();
    cicCodeRequest.setCustomerUniqueId(IDENTITY_CARD);
    cicCodeRequest.setCustomerType(CustomerType.INDIVIDUAL);
    when(cicClient.searchCode(cicCodeRequest, CLIENT_CODE))
        .thenThrow(FeignException.class);

    OrganizationEntity organizationEntity = new OrganizationEntity();
    organizationEntity.setType(OrganizationTypeEnum.DVKD.getCode());
    when(cmsUserRepository.findByEmail(anyString())).thenReturn(Optional.of(new CmsUserEntity()));
    when(organizationRepository.findByCode(any())).thenReturn(organizationEntity);
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn("EMAIL");
      ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        cicService.checkTypeDebt(infos, LOAN_APPLICATION_ID);
      });

      assertEquals(exception.getCode().intValue(), ErrorEnum.CALL_API_CIC_ERROR.getCode());
    }

  }

  @Test
  void givenValidInput_ThenGetCic_shouldReturnSuccess() {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD)
        .oldIdNo2(IDENTITY_CARD_OLD_2)
        .oldIdNo3(IDENTITY_CARD_OLD_3).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_APPLICATION_ID);

    List<CicItemEntity> cicItemEntities = new ArrayList<>();
    CicItemEntity cicItemEntity = CicItemEntity.builder()
        .cicId(CIC_ID)
        .uuid(CIC_ITEM_ID)
        .identityCard(IDENTITY_CARD)
        .build();
    cicItemEntities.add(cicItemEntity);
    doReturn(cicItemEntities).when(cicItemRepository)
        .findByLoanApplicationAndIdentityCardInAndActive(LOAN_APPLICATION_ID, new HashSet<>(
            Arrays.asList(IDENTITY_CARD, IDENTITY_CARD_OLD, IDENTITY_CARD_OLD_2,
                IDENTITY_CARD_OLD_3)), true);

    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    doReturn(marriedPersonEntity).when(marriedPersonRepository)
        .findOneByLoanId(LOAN_APPLICATION_ID);

    List<LoanPayerEntity> loanPayers = new ArrayList<>();
    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    loanPayers.add(loanPayerEntity);
    doReturn(loanPayers).when(loanPayerRepository).findByLoanId(LOAN_APPLICATION_ID);

    List<BusinessIncomeEntity> businessIncomes = new ArrayList<>();
    BusinessIncomeEntity businessIncomeEntity = BusinessIncomeEntity.builder()
        .loanApplicationId(LOAN_APPLICATION_ID)
        .businessType(BusinessTypeEnum.ENTERPRISE)
        .businessCode("code").build();
    businessIncomes.add(businessIncomeEntity);
    doReturn(businessIncomes).when(businessIncomeRepository)
        .findByLoanApplicationIdAndBusinessTypeIn(LOAN_APPLICATION_ID,
            Arrays.asList(BusinessTypeEnum.ENTERPRISE, BusinessTypeEnum.WITH_REGISTRATION));

    List<CollateralOwnerEntity> collateralOwners = new ArrayList<>();
    CollateralOwnerEntity collateralOwnerEntity = CollateralOwnerEntity.builder()
        .loanId(LOAN_APPLICATION_ID)
        .idNo(IDENTITY_CARD)
        .oldIdNo(IDENTITY_CARD_OLD).build();
    collateralOwners.add(collateralOwnerEntity);
    doReturn(collateralOwners).when(collateralOwnerRepository).getByLoanId(LOAN_APPLICATION_ID);

    List<CMSGetCicInfo> result = cicService.getCic(LOAN_APPLICATION_ID);

    assertEquals(result.get(0).getCicItems().get(0).getCicId(), CIC_ID);
  }
}
