package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "m_value", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MvalueEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "asset_code")
  String assetCode;

  @Column(name = "check_date")
  Date checkDate;

  @Column(name = "meta_data")
  String metaData;
}
