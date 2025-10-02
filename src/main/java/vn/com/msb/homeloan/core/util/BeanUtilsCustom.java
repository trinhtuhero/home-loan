package vn.com.msb.homeloan.core.util;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;

@Slf4j
public class BeanUtilsCustom {

  public static <T> List<T> copyPropertiesArray(List<T> source, Class<T> destinationClass) {
    try {
      List<T> list = new ArrayList();
      for (int i = 0; i < source.size(); i++) {
        T obj = destinationClass.newInstance();
        BeanUtils.copyProperties(obj, source.get(i));
        list.add(obj);
      }
      return list;
    } catch (Exception ex) {
      log.error("[BeanUtilsCustom] copy properties array error: " + ex.getMessage());
      throw new ApplicationException(ErrorEnum.SERVER_ERROR);
    }
  }
}
