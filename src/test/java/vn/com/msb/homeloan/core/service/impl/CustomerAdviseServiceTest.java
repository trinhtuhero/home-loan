package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;
import vn.com.msb.homeloan.core.model.AdviseCustomer;
import vn.com.msb.homeloan.core.repository.CustomerAdviseRepository;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.service.CustomerAdviseService;
import vn.com.msb.homeloan.core.service.MtkTrackingService;
import vn.com.msb.homeloan.core.service.mockdata.AdviseDataBuild;

@ExtendWith(MockitoExtension.class)
class CustomerAdviseServiceTest {

  CustomerAdviseService customerAdviseService;

  @Mock
  private CustomerAdviseRepository customerAdviseRepository;

  @Mock
  private ProvinceRepository provinceRepository;

  @Mock
  private MtkTrackingService mtkTrackingService;

  @BeforeEach
  public void setup() {
    customerAdviseService = new CustomerAdviseServiceImpl(customerAdviseRepository,
        provinceRepository);
  }

  @Test
  void test__registryAdvise__thenReturnSuccess() {
    AdviseCustomer dto = AdviseDataBuild.buildCustomerRegistryDTO();
    doReturn(AdviseDataBuild.buildAdviseCustomerEntitySaved()).when(customerAdviseRepository)
        .save(any(AdviseCustomerEntity.class));

    doReturn(Optional.of(AdviseDataBuild.buildProvinceEntity())).when(provinceRepository)
        .findByCode("01");

    AdviseCustomer response = customerAdviseService.save(dto);
    assertEquals(response.getCustomerName(), dto.getCustomerName());
  }

  @Test
  void test__registryAdvise__thenThrowProvinceNotFoundException() {
    AdviseCustomer dto = AdviseDataBuild.buildCustomerRegistryDTO();
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> customerAdviseService.save(dto));
    assertNull(exception.getCause());
  }
}
