package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.PropertyEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.repository.PropertyRepository;
import vn.com.msb.homeloan.core.service.PropertyService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {

  private final PropertyRepository propertyRepository;

  @Override
  public <T> T getByName(String name, Class<T> object) {

    PropertyEntity propertyEntity = propertyRepository.findByName(name)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "properties:name", name));

    if ((String.class).equals(object)) {
      if (propertyEntity.getCurrentValue() != null) {
        return (T) propertyEntity.getCurrentValue();
      } else {
        return (T) propertyEntity.getDefaultValue();
      }
    }
    return null;
  }

  @Override
  public String getHomePageConfiguration(String type) {
    if (StringUtils.isEmpty(type)) {
      return getByName(Constants.HOME_PAGE_CONFIG, String.class);
    } else if ("HOME_PAGE".equals(type)) {
      return getByName(Constants.HOME_PAGE_CONFIG, String.class);
    } else if ("BAT_DONG_SAN".equals(type)) {
      return getByName(Constants.BAT_DONG_SAN_CONFIG, String.class);
    } else if ("TIEU_DUNG".equals(type)) {
      return getByName(Constants.TIEU_DUNG_CONFIG, String.class);
    } else if ("XAY_SUA_NHA".equals(type)) {
      return getByName(Constants.XAY_SUA_NHA_CONFIG, String.class);
    } else if ("HOUSEHOLD".equals(type)) {
      return getByName(Constants.HOUSEHOLD, String.class);
    } else {
      return null;
    }
  }

  @Override
  public String saveOrUpdate(String name, String currentValue) {

    log.info("Start saveOrUpdate: {}", name);
    PropertyEntity entity = propertyRepository.findByName(name).orElse(null);
    if (entity == null) {
      log.info("config not exist, save new config");
      entity = new PropertyEntity();
      entity.setName(name);
      entity.setCurrentValue(currentValue);
    } else {
      log.info("Update config have id: {}", entity.getUuid());
      entity.setCurrentValue(currentValue);
    }
    entity = propertyRepository.save(entity);
    return entity.getCurrentValue();
  }
}
