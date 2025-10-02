package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;

public interface PlaceOfIssueIdCardService {

  List<PlaceOfIssueIdCardEntity> getAll();

  String getNameByCode(String code);
}
