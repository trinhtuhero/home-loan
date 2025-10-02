package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ContactStatusEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanAdviseCustomerEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;
import vn.com.msb.homeloan.core.model.mapper.LoanAdviseCustomerMapper;
import vn.com.msb.homeloan.core.repository.LoanAdviseCustomerRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.LoanAdviseCustomerService;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanAdviseCustomerServiceImpl implements LoanAdviseCustomerService {

  private final LoanAdviseCustomerRepository loanAdviseCustomerRepository;

  private final LoanApplicationRepository loanApplicationRepository;

  @Override
  public LoanAdviseCustomer save(LoanAdviseCustomer loanAdviseCustomer) {

    loanApplicationRepository.findByUuid(loanAdviseCustomer.getLoanApplicationId())
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loan_applications:uuid"));

    LoanAdviseCustomerEntity entity = loanAdviseCustomerRepository.findByLoanApplicationId(
        loanAdviseCustomer.getLoanApplicationId());

    if (entity == null) {
      entity = new LoanAdviseCustomerEntity();
    }

    entity.setLoanApplicationId(loanAdviseCustomer.getLoanApplicationId());
    entity.setAdviseDate(loanAdviseCustomer.getAdviseDate());
    entity.setAdviseTimeFrame(loanAdviseCustomer.getAdviseTimeFrame());
    entity.setContent(loanAdviseCustomer.getContent());
    entity.setStatus(ContactStatusEnum.NOT_CONTACTED_YET);

    return LoanAdviseCustomerMapper.INSTANCE.toModel(loanAdviseCustomerRepository.save(entity));
  }

  @Override
  public LoanAdviseCustomer get(String loanId) {
    log.info("Start get advise for loanId: {}", loanId);
    LoanAdviseCustomerEntity entity = loanAdviseCustomerRepository.findByLoanApplicationId(loanId);
    if (entity != null) {
      log.info("Advise found: {}", entity);
      return LoanAdviseCustomerMapper.INSTANCE.toModel(entity);
    }

    log.info("advise not found by id: {}", loanId);
    return null;
  }

  @Override
  public LoanAdviseCustomer changeStatus(String loanId, String status) {
    LoanAdviseCustomerEntity entity = loanAdviseCustomerRepository.findByLoanApplicationId(loanId);
    if (entity != null) {
      if (!ContactStatusEnum.CONTACTED.name().equals(status)) {
        entity.setStatus(ContactStatusEnum.CONTACTED);
        log.info("Status updated: {}", entity);
      }
      return LoanAdviseCustomerMapper.INSTANCE.toModel(loanAdviseCustomerRepository.save(entity));
    }
    throw new EntityNotFoundException("LoanAdviseCustomerEntity not found with loanId: " + loanId);
  }
}
