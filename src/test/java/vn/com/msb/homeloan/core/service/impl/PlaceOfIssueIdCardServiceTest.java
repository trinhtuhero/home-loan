package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.service.PlaceOfIssueIdCardService;

@ExtendWith(MockitoExtension.class)
class PlaceOfIssueIdCardServiceTest {

  PlaceOfIssueIdCardService placeOfIssueIdCardService;

  @Mock
  PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  @BeforeEach
  void setUp() {
    this.placeOfIssueIdCardService = new PlaceOfIssueIdCardServiceImpl(
        placeOfIssueIdCardRepository);
  }

  @Test
  void givenValidInput_ThenAll_shouldReturnSuccess() {
    List<PlaceOfIssueIdCardEntity> placeOfIssueIdCardEntities = new ArrayList<>();
    placeOfIssueIdCardEntities.add(PlaceOfIssueIdCardEntity.builder().build());
    placeOfIssueIdCardEntities.add(PlaceOfIssueIdCardEntity.builder().build());
    placeOfIssueIdCardEntities.add(PlaceOfIssueIdCardEntity.builder().build());
    doReturn(placeOfIssueIdCardEntities).when(placeOfIssueIdCardRepository).findAll();

    List<PlaceOfIssueIdCardEntity> results = placeOfIssueIdCardService.getAll();
    assertEquals(results.size(), 3);
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnSuccess() {
    PlaceOfIssueIdCardEntity placeOfIssueIdCardEntity = PlaceOfIssueIdCardEntity.builder()
        .code("01")
        .name("name")
        .build();
    doReturn(Optional.of(placeOfIssueIdCardEntity)).when(placeOfIssueIdCardRepository)
        .findByCode("01");

    String result = placeOfIssueIdCardService.getNameByCode("01");
    assertEquals(placeOfIssueIdCardEntity.getName(), result);
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnNull() {
    doReturn(Optional.empty()).when(placeOfIssueIdCardRepository).findByCode("01");
    String result = placeOfIssueIdCardService.getNameByCode("01");
    assertEquals(result, null);
  }
}