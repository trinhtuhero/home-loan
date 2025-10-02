package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.WardEntity;
import vn.com.msb.homeloan.core.model.Ward;
import vn.com.msb.homeloan.core.repository.WardRepository;
import vn.com.msb.homeloan.core.service.WardService;

@ExtendWith(MockitoExtension.class)
class WardServiceTest {

  WardService wardService;

  @Mock
  WardRepository wardRepository;

  @BeforeEach
  void setUp() {
    this.wardService = new WardServiceImpl(
        wardRepository);
  }

  @Test
  void givenValidInput_ThenAll_shouldReturnSuccess() {
    List<WardEntity> wards = new ArrayList<>();
    wards.add(WardEntity.builder().build());
    wards.add(WardEntity.builder().build());
    wards.add(WardEntity.builder().build());
    doReturn(wards).when(wardRepository).findByProvinceCodeAndDistrictCode("01", "001");

    List<Ward> results = wardService.findByDistrictCode("01", "001");
    assertEquals(results.size(), 3);
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnSuccess() {
    WardEntity ward = WardEntity.builder()
        .code("01")
        .build();
    doReturn(Optional.of(ward)).when(wardRepository).findByCode("01");

    Ward result = wardService.findByCode("01");
    assertEquals(ward.getCode(), result.getCode());
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnNull() {
    doReturn(Optional.empty()).when(wardRepository).findByCode("01");
    Ward result = wardService.findByCode("01");
    assertEquals(result, null);
  }
}