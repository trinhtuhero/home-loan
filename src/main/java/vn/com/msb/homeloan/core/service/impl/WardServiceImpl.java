package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.Ward;
import vn.com.msb.homeloan.core.model.mapper.WardMapper;
import vn.com.msb.homeloan.core.repository.WardRepository;
import vn.com.msb.homeloan.core.service.WardService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WardServiceImpl implements WardService {

  private final WardRepository wardRepository;

  @Override
  public List<Ward> findByDistrictCode(String provinceCode, String districtCode) {
    return WardMapper.INSTANCE.toModels(
        wardRepository.findByProvinceCodeAndDistrictCode(provinceCode, districtCode));
  }

  @Override
  public List<Ward> findByMvalueDistrictCode(String provinceCode, String districtCode) {
    return WardMapper.INSTANCE.toModels(
      wardRepository.findByMvalueProvinceCodeAndMvalueDistrictCode(provinceCode, districtCode));
  }

  @Override
  public Ward findByCode(String code) {
    return WardMapper.INSTANCE.toModel(wardRepository.findByCode(code).orElse(null));
  }
}
