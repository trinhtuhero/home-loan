package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.Nationality;
import vn.com.msb.homeloan.core.model.mapper.NationalityMapper;
import vn.com.msb.homeloan.core.repository.NationalityRepository;
import vn.com.msb.homeloan.core.service.NationalityService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NationalityServiceImpl implements NationalityService {

  private final NationalityRepository nationalityRepository;

  @Override
  public List<Nationality> findAll() {
    return NationalityMapper.INSTANCE.toModels(nationalityRepository.findAll());
  }
}
