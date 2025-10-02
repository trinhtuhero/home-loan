package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.Province;
import vn.com.msb.homeloan.core.model.mapper.ProvinceMapper;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.service.ProvinceService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

  private final ProvinceRepository provinceRepository;

  @Override
  public List<Province> findAll() {
    return ProvinceMapper.INSTANCE.toModels(provinceRepository.findByCodeNotNullOrderByOrderAsc());
  }

  @Override
  public Province findByCode(String code) {
    return ProvinceMapper.INSTANCE.toModel(provinceRepository.findByCode(code).orElse(null));
  }

  @Override
  public List<Province> findAllSysMValue() {
    return ProvinceMapper.INSTANCE.toModels(provinceRepository.findByMvalueCodeNotNullOrderByOrderAsc());
  }
}
