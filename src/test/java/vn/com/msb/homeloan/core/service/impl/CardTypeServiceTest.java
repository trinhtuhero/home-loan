package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.CardTypeEntity;
import vn.com.msb.homeloan.core.model.CardType;
import vn.com.msb.homeloan.core.repository.CardTypeRepository;
import vn.com.msb.homeloan.core.service.CardTypeService;

@ExtendWith(MockitoExtension.class)
class CardTypeServiceTest {

  CardTypeService cardTypeService;

  @Mock
  CardTypeRepository cardTypeRepository;

  @BeforeEach
  void setUp() {
    this.cardTypeService = new CardTypeServiceImpl(cardTypeRepository);
  }

  @Test
  void givenValidInput_ThenGetAll_shouldReturnSuccess() {
    List<CardTypeEntity> cardTypeEntities = new ArrayList<>();
    CardTypeEntity cardPolicyEntity = CardTypeEntity.builder()
        .build();
    cardTypeEntities.add(cardPolicyEntity);
    doReturn(cardTypeEntities).when(cardTypeRepository).findAll();

    List<CardType> results = cardTypeService.getCardTypes();

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnSuccess() {
    List<CardTypeEntity> cardTypeEntities = new ArrayList<>();
    CardTypeEntity cardPolicyEntity = CardTypeEntity.builder()
        .code("code")
        .name("name")
        .build();
    cardTypeEntities.add(cardPolicyEntity);
    doReturn(cardTypeEntities).when(cardTypeRepository).findByCodes(any());

    Map<String, String> results = cardTypeService.getCardPolicyMapCodes(any());

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnEmpty() {
    List<CardTypeEntity> cardTypeEntities = new ArrayList<>();
    doReturn(cardTypeEntities).when(cardTypeRepository).findByCodes(any());

    Map<String, String> results = cardTypeService.getCardPolicyMapCodes(any());

    assertTrue(results.size() == 0);
  }
}