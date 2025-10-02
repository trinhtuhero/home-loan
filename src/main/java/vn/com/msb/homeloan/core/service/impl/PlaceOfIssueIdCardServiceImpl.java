package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.service.PlaceOfIssueIdCardService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlaceOfIssueIdCardServiceImpl implements PlaceOfIssueIdCardService {

  private final PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;

  @Override
  public List<PlaceOfIssueIdCardEntity> getAll() {
    return placeOfIssueIdCardRepository.findAll();
  }

  @Override
  public String getNameByCode(String code) {
    PlaceOfIssueIdCardEntity entity =
        code != null ? placeOfIssueIdCardRepository.findByCode(code).orElse(null) : null;
    return entity != null ? entity.getName() : null;
  }
}
