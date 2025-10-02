package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.AssetTypeEnum;

@Data
@Entity
@Table(name = "asset_evaluate_item", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetEvaluateItemEntity extends BaseEntity {

  @Column(name = "asset_eval_id")
  String assetEvalId;

  @Column(name = "asset_type")
  @Enumerated(EnumType.STRING)
  AssetTypeEnum assetType;

  @Column(name = "asset_type_other")
  String assetTypeOther;

  @Column(name = "legal_record")
  String legalRecord;

  @Column(name = "value")
  Long value;

  @Column(name = "asset_description")
  String assetDescription;
}
