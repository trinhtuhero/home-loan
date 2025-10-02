package vn.com.msb.homeloan.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "document_mapping", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DocumentMappingEntity extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "parent_id")
  String parentId;

  @Column(name = "code")
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "description")
  String description;

  @Column(name = "collateral_type")
  String collateralType;

  @Column(name = "type")
  String type;

  @Column(name = "is_required")
  String isRequired;
}
