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
import vn.com.msb.homeloan.core.entity.DistrictEntity;
import vn.com.msb.homeloan.core.model.District;
import vn.com.msb.homeloan.core.repository.DistrictRepository;
import vn.com.msb.homeloan.core.service.DistrictService;

@ExtendWith(MockitoExtension.class)
class DistrictServiceTest {

  DistrictService districtService;

  @Mock
  DistrictRepository districtRepository;

  @BeforeEach
  void setUp() {
    this.districtService = new DistrictServiceImpl(
        districtRepository);
  }

  @Test
  void givenValidInput_ThenAll_shouldReturnSuccess() {
    List<DistrictEntity> districts = new ArrayList<>();
    districts.add(DistrictEntity.builder().build());
    districts.add(DistrictEntity.builder().build());
    districts.add(DistrictEntity.builder().build());
    doReturn(districts).when(districtRepository).findByProvinceCode("01");

    List<District> results = districtService.findByProvinceCode("01");
    assertEquals(results.size(), 3);
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnSuccess() {
    DistrictEntity district = DistrictEntity.builder()
        .code("01")
        .build();
    doReturn(Optional.of(district)).when(districtRepository).findByCode("01");

    District result = districtService.findByCode("01");
    assertEquals(result.getCode(), "01");
  }

  @Test
  void givenValidInput_ThenFindByCode_shouldReturnNull() {
    doReturn(Optional.empty()).when(districtRepository).findByCode("01");

    District result = districtService.findByCode("01");
    assertEquals(result, null);
  }
}