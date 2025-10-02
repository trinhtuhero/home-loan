package vn.com.msb.homeloan.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Only applies to {@link String}.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(DateTimeFormat.List.class)
@Documented
@Constraint(validatedBy = {DateTimeFormatValidator.class})
public @interface DateTimeFormat {

  String message() default "These fields must have %s format: ";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String illegalUsageMessage() default "Illegal usage of DateTimeFormat constraint: can only be used on String type, but found used on %s";

  DateTimeType type() default DateTimeType.DATE;

  String pattern() default "yyyy-MM-dd";

  String[] values() default {};

  enum DateTimeType {
    DATE,
    DATETIME,
    TIME,
    ZONED_DATETIME
  }

  @Target({FIELD})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    DateTimeFormat[] value();
  }
}
