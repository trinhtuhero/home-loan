package vn.com.msb.homeloan.api.validation;

import java.lang.reflect.InvocationTargetException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;
import vn.com.msb.homeloan.api.validation.DateTimeFormat.DateTimeType;

@Slf4j
public class DateTimeFormatValidator implements ConstraintValidator<DateTimeFormat, Object> {

  private DateTimeType type;
  private String pattern;
  private String[] values;
  private String defaultMessage;
  private String illegalUsageMessage;

  @Override
  public void initialize(DateTimeFormat validatedBean) {
    type = validatedBean.type();
    pattern = validatedBean.pattern();
    values = validatedBean.values();
    defaultMessage = validatedBean.message();
    illegalUsageMessage = validatedBean.illegalUsageMessage();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    boolean isValid = true;
    for (String field : values) {
      try {
        Object checkedValue = BeanUtils.getProperty(object, field);
        if (StringUtils.isEmpty(checkedValue)) {
          continue;
        } else if (!(checkedValue instanceof String)) {
          context.disableDefaultConstraintViolation();
          String message = String.format(illegalUsageMessage, checkedValue.getClass().toString());
          context.buildConstraintViolationWithTemplate(message)
              .addPropertyNode(field).addConstraintViolation();
          isValid = false;
          continue;
        }

        boolean isFormatValid = true;
        String value = (String) checkedValue;
        try {
          switch (type) {
            case DATE:
              LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
              break;
            case DATETIME:
              LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
              break;
            case TIME:
              LocalTime.parse(value, DateTimeFormatter.ofPattern(pattern));
              break;
            case ZONED_DATETIME:
              ZonedDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
              break;
          }
        } catch (DateTimeException ex) {
          isFormatValid = false;
          context.disableDefaultConstraintViolation();
          String message = String.format(defaultMessage, pattern);
          context.buildConstraintViolationWithTemplate(message)
              .addPropertyNode(field).addConstraintViolation();
        }

        isValid = isValid && isFormatValid;
      } catch (IllegalAccessException e) {
        log.error("Accessor method is not available for class : {}, exception : {}",
            object.getClass().getName(), e);
        return false;
      } catch (NoSuchMethodException e) {
        log.error("Field or method is not present on class : {}, exception : {}",
            object.getClass().getName(), e);
        return false;
      } catch (InvocationTargetException e) {
        log.error("An exception occurred while accessing class : {}, exception : {}",
            object.getClass().getName(), e);
        return false;
      }
    }
    return isValid;
  }
}
