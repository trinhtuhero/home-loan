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
import vn.com.msb.homeloan.core.constant.ApprovalFlowEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod2Enum;

@Data
@Entity
@Table(name = "common_income", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonIncomeEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  //luồng phê duyệt
  @Column(name = "approval_flow")
  @Enumerated(EnumType.STRING)
  ApprovalFlowEnum approvalFlow;

  //phương pháp ghi nhận thu nhập 1
  @Column(name = "recognition_method_1")
  @Enumerated(EnumType.STRING)
  RecognitionMethod1Enum recognitionMethod1;

  //phương pháp ghi nhận thu nhập 2
  @Column(name = "recognition_method_2")
  @Enumerated(EnumType.STRING)
  RecognitionMethod2Enum recognitionMethod2;

  @Column(name = "selected_incomes")
  String selectedIncomes;
}
