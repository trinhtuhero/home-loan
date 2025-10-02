package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CollateralOwnerMapTypeEnum;

@Data
@Entity
@Table(name = "collateral_owner_map", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CollateralOwnerMapEntity extends BaseEntity {

  @Column(name = "loan_id")
  private String loanId;

  @Column(name = "collateral_owner_id")
  private String collateralOwnerId;

  @Column(name = "collateral_id")
  private String collateralId;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CollateralOwnerMapTypeEnum type;
}
