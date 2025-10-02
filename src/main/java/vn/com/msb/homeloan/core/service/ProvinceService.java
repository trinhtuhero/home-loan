package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.Province;

public interface ProvinceService {

  List<Province> findAll();

  Province findByCode(String code);

  List<Province> findAllSysMValue();
}
