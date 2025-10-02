package vn.com.msb.homeloan.api.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import vn.com.msb.homeloan.core.util.StringUtils;

public class CustomDateValidator implements ConstraintValidator<CustomDateConstraint, String> {

  private static final String DATE_PATTERN = "yyyyMMdd";

  @Override
  public void initialize(CustomDateConstraint customDate) {
  }

  @Override
  public boolean isValid(String customDateField,
      ConstraintValidatorContext cxt) {
    if (StringUtils.isEmpty(customDateField)) {
      return true;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    try {
      sdf.setLenient(false);
      Date d = sdf.parse(customDateField);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

}