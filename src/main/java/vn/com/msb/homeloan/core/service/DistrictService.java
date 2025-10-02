package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.District;

public interface DistrictService {

  List<District> findByProvinceCode(String provinceCode);

  District findByCode(String code);

  List<District> findByProvinceCodeMValue(String code);
}
