package vn.com.msb.homeloan.core.service.mockdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import vn.com.msb.homeloan.core.entity.DebtPaymentMethodEntity;
import vn.com.msb.homeloan.core.entity.InterestRateEntity;
import vn.com.msb.homeloan.core.model.LoanCalculate;

public class LoanCalculationDataBuild {

  public static LoanCalculate buildLoanCalculate() {
    LoanCalculate loanCalculate = new LoanCalculate();

    loanCalculate.setLoanAmount(new BigDecimal(100000000));
    loanCalculate.setLoanTime(3);
    loanCalculate.setInterestRateKey("499");
    loanCalculate.setDebtPaymentMethodKey("GTDHH");

    return loanCalculate;
  }

  public static List<InterestRateEntity> buildListInterestRateEntity() {
    InterestRateEntity interestRateEntity = new InterestRateEntity();
    interestRateEntity.setInterestRate(4.99);
    interestRateEntity.setKey("499");
    interestRateEntity.setName("4,99% - 3 tháng đầu");
    interestRateEntity.setPrepaymentFee(4.0);
    interestRateEntity.setFromMonth(1);
    interestRateEntity.setToMonth(3);

    List<InterestRateEntity> interestRateEntities = new ArrayList<>();
    interestRateEntities.add(interestRateEntity);
    return interestRateEntities;
  }

  public static List<DebtPaymentMethodEntity> buildListDebtPaymentMethodEntity() {
    DebtPaymentMethodEntity debtPaymentMethodEntity = new DebtPaymentMethodEntity();
    debtPaymentMethodEntity.setCondition(null);
    debtPaymentMethodEntity.setKey("GTDHH");
    debtPaymentMethodEntity.setName("Gốc trả đều hàng tháng lãi giảm dần");
    debtPaymentMethodEntity.setFormulaInterest("100000000*4.99/100*30/365");
    debtPaymentMethodEntity.setFormulaPrincipal("100000000/3");
    debtPaymentMethodEntity.setFromMonth(1);
    debtPaymentMethodEntity.setToMonth(300);

    List<DebtPaymentMethodEntity> debtPaymentMethodEntities = new ArrayList<>();
    debtPaymentMethodEntities.add(debtPaymentMethodEntity);
    return debtPaymentMethodEntities;
  }
}
