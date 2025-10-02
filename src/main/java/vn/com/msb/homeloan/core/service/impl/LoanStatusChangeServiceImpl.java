package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;
import vn.com.msb.homeloan.core.model.LoanStatusChange;
import vn.com.msb.homeloan.core.model.mapper.LoanStatusChangeMapper;
import vn.com.msb.homeloan.core.repository.LoanStatusChangeRepository;
import vn.com.msb.homeloan.core.service.LoanStatusChangeService;

@Slf4j
@Service
@AllArgsConstructor
public class LoanStatusChangeServiceImpl implements LoanStatusChangeService {

  private final LoanStatusChangeRepository loanStatusChangeRepository;

  @Override
  public LoanStatusChange save(LoanStatusChange loanStatusChange) {
    LoanStatusChangeEntity entity = LoanStatusChangeMapper.INSTANCE.toEntity(loanStatusChange);
    return LoanStatusChangeMapper.INSTANCE.toModel(loanStatusChangeRepository.save(entity));
  }

}
