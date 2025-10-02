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

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Size.List.class)
@Documented
@Constraint(validatedBy = {})
public @interface Size {

  String message() default "The size of these fields must be between %s and %s : ";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * @return size the element must be higher or equal to
   */
  int min() default 0;

  /**
   * @return size the element must be lower or equal to
   */
  int max() default Integer.MAX_VALUE;

  String[] values();

  /**
   * Defines several {@link javax.validation.constraints.Size} annotations on the same element.
   *
   * @see javax.validation.constraints.Size
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    Size[] value();
  }
}
