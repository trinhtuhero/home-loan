package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.AssetTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;
import vn.com.msb.homeloan.core.model.mapper.AssetEvaluateItemMapper;
import vn.com.msb.homeloan.core.repository.AssetEvaluateItemRepository;
import vn.com.msb.homeloan.core.service.AssetEvaluateItemService;

@ExtendWith(MockitoExtension.class)
class AssetEvaluateItemServiceTest {

  private final String ASSET_EVALUATE_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String ASSET_EVALUATE_ITEM_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";
  private final String ASSET_EVALUATE_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String ASSET_EVALUATE_ID_OTHER = "03f9e024-7ec4-4ed3-8f1f-330d232b63aa";

  AssetEvaluateItemService assetEvaluateItemService;

  @Mock
  AssetEvaluateItemRepository assetEvaluateItemRepository;

  @BeforeEach
  void setUp() {
    this.assetEvaluateItemService = new AssetEvaluateItemServiceImpl(
        assetEvaluateItemRepository);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() throws ParseException {
    List<AssetEvaluateItemEntity> assetEvaluateItems = new ArrayList<>();
    List<AssetEvaluateItemEntity> assetEvaluateItemsInDB = new ArrayList<>();
    AssetEvaluateItemEntity assetEvaluateItem = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    AssetEvaluateItemEntity assetEvaluateItemDelete = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID_OTHER)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluateItemsInDB.add(assetEvaluateItem);
    assetEvaluateItemsInDB.add(assetEvaluateItemDelete);
    doReturn(assetEvaluateItemsInDB).when(assetEvaluateItemRepository)
        .findByAssetEvalId(ASSET_EVALUATE_ID);

    List<AssetEvaluateItem> assetEvaluateItemsInsert = AssetEvaluateItemMapper.INSTANCE.toModels(
        assetEvaluateItems);

    doReturn(assetEvaluateItems).when(assetEvaluateItemRepository).saveAll(any());
    List<AssetEvaluateItem> result = assetEvaluateItemService.save(assetEvaluateItemsInsert);

    assertEquals(result.get(0).getAssetEvalId(), ASSET_EVALUATE_ID);
  }


  @Test
  void givenValidInput_ThenSave_shouldReturnRESOURCE_NOT_FOUND() throws ParseException {
    List<AssetEvaluateItemEntity> assetEvaluateItems = new ArrayList<>();
    List<AssetEvaluateItemEntity> assetEvaluateItemsInDB = new ArrayList<>();
    AssetEvaluateItemEntity assetEvaluateItem = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    AssetEvaluateItemEntity assetEvaluateItemDelete = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID_OTHER)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluateItemsInDB.add(assetEvaluateItem);
    assetEvaluateItemsInDB.add(assetEvaluateItemDelete);
    doReturn(assetEvaluateItemsInDB).when(assetEvaluateItemRepository)
        .findByAssetEvalId(ASSET_EVALUATE_ID);

    List<AssetEvaluateItem> assetEvaluateItemsInsert = AssetEvaluateItemMapper.INSTANCE.toModels(
        assetEvaluateItems);

    assetEvaluateItemsInsert.get(0).setUuid("uuid");
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      assetEvaluateItemService.save(assetEvaluateItemsInsert);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnASSET_EVAL_ID_NOT_UPDATE() throws ParseException {
    List<AssetEvaluateItemEntity> assetEvaluateItems = new ArrayList<>();
    AssetEvaluateItemEntity assetEvaluateItem = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    AssetEvaluateItemEntity assetEvaluateItemError = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID_OTHER)
        .assetEvalId(ASSET_EVALUATE_ID_OTHER)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    assetEvaluateItems.add(assetEvaluateItem);
    assetEvaluateItems.add(assetEvaluateItemError);

    List<AssetEvaluateItemEntity> assetEvaluateItemsInDB = new ArrayList<>();
    AssetEvaluateItemEntity assetEvaluateItem1 = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    AssetEvaluateItemEntity assetEvaluateItem2 = AssetEvaluateItemEntity.builder()
        .uuid(ASSET_EVALUATE_ITEM_ID_OTHER)
        .assetEvalId(ASSET_EVALUATE_ID)
        .assetType(AssetTypeEnum.REAL_ESTATE)
        .legalRecord("legal_record")
        .value(1111L)
        .assetDescription("description")
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    assetEvaluateItemsInDB.add(assetEvaluateItem1);
    assetEvaluateItemsInDB.add(assetEvaluateItem2);
    doReturn(assetEvaluateItemsInDB).when(assetEvaluateItemRepository)
        .findByAssetEvalId(ASSET_EVALUATE_ID);

    List<AssetEvaluateItem> assetEvaluateItemsInsert = AssetEvaluateItemMapper.INSTANCE.toModels(
        assetEvaluateItems);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      assetEvaluateItemService.save(assetEvaluateItemsInsert);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ASSET_EVAL_ID_NOT_UPDATE.getCode());
  }
}
