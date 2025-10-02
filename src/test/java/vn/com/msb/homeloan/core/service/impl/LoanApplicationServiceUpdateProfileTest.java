package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.api.dto.mapper.UpdateProfileRequestMapper;
import vn.com.msb.homeloan.api.dto.request.UpdateProfileRequest;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.Profile;
import vn.com.msb.homeloan.core.model.mapper.ProfileMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.ProfileService;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceUpdateProfileTest {

  ProfileService profileService;

  @Mock
  ProfileRepository profileRepository;

  @Mock
  PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @BeforeEach
  void setUp() {
    this.profileService = new ProfileServiceImpl(
        profileRepository,
        placeOfIssueIdCardRepository,
        loanApplicationRepository
    );
  }

  @Test
  @DisplayName("LoanApplicationService Test updateProfile Success")
  void givenValidInput_ThenUpdateProfile_shouldReturnSuccess() throws JsonProcessingException {
    String uuid = "1";
    String request = "{\n" +
        "    \"uuid\": \"1\",\n" +
        "    \"fullName\": \"Cao Anh Tuấn\",\n" +
        "    \"gender\": \"MALE\",\n" +
        "    \"nationality\": \"VIETNAM\",\n" +
        "    \"idNo\": \"101103085\",\n" +
        "    \"issuedOn\": \"20140313\",\n" +
        "    \"placeOfIssue\": \"cbvcb\",\n" +
        "    \"oldIdNo\": null,\n" +
        "    \"phone\": \"0906130895\",\n" +
        "    \"email\": \"caoanhtuan.uet@gmail.com\",\n" +
        "    \"maritalStatus\": \"SINGLE\",\n" +
        "    \"numberOfDependents\": \"4\",\n" +
        "    \"province\": \"cbvb\",\n" +
        "    \"provinceName\": \"bcv\",\n" +
        "    \"district\": \"bcv\",\n" +
        "    \"districtName\": \"bcv\",\n" +
        "    \"ward\": \"cbc\",\n" +
        "    \"wardName\": \"cvbc\",\n" +
        "    \"address\": \"cbvb\",\n" +
        "    \"birthday\": \"19950813\"\n" +
        "}";
    ObjectMapper mapper = new ObjectMapper();
    UpdateProfileRequest updateProfileRequest = mapper.readValue(request,
        UpdateProfileRequest.class);
    Profile profile = UpdateProfileRequestMapper.INSTANCE.toProfileModel(updateProfileRequest);
    ProfileEntity profileSaveDB = ProfileMapper.INSTANCE.toEntity(profile);

    ProfileEntity dataInDB = ProfileEntity.builder()
        .uuid(uuid)
        .email("tuanidol95@gmail.com")
        .build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
        .uuid("123456")
        .profileId(profile.getUuid())
        .status(LoanInfoStatusEnum.DRAFT).build();
    List<LoanApplicationEntity> loanApplicationEntities = new ArrayList<>();
    loanApplicationEntities.add(loanApplicationEntity);

    doReturn(Optional.of(dataInDB)).when(profileRepository).findByUuid(uuid);
    doReturn(profileSaveDB).when(profileRepository).save(profileSaveDB);
    doReturn(loanApplicationEntities).when(loanApplicationRepository)
        .findByProfileId(profile.getUuid());
    profileService.updateProfile(
        UpdateProfileRequestMapper.INSTANCE.toProfileModel(updateProfileRequest));

    assertTrue("caoanhtuan.uet@gmail.com".equalsIgnoreCase(profileSaveDB.getEmail()),
        String.valueOf(true));
  }
}