package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.PropertyEntity;
import vn.com.msb.homeloan.core.repository.PropertyRepository;
import vn.com.msb.homeloan.core.service.mockdata.PropertiesDataBuild;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

  @Mock
  private PropertyRepository propertyRepository;
  @InjectMocks
  private PropertyServiceImpl underTest;

  @Test
  void test__Update__ThenReturnSuccess() {
    PropertyEntity entity = PropertiesDataBuild.buildPropertyEntity();
    Optional<PropertyEntity> optional = Optional.of(entity);
    when(propertyRepository.findByName(anyString())).thenReturn(optional);
    when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(entity);
    String result = underTest.saveOrUpdate(PropertiesDataBuild.buildUpdateConfig().getName(),
        PropertiesDataBuild.buildUpdateConfig().getCurrentValue());

    assertEquals(result, PropertiesDataBuild.buildUpdateConfig().getCurrentValue());
  }

  @Test
  void test__Save__ThenReturnSuccess() {
    PropertyEntity entity = PropertiesDataBuild.buildPropertyEntity();
    when(propertyRepository.findByName(anyString())).thenReturn(Optional.empty());
    when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(entity);
    String result = underTest.saveOrUpdate(PropertiesDataBuild.buildUpdateConfig().getName(),
        PropertiesDataBuild.buildUpdateConfig().getCurrentValue());
    assertEquals(result, PropertiesDataBuild.buildUpdateConfig().getCurrentValue());

  }
}