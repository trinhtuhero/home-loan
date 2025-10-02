package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.Profile;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.ProfileService;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceGetProfileByIdTest {

  ProfileService profileService;

  @Mock
  ProfileRepository profileRepository;

  @Mock
  PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  @BeforeEach
  void setUp() {
    this.profileService = new ProfileServiceImpl(
        profileRepository,
        placeOfIssueIdCardRepository,
        null
    );
  }

  @Test
  @DisplayName("LoanApplicationService Test updateProfile Success")
  void givenValidInput_ThenUpdateProfile_shouldReturnSuccess() throws JsonProcessingException {
    String uuid = "1";

    ProfileEntity dataInDB = ProfileEntity.builder()
        .uuid(uuid)
        .email("tuanidol95@gmail.com")
        .placeOfIssue("placeCode")
        .build();

    doReturn(Optional.of(dataInDB)).when(profileRepository).findByUuid(uuid);
    doReturn(Optional.of(PlaceOfIssueIdCardEntity.builder()
        .name("profileName")
        .build())).when(placeOfIssueIdCardRepository).findByCode("placeCode");

    Profile profile = profileService.getProfileById(uuid);

    assertTrue(dataInDB.getUuid().equalsIgnoreCase(profile.getUuid()), String.valueOf(true));
    assertTrue(profile.getPlaceOfIssueName().equalsIgnoreCase("profileName"), String.valueOf(true));
  }
}