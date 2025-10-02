package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.CardPolicyEntity;
import vn.com.msb.homeloan.core.model.CardPolicy;
import vn.com.msb.homeloan.core.model.mapper.CardPolicyMapper;
import vn.com.msb.homeloan.core.repository.CardPolicyRepository;
import vn.com.msb.homeloan.core.service.CardPolicyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CardPolicyServiceImpl implements CardPolicyService {

  private final CardPolicyRepository cardPolicyRepository;

  @Override
  public List<CardPolicy> getCardPolicies() {
    List<CardPolicyEntity> cardPolicyEntities = cardPolicyRepository.findAll();
    return CardPolicyMapper.INSTANCE.toModels(cardPolicyEntities);
  }

  @Override
  public Map<String, String> getCardPolicyMapCodes(List<String> codes) {
    List<CardPolicyEntity> cardPolicyEntities = cardPolicyRepository.findByCodes(codes);
    if (CollectionUtils.isEmpty(cardPolicyEntities)) {
      return new HashMap<>();
    }

    return cardPolicyEntities.stream()
        .collect(
            Collectors.toMap(CardPolicyEntity::getCode, CardPolicyEntity::getName, (a, b) -> a));
  }
}
