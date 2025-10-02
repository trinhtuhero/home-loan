package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ExceptionItem;
import vn.com.msb.homeloan.core.model.mapper.ExceptionItemMapper;
import vn.com.msb.homeloan.core.repository.ExceptionItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.ExceptionItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ExceptionItemServiceImpl implements ExceptionItemService {

  private final ExceptionItemRepository exceptionItemRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;

  @Override
  @Transactional
  public ExceptionItem save(ExceptionItem exceptionItem) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            exceptionItem.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);
    // insert
    if (StringUtils.isEmpty(exceptionItem.getUuid())) {
      int count = exceptionItemRepository.countByLoanApplicationId(
          exceptionItem.getLoanApplicationId());
      if (count >= 10) {
        throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "exception item", String.valueOf(10));
      }
    }
    return ExceptionItemMapper.INSTANCE.toModel(
        exceptionItemRepository.save(ExceptionItemMapper.INSTANCE.toEntity(exceptionItem)));
  }

  @Override
  public List<ExceptionItem> getByLoanId(String loanId) {
    return ExceptionItemMapper.INSTANCE.toModels(
        exceptionItemRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanId));
  }

  @Override
  public void deleteByUuid(String uuid) {
    exceptionItemRepository.delete(exceptionItemRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }
}
