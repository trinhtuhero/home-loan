package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.NationalityEntity;
import vn.com.msb.homeloan.core.model.Nationality;
import vn.com.msb.homeloan.core.repository.NationalityRepository;
import vn.com.msb.homeloan.core.service.NationalityService;

@ExtendWith(MockitoExtension.class)
class NationalityServiceTest {

  NationalityService nationalityService;

  @Mock
  NationalityRepository nationalityRepository;

  @BeforeEach
  void setUp() {
    this.nationalityService = new NationalityServiceImpl(
        nationalityRepository);
  }

  @Test
  void givenValidInput_ThenAll_shouldReturnSuccess() {
    List<NationalityEntity> nationalityEntities = new ArrayList<>();
    nationalityEntities.add(NationalityEntity.builder().build());
    nationalityEntities.add(NationalityEntity.builder().build());
    nationalityEntities.add(NationalityEntity.builder().build());
    doReturn(nationalityEntities).when(nationalityRepository).findAll();

    List<Nationality> results = nationalityService.findAll();
    assertEquals(results.size(), 3);
  }

}