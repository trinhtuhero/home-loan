package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.model.OtherEvaluate;

public interface OtherEvaluateService {

  List<OtherEvaluate> saves(List<OtherEvaluate> otherEvaluates);

  void deleteByUuid(String uuid);
}
