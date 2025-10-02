package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.CardTypeEntity;
import vn.com.msb.homeloan.core.model.CardType;
import vn.com.msb.homeloan.core.model.mapper.CardTypeMapper;
import vn.com.msb.homeloan.core.repository.CardTypeRepository;
import vn.com.msb.homeloan.core.service.CardTypeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CardTypeServiceImpl implements CardTypeService {

  private final CardTypeRepository cardTypeRepository;

  @Override
  public List<CardType> getCardTypes() {
    List<CardTypeEntity> cardTypeEntities = cardTypeRepository.findAll();
    return CardTypeMapper.INSTANCE.toModels(cardTypeEntities);
  }

  @Override
  public Map<String, String> getCardPolicyMapCodes(List<String> codes) {
    List<CardTypeEntity> cardTypeEntities = cardTypeRepository.findByCodes(codes);
    if (CollectionUtils.isEmpty(cardTypeEntities)) {
      return new HashMap<>();
    }

    return cardTypeEntities.stream()
        .collect(Collectors.toMap(CardTypeEntity::getCode, CardTypeEntity::getName, (a, b) -> a));
  }
}
