package vn.com.msb.homeloan.api.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface Conditional {

  String message() default "These field is required: ";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String selected();

  String[] required();

  String[] values();

  Size[] size() default {};

  DateTimeFormat[] dateFormat() default {};
}
