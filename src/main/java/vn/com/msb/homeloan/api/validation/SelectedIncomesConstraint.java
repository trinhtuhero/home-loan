package vn.com.msb.homeloan.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SelectedIncomesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectedIncomesConstraint {

  String message() default "The selected income is not valid: not null and values in (SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME, OTHERS_INCOME) or in (ASSUMING_TOTAL_ASSETS_INCOME, ASSUMING_OTHERS_INCOME) and not in both";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
