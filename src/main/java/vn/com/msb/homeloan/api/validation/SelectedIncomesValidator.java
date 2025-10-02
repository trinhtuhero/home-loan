package vn.com.msb.homeloan.api.validation;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;

public class SelectedIncomesValidator implements
    ConstraintValidator<SelectedIncomesConstraint, String[]> {

  @Override
  public void initialize(SelectedIncomesConstraint constraint) {
  }

  @Override
  public boolean isValid(String[] selectedIncomes,
      ConstraintValidatorContext cxt) {
    String[] collect1 = {
        CMSTabEnum.SALARY_INCOME.getCode()
        , CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode()
        , CMSTabEnum.BUSINESS_INCOME.getCode()
        , CMSTabEnum.OTHERS_INCOME.getCode()
    };
    String[] collect2 = {
        CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME.getCode()
        , CMSTabEnum.ASSUMING_OTHERS_INCOME.getCode()
    };

    if (selectedIncomes == null) {
      return true;
    }

    boolean check1 = false;
    boolean check2 = false;
    for (String str : selectedIncomes) {
      if (!Arrays.asList(collect1).contains(str) && !Arrays.asList(collect2).contains(str)) {
        return false;
      }

      if (Arrays.asList(collect1).contains(str)) {
        check1 = true;
      }

      if (Arrays.asList(collect2).contains(str)) {
        check2 = true;
      }
    }
    if ((check1 & check2) == true) {
      return false;
    }
    return true;
  }
}
