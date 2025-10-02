package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.DebtPaymentMethodEntity;
import vn.com.msb.homeloan.core.entity.InterestRateEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanCalculate;
import vn.com.msb.homeloan.core.model.LoanCalculation;
import vn.com.msb.homeloan.core.repository.DebtPaymentMethodRepository;
import vn.com.msb.homeloan.core.repository.InterestRateRepository;
import vn.com.msb.homeloan.core.service.LoanCalculationService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanCalculationServiceImpl implements LoanCalculationService {

  private final InterestRateRepository interestRateRepository;

  private final DebtPaymentMethodRepository debtPaymentMethodRepository;

  @Override
  public List<LoanCalculation> calculateLoan(LoanCalculate loanCalculate) throws ScriptException {
    if (Boolean.FALSE.equals(
        validateMonth(loanCalculate.getLoanTime(), loanCalculate.getDebtPaymentMethodKey()))) {
      throw new ApplicationException(ErrorEnum.DEBT_METHOD_MONTH);
    }
    log.info("Start calculate: {}", loanCalculate);
    List<InterestRateEntity> interestRateEntities = interestRateRepository.findAllByKey(
        loanCalculate.getInterestRateKey());
    log.info("interest rate found: {}", interestRateEntities);
    List<DebtPaymentMethodEntity> debtPaymentMethodEntities = debtPaymentMethodRepository.findAllByKey(
        loanCalculate.getDebtPaymentMethodKey());
    log.info("debtPaymentMethod found: {}", debtPaymentMethodEntities);
    List<LoanCalculation> result = new ArrayList<>();
    int monthIndex = 1;
    Map<String, Object> params = new HashMap<>();
    params.put("TST", loanCalculate.getLoanAmount());
    params.put("KHV", loanCalculate.getLoanTime());
    BigDecimal principalPrev = loanCalculate.getLoanAmount();
    while (monthIndex <= loanCalculate.getLoanTime()) {
      if (principalPrev.compareTo(BigDecimal.valueOf(0)) != 0) {
        if (monthIndex == 49) {
          params.put("DNG", principalPrev);
        }
        LoanCalculation loanCalculation = new LoanCalculation();
        loanCalculation.setMonth(monthIndex);
        int finalMonthIndex = monthIndex;
        DebtPaymentMethodEntity currentMethod = debtPaymentMethodEntities.stream()
            .filter(debtPaymentMethodEntity
                -> debtPaymentMethodEntity.getFromMonth() <= finalMonthIndex &&
                debtPaymentMethodEntity.getToMonth() >= finalMonthIndex).findFirst().orElse(null);
        InterestRateEntity currentRate = interestRateEntities.stream().filter(interestRate
            -> interestRate.getFromMonth() <= finalMonthIndex &&
            interestRate.getToMonth() >= finalMonthIndex).findFirst().orElse(null);
        log.info("currentMethod found {} and currentRate found {}", currentMethod, currentRate);
        if (currentRate != null && currentMethod != null) {
          principalPrev = principalPrev.setScale(0, RoundingMode.HALF_UP);
          loanCalculation.setPrincipalAmount(principalPrev.toPlainString());
          log.info("Month: {}", monthIndex);
          Map<String, Object> paramsInterest = new HashMap<>();
          paramsInterest.put("DNG", principalPrev);
          paramsInterest.put("LS", currentRate.getInterestRate());
          BigDecimal interest = new BigDecimal(convertFormula(currentMethod.getFormulaInterest(),
              paramsInterest).toString()).setScale(0, RoundingMode.HALF_UP);
          if (Boolean.TRUE.equals(checkCondition(currentMethod.getCondition(), monthIndex))
              || monthIndex == loanCalculate.getLoanTime()) {
            BigDecimal paymentAmount = new BigDecimal(
                convertFormula(currentMethod.getFormulaPrincipal(), params).toString())
                .setScale(0, RoundingMode.HALF_UP);

            if (paymentAmount.compareTo(principalPrev) > 0
                || monthIndex == loanCalculate.getLoanTime()) {
              loanCalculation.setTotal(principalPrev.add(interest).toPlainString());
              loanCalculation.setPrincipalAmountMonthly(principalPrev.toPlainString());
              principalPrev = new BigDecimal(0);
            } else {
              loanCalculation.setTotal(paymentAmount.add(interest).toPlainString());
              principalPrev = principalPrev.subtract(paymentAmount);
              loanCalculation.setPrincipalAmountMonthly(
                  paymentAmount.setScale(0, RoundingMode.HALF_UP).toPlainString());
            }
          } else {
            loanCalculation.setTotal(interest.toPlainString());
            loanCalculation.setPrincipalAmountMonthly("0");
          }
          loanCalculation.setInterestRate(currentRate.getInterestRate());
          loanCalculation.setInterestMonthly(interest.toPlainString());
          loanCalculation.setPrepaymentFee(currentRate.getPrepaymentFee());
          result.add(loanCalculation);
        }
      } else {
        result.add(LoanCalculation.builder().month(monthIndex)
            .interestRate(0.0)
            .principalAmount("0")
            .prepaymentFee(0.0)
            .principalAmountMonthly("0")
            .interestMonthly("0")
            .total("0")
            .build());
      }
      monthIndex++;
    }
    return result;
  }

  public Boolean checkCondition(String condition, int month) throws ScriptException {
    Map<String, Object> paramsCondition = new HashMap<>();
    paramsCondition.put("T", month);
    if (condition == null) {
      return true;
    }

    return Boolean.valueOf(convertFormula(condition, paramsCondition).toString());
  }


  private Object convertFormula(String formula, Map<String, Object> param) throws ScriptException {
    String result = StrSubstitutor.replace(formula, param, "${", "}");
    log.info("Formula replaced: {}", result);
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

    return engine.eval(result).toString();
  }

  private Boolean validateMonth(int month, String debtPaymentMethod) {
    return 84 <= month || (!"GTD".equals(debtPaymentMethod) && !"GGD".equals(debtPaymentMethod));
  }
}
