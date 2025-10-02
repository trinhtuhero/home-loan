package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.model.CreditInstitution;
import vn.com.msb.homeloan.core.model.mapper.CreditInstitutionMapper;
import vn.com.msb.homeloan.core.repository.CreditInstitutionRepository;
import vn.com.msb.homeloan.core.service.CreditInstitutionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CreditInstitutionServiceImpl implements CreditInstitutionService {

  private final CreditInstitutionRepository creditInstitutionRepository;

  @Override
  public List<CreditInstitution> getAll() {
    return CreditInstitutionMapper.INSTANCE.toModels(creditInstitutionRepository.findAll());
  }

  @Override
  public Map<String, CreditInstitutionEntity> getMapByCodes(List<String> codes) {
    List<CreditInstitutionEntity> creditInstitutionEntities = creditInstitutionRepository.findByCodes(
        codes);
    if (CollectionUtils.isEmpty(creditInstitutionEntities)) {
      return new HashMap<>();
    }

    return creditInstitutionEntities.stream()
        .collect(
            Collectors.toMap(CreditInstitutionEntity::getCode, Function.identity(), (a, b) -> a));
  }
}
