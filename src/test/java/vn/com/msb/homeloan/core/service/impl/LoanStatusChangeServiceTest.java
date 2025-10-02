package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;
import vn.com.msb.homeloan.core.model.LoanStatusChange;
import vn.com.msb.homeloan.core.model.mapper.LoanStatusChangeMapper;
import vn.com.msb.homeloan.core.repository.LoanStatusChangeRepository;
import vn.com.msb.homeloan.core.service.LoanStatusChangeService;

@ExtendWith(MockitoExtension.class)
class LoanStatusChangeServiceTest {

  @Mock
  LoanStatusChangeRepository loanStatusChangeRepository;

  LoanStatusChangeService loanUploadFileService;

  @BeforeEach
  void setUp() {
    this.loanUploadFileService = new LoanStatusChangeServiceImpl(
        loanStatusChangeRepository
    );
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() {
    LoanStatusChangeEntity entity = LoanStatusChangeEntity.builder()
        .uuid("uuid")
        .build();
    doReturn(entity).when(loanStatusChangeRepository).save(any());

    LoanStatusChange result = loanUploadFileService.save(
        LoanStatusChangeMapper.INSTANCE.toModel(entity));

    assertEquals(result.getUuid(), entity.getUuid());
  }

}