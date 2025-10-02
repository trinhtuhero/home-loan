package vn.com.msb.homeloan.api.validation;

import static org.springframework.util.StringUtils.isEmpty;

import java.lang.reflect.InvocationTargetException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

@Slf4j
public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

  private String selected;
  private String[] required;
  private String message;
  private String[] values;
  private Size[] sizes;
  private DateTimeFormat[] formats;

  @Override
  public void initialize(Conditional requiredIfChecked) {
    selected = requiredIfChecked.selected();
    required = requiredIfChecked.required();
    message = requiredIfChecked.message();
    values = requiredIfChecked.values();
    this.sizes = requiredIfChecked.size();
    formats = requiredIfChecked.dateFormat();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    boolean valid = true;
    try {
      Object checkedValue = BeanUtils.getProperty(object, selected);
      if (values.length == 0 || Arrays.asList(values).contains(checkedValue)) {
        valid = validateRequireField(object, context, valid);
        valid = validateSize(object, context, valid);
        valid = validateDateTimeFormat(object, context, valid);
      }
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
    return valid;
  }

  private boolean validateRequireField(Object object, ConstraintValidatorContext context,
      boolean valid) {
    for (String propName : required) {
      boolean isPropValid = false;
      Object requiredValue = null;
      try {
        if (propName.contains(".")) {
          requiredValue = BeanUtils.getNestedProperty(object, propName);
        } else {
          requiredValue = BeanUtils.getProperty(object, propName);
        }
      } catch (Exception e) {
        log.info(e.getMessage());
      }

      if (requiredValue instanceof String) {
        isPropValid = !isEmpty(requiredValue);
      } else {
        isPropValid = requiredValue != null;
      }
      valid = isPropValid && valid;
      if (!isPropValid) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).
            addPropertyNode(propName).addConstraintViolation();
      }
    }
    return valid;
  }

  private boolean validateSize(Object object, ConstraintValidatorContext context, boolean valid) {
    for (Size size : sizes) {
      String[] propNames = size.values();
      int min = size.min();
      int max = size.max();
      for (String propName : propNames) {
        Object requiredValue = null;
        try {
          if (propName.contains(".")) {
            requiredValue = BeanUtilsBean.getInstance().getPropertyUtils()
                .getNestedProperty(object, propName);
          } else {
            requiredValue = BeanUtilsBean.getInstance().getPropertyUtils()
                .getProperty(object, propName);
          }
        } catch (Exception e) {
          log.info(e.getMessage());
        }

        if (requiredValue != null) {
          if (requiredValue instanceof String) {
            String value = (String) requiredValue;
            valid = validateSizeForValue(context, valid, size, min, max, propName, value);
          } else if (requiredValue instanceof Map) {
            Map<String, String> map = (Map) requiredValue;
            for (Map.Entry<String, String> entry : map.entrySet()) {
              String value = map.get(entry.getKey());
              valid = validateSizeForValue(context, valid, size, min, max, propName, value);
            }
          } else if (requiredValue instanceof List) {
            List list = (List) requiredValue;
            boolean isListSizeValid = min <= list.size() && max >= list.size();
            valid = valid && isListSizeValid;
            if (!isListSizeValid) {
              context.disableDefaultConstraintViolation();
              String message = String.format(size.message(), min, max);
              context.buildConstraintViolationWithTemplate(message)
                  .addPropertyNode(propName).addConstraintViolation();
            }
          }
        }
      }
    }
    return valid;
  }

  private boolean validateSizeForValue(ConstraintValidatorContext context, boolean valid, Size size,
      int min, int max, String propName, String value) {
    boolean isPropValid;
    int length = value.length();
    isPropValid = length >= min && length <= max;
    valid = isPropValid && valid;
    if (!isPropValid) {
      context.disableDefaultConstraintViolation();
      String message = String.format(size.message(), min, max);
      context.buildConstraintViolationWithTemplate(message)
          .addPropertyNode(propName).addConstraintViolation();
    }
    return valid;
  }

  private boolean validateDateTimeFormat(Object object, ConstraintValidatorContext context,
      boolean valid) {
    for (DateTimeFormat format : formats) {
      String[] propNames = format.values();

      for (String propName : propNames) {
        Object requiredValue = null;
        try {
          if (propName.contains(".")) {
            requiredValue = BeanUtilsBean.getInstance().getPropertyUtils()
                .getNestedProperty(object, propName);
          } else {
            requiredValue = BeanUtilsBean.getInstance().getPropertyUtils()
                .getProperty(object, propName);
          }
        } catch (Exception e) {
          log.info(e.getMessage());
        }

        if (requiredValue != null) {
          if (!(requiredValue instanceof String)) {
            context.disableDefaultConstraintViolation();
            String message = String
                .format(format.illegalUsageMessage(), requiredValue.getClass().toString());
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propName).addConstraintViolation();
            valid = false;
            continue;
          }

          boolean isFormatValid = true;
          String value = (String) requiredValue;
          String pattern = format.pattern();
          try {
            switch (format.type()) {
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
            String message = String.format(format.message(), pattern);
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propName).addConstraintViolation();
          }

          valid = valid && isFormatValid;
        }
      }
    }
    return valid;
  }
}
