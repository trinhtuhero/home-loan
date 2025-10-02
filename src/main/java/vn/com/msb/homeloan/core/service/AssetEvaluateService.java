package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.model.AssetEvaluate;

public interface AssetEvaluateService {

  AssetEvaluateEntity findByUuid(String uuid);

  AssetEvaluate save(AssetEvaluate assetEvaluate);
}
