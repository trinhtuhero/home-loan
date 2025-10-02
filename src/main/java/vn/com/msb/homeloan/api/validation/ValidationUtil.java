package vn.com.msb.homeloan.api.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import vn.com.msb.homeloan.core.util.MessageUtil;

public class ValidationUtil {

  public static List<CustomFieldError> getMessageError(BindingResult bind, MessageUtil messageUtil,
      Locale locale) {
    List<CustomFieldError> customFieldErrorList = new ArrayList<>();
    List<String> list = new ArrayList<>();
    for (ObjectError error : bind.getAllErrors()) {
      if (error instanceof FieldError) {
        FieldError fe = (FieldError) error;
        if (!list.contains(fe.getCode())) {
          list.add(fe.getField());
          CustomFieldError customFieldError = new CustomFieldError();
          customFieldError.setField(fe.getField());
          customFieldError.setError(messageUtil.getMessage(fe.getCode(), locale));
          customFieldErrorList.add(customFieldError);
        }
      }
    }
    return customFieldErrorList;
  }
}
