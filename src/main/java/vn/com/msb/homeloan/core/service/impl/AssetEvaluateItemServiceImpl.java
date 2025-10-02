package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.AssetTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;
import vn.com.msb.homeloan.core.model.mapper.AssetEvaluateItemMapper;
import vn.com.msb.homeloan.core.repository.AssetEvaluateItemRepository;
import vn.com.msb.homeloan.core.service.AssetEvaluateItemService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AssetEvaluateItemServiceImpl implements AssetEvaluateItemService {

  private final AssetEvaluateItemRepository assetEvaluateItemRepository;

  @Transactional
  @Override
  public List<AssetEvaluateItem> save(List<AssetEvaluateItem> assetEvaluateItems) {
    List<AssetEvaluateItemEntity> assetEvaluateItemEntities = AssetEvaluateItemMapper.INSTANCE.toEntities(
        assetEvaluateItems);
    List<AssetEvaluateItemEntity> assetEvaluateItemEntitiesInDB = assetEvaluateItemRepository.findByAssetEvalId(
        assetEvaluateItemEntities.get(0).getAssetEvalId());
    for (AssetEvaluateItemEntity assetEvaluateItemEntity : assetEvaluateItemEntities) {
      validateInput(assetEvaluateItemEntity);
      if (!StringUtils.isEmpty(assetEvaluateItemEntity.getUuid())) {
        List<AssetEvaluateItemEntity> lstAssetEvaluateItem = assetEvaluateItemEntitiesInDB.stream()
            .filter(aei -> assetEvaluateItemEntity.getUuid().equals(aei.getUuid()))
            .collect(Collectors.toList());
        assetEvaluateItemEntitiesInDB.removeIf(
            aei -> assetEvaluateItemEntity.getUuid().equals(aei.getUuid()));
        if (CollectionUtils.isEmpty(lstAssetEvaluateItem)) {
          throw new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
              assetEvaluateItemEntity.getUuid());
        } else {
          AssetEvaluateItemEntity assetEvaluateEntityInDB = lstAssetEvaluateItem.get(0);
          //ko được thay đổi asset_eval_id
          if (!assetEvaluateEntityInDB.getAssetEvalId()
              .equals(assetEvaluateItemEntity.getAssetEvalId())) {
            throw new ApplicationException(ErrorEnum.ASSET_EVAL_ID_NOT_UPDATE);
          }
        }
      }
    }
    if (assetEvaluateItemEntitiesInDB != null && !assetEvaluateItemEntitiesInDB.isEmpty()) {
      assetEvaluateItemRepository.deleteAll(assetEvaluateItemEntitiesInDB);
    }
    return AssetEvaluateItemMapper.INSTANCE.toModels(
        assetEvaluateItemRepository.saveAll(assetEvaluateItemEntities));
  }

  private void validateInput(AssetEvaluateItemEntity assetEvaluateItemEntity) {
    Map<String, String> errorDetail = new HashMap<>();
    if (assetEvaluateItemEntity.getAssetType().equals(AssetTypeEnum.OTHER)) {
      if (StringUtils.isEmpty(assetEvaluateItemEntity.getAssetTypeOther())) {
        errorDetail.put("not_blank.asset_type_other", "must not be blank");
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
