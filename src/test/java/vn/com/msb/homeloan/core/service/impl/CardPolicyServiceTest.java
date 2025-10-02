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
import vn.com.msb.homeloan.core.entity.CardPolicyEntity;
import vn.com.msb.homeloan.core.model.CardPolicy;
import vn.com.msb.homeloan.core.repository.CardPolicyRepository;
import vn.com.msb.homeloan.core.service.CardPolicyService;

@ExtendWith(MockitoExtension.class)
class CardPolicyServiceTest {

  CardPolicyService cardPolicyService;

  @Mock
  CardPolicyRepository cardPolicyRepository;

  @BeforeEach
  void setUp() {
    this.cardPolicyService = new CardPolicyServiceImpl(cardPolicyRepository);
  }

  @Test
  void givenValidInput_ThenGetAll_shouldReturnSuccess() {
    List<CardPolicyEntity> cardPolicyEntities = new ArrayList<>();
    CardPolicyEntity cardPolicyEntity = CardPolicyEntity.builder()
        .build();
    cardPolicyEntities.add(cardPolicyEntity);
    doReturn(cardPolicyEntities).when(cardPolicyRepository).findAll();

    List<CardPolicy> results = cardPolicyService.getCardPolicies();

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnSuccess() {
    List<CardPolicyEntity> cardPolicyEntities = new ArrayList<>();
    CardPolicyEntity cardPolicyEntity = CardPolicyEntity.builder()
        .code("code")
        .name("name")
        .build();
    cardPolicyEntities.add(cardPolicyEntity);
    doReturn(cardPolicyEntities).when(cardPolicyRepository).findByCodes(any());

    Map<String, String> results = cardPolicyService.getCardPolicyMapCodes(any());

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnEmpty() {
    List<CardPolicyEntity> cardPolicyEntities = new ArrayList<>();
    doReturn(cardPolicyEntities).when(cardPolicyRepository).findByCodes(any());

    Map<String, String> results = cardPolicyService.getCardPolicyMapCodes(any());

    assertTrue(results.size() == 0);
  }
}