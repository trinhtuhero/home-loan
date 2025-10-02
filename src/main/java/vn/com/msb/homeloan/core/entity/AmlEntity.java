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
@Table(name = "aml", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AmlEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "id_passport")
  String idPassport;

  @Column(name = "meta_data")
  String metaData;

  @Column(name = "pass")
  Boolean pass;

  @Column(name = "check_date")
  Date checkDate;

  @Column(name = "active")
  Boolean active;
}
