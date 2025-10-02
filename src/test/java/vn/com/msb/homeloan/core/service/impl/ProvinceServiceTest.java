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
import vn.com.msb.homeloan.core.entity.ProvinceEntity;
import vn.com.msb.homeloan.core.model.Province;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.service.ProvinceService;

@ExtendWith(MockitoExtension.class)
class ProvinceServiceTest {

  ProvinceService provinceService;

  @Mock
  ProvinceRepository provinceRepository;

  @BeforeEach
  void setUp() {
    this.provinceService = new ProvinceServiceImpl(
        provinceRepository);
  }

  @Test
  void givenValidInput_ThenAll_shouldReturnSuccess() {
    List<ProvinceEntity> provinces = new ArrayList<>();
    provinces.add(ProvinceEntity.builder().build());
    provinces.add(ProvinceEntity.builder().build());
    provinces.add(ProvinceEntity.builder().build());
    doReturn(provinces).when(provinceRepository).findByCodeNotNullOrderByOrderAsc();

    List<Province> results = provinceService.findAll();
    assertEquals(results.size(), 3);
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnSuccess() {
    ProvinceEntity province = ProvinceEntity.builder()
        .code("01")
        .build();
    doReturn(Optional.of(province)).when(provinceRepository).findByCode("01");

    Province result = provinceService.findByCode("01");
    assertEquals(province.getCode(), result.getCode());
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnNull() {
    doReturn(Optional.empty()).when(provinceRepository).findByCode("01");
    Province result = provinceService.findByCode("01");
    assertEquals(result, null);
  }
}