package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.District;
import vn.com.msb.homeloan.core.model.mapper.DistrictMapper;
import vn.com.msb.homeloan.core.repository.DistrictRepository;
import vn.com.msb.homeloan.core.service.DistrictService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

  private final DistrictRepository districtRepository;

  @Override
  public List<District> findByProvinceCode(String provinceCode) {
    return DistrictMapper.INSTANCE.toModels(districtRepository.findByProvinceCode(provinceCode));
  }

  @Override
  public District findByCode(String code) {
    return DistrictMapper.INSTANCE.toModel(districtRepository.findByCode(code).orElse(null));
  }

  @Override
  public List<District> findByProvinceCodeMValue(String code) {
    return DistrictMapper.INSTANCE.toModels(districtRepository.findByMvalueProvinceCode(code));
  }
}
