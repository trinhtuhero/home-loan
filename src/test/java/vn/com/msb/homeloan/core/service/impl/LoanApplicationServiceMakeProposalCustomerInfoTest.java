package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vn.com.msb.homeloan.api.dto.request.RMUpdateCustomerInfoRequest;
import vn.com.msb.homeloan.api.dto.response.CMSCustomerInfoResponse;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.entity.CicEntity;
import vn.com.msb.homeloan.core.entity.CicItemEntity;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.entity.OpRiskEntity;
import vn.com.msb.homeloan.core.model.CMSCustomerAndRelatedPersonInfo;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;

class LoanApplicationServiceMakeProposalCustomerInfoTest extends LoanApplicationServiceBaseTest {

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String MARRIED_PERSON = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CIC_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CIC_ITEM_ID1 = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CIC_ITEM_ID2 = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OPRISK_ID1 = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String OPRISK_ID2 = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CONTACT_PERSON = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String ID_NO = "1234456";
  private final String OLD_ID_NO = "654321";

  @Test
  @DisplayName("LoanApplicationService Test makeProposalCustomerInfo function success")
  void givenValidInput_ThenMakeProposalCustomerInfo_shouldReturnSuccess()
      throws JsonProcessingException {
    String updateCustomerInfo = "{\n" +
        "    \"uuid\": \"03f9e024-7ec4-4ed3-8f1f-330d232b6399\",\n" +
        "    \"fullName\": \"Cao Anh Tuấn\",\n" +
        "    \"birthday\": \"19950813\",\n" +
        "    \"gender\": \"MALE\",\n" +
        "    \"idNo\": \"12345335645\",\n" +
        "    \"issuedOn\": \"20110813\",\n" +
        "    \"placeOfIssue\": \"Quảng Ninh\",\n" +
        "    \"oldIdNo\": \"78996789\",\n" +
        "    \"email\": \"caoanhtuan@gmail.com\",\n" +
        "    \"maritalStatus\": \"SINGLE\",\n" +
        "    \"numberOfDependents\": 4,\n" +
        "    \"province\": \"4321423\",\n" +
        "    \"provinceName\": \"Quảng Ninh\",\n" +
        "    \"district\": \"5645\",\n" +
        "    \"districtName\": \"Quảng Yên\",\n" +
        "    \"ward\": \"54354\",\n" +
        "    \"wardName\": \"Đông Mai\",\n" +
        "    \"address\": \"123 làng Nha\",\n" +
        "    \"refCode\": \"12345\",\n" +
        "    \"residenceProvince\": \"534543\",\n" +
        "    \"residenceProvinceName\": \"Quảng Ninh\",\n" +
        "    \"residenceDistrict\": \"34535\",\n" +
        "    \"residenceDistrictName\": \"Quảng Yên\",\n" +
        "    \"residenceWard\": \"dfgf\",\n" +
        "    \"residenceWardName\": \"Đông Mai\",\n" +
        "    \"residenceAddress\": \"0896 xóm đồi\",\n" +
        "    \"cifNo\": \"65465645645\",\n" +
        "    \"education\": \"UNIVERSITY\",\n" +
        "    \"partnerCode\": \"4656456\",\n" +
        "    \"customerSegment\": \"YP\"\n" +
        "}";
    ObjectMapper mapper = new ObjectMapper();
    RMUpdateCustomerInfoRequest rmUpdateCustomerInfoRequest = mapper.readValue(updateCustomerInfo,
        RMUpdateCustomerInfoRequest.class);
    rmUpdateCustomerInfoRequest.setUuid(LOAN_ID);

    LoanApplicationEntity entity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .phone("0906121212")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .email("caoanhtuan@gmail.com").build();
    doReturn(Optional.of(entity)).when(loanApplicationRepository).findByUuid(LOAN_ID);

    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON)
        .loanId(LOAN_ID)
        .idNo(ID_NO)
        .oldIdNo(OLD_ID_NO)
        .phone("0906121212")
        .email("caoanhtuan@gmail.com").build();
    doReturn(marriedPersonEntity).when(marriedPersonsRepository).findOneByLoanId(LOAN_ID);

    CicEntity cicEntity = CicEntity.builder()
        .uuid(CIC_ID)
        .loanApplicationId(LOAN_ID).build();
    CicItemEntity cicItemEntity1 = CicItemEntity.builder()
        .uuid(CIC_ITEM_ID1)
        .cicId(CIC_ID)
        .identityCard(ID_NO).build();
    CicItemEntity cicItemEntity2 = CicItemEntity.builder()
        .uuid(CIC_ITEM_ID2)
        .cicId(CIC_ID)
        .identityCard(OLD_ID_NO).build();
    doReturn(cicEntity).when(cicRepository).findByLoanApplicationIdAndIdentityCards(LOAN_ID,
        new HashSet<>(Arrays.asList(ID_NO, OLD_ID_NO)));
    List<CicItemEntity> cicItemEntities = Arrays.asList(cicItemEntity1, cicItemEntity2);
    doReturn(cicItemEntities).when(cicItemRepository)
        .findByLoanApplicationAndIdentityCardIn(LOAN_ID,
            new HashSet<>(Arrays.asList(ID_NO, OLD_ID_NO)));

    OpRiskEntity opRiskEntity1 = OpRiskEntity.builder()
        .uuid(OPRISK_ID1)
        .loanApplicationId(LOAN_ID)
        .identityCard(ID_NO).build();
    OpRiskEntity opRiskEntity2 = OpRiskEntity.builder()
        .uuid(OPRISK_ID2)
        .loanApplicationId(LOAN_ID)
        .identityCard(OLD_ID_NO).build();
    List<OpRiskEntity> opRiskEntities = Arrays.asList(opRiskEntity1, opRiskEntity2);
    doReturn(opRiskEntities).when(opRiskRepository)
        .findByLoanApplicationIdAndIdentityCardIn(LOAN_ID,
            new HashSet<>(Arrays.asList(ID_NO, OLD_ID_NO)));

    CMSCustomerInfoResponse customerInfoResponse = loanApplicationService.makeProposalCustomerInfo(
        LoanApplicationMapper.INSTANCE.toLoanApplication(rmUpdateCustomerInfoRequest));

    assertTrue("Cao Anh Tuấn".equalsIgnoreCase(customerInfoResponse.getFullName()),
        String.valueOf(true));
  }

  @Test
  @DisplayName("LoanApplicationService Test getCustomerInfo function success")
  void givenValidInput_ThenGetCustomerInfo_shouldReturnSuccess()
      throws JsonProcessingException, ParseException {
    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid(LOAN_ID)
        .phone("0906121212")
        .email("caoanhtuan@gmail.com")
        .maritalStatus(MaritalStatusEnum.MARRIED)
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996")).build();
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository)
        .findByUuid(LOAN_ID);

    MarriedPersonEntity marriedPersonEntity = MarriedPersonEntity.builder()
        .uuid(MARRIED_PERSON)
        .loanId(LOAN_ID)
        .phone("0906121212")
        .email("caoanhtuan@gmail.com")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996")).build();
    doReturn(marriedPersonEntity).when(marriedPersonsRepository).findOneByLoanId(LOAN_ID);

    ContactPersonEntity contactPersonEntity = ContactPersonEntity.builder()
        .uuid(CONTACT_PERSON)
        .loanId(LOAN_ID)
        .phone("0906121212")
        .fullName("haha")
        .type(ContactPersonTypeEnum.HUSBAND).build();
    doReturn(contactPersonEntity).when(contactPersonsRepository).findOneByLoanId(LOAN_ID);

    List<LoanPayerEntity> loanPayerEntities = new ArrayList<>();
    LoanPayerEntity loanPayerEntity = LoanPayerEntity.builder()
        .uuid(LOAN_ID)
        .phone("0906121212")
        .email("caoanhtuan@gmail.com")
        .birthday(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/1996")).build();
    loanPayerEntities.add(loanPayerEntity);
    doReturn(loanPayerEntities).when(loanPayerRepository).findByLoanIdOrderByCreatedAtAsc(LOAN_ID);

    CMSCustomerAndRelatedPersonInfo customerAndRelatedPersonInfoResponse = loanApplicationService.getCustomerAndRelatedPersonInfo(
        LOAN_ID);

    assertTrue("caoanhtuan@gmail.com".equalsIgnoreCase(
        customerAndRelatedPersonInfoResponse.getCustomerInfo().getEmail()), String.valueOf(true));
  }
}