package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.Ward;

public interface WardService {

  List<Ward> findByDistrictCode(String provinceCode, String districtCode);

  List<Ward> findByMvalueDistrictCode(String provinceCode, String districtCode);

  Ward findByCode(String code);
}
