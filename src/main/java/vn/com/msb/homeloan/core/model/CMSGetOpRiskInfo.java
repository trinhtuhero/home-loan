package vn.com.msb.homeloan.core.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CICRelationshipTypeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CMSGetOpRiskInfo {

  String name;

  CICRelationshipTypeEnum relationshipType;

  Date birthday;

  List<OpRisk> opRisks;
}
