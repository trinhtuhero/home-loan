package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.mapper.LandTransactionMapper;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.api.dto.response.LandTransactionResponse;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LandTransactionEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.repository.LandTransactionRepository;
import vn.com.msb.homeloan.core.service.LandTransactionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LandTransactionServiceImpl implements LandTransactionService {

  private final LandTransactionRepository landTransactionRepository;

  @Override
  public List<LandTransactionResponse> saveOrUpdate(List<LandTransactionRequest> requests,
      String otherIncomeId) {
    log.info("Start saveOrUpdate propertyTransaction: {}", requests);

    List<String> idExisted = new ArrayList<>();
    List<LandTransactionEntity> entities = new ArrayList<>();
    for (LandTransactionRequest request : requests) {
      LandTransactionEntity entity;
      if (request.getUuid() != null) {
        idExisted.add(request.getUuid());
        log.info("Start update propertyTransaction by Id: {}", request.getUuid());
        entity = landTransactionRepository.findByUuidAndOtherIncomeId(request.getUuid(),
                otherIncomeId)
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LAND_TRANSACTION_NOTFOUND));
        LandTransactionMapper.INSTANCE.toUpdateEntity(entity, request);
      } else {
        log.info("Create propertyTransaction");
        entity = LandTransactionMapper.INSTANCE.toEntity(request);
      }
      entity.setOtherIncomeId(otherIncomeId);
      entities.add(entity);
    }
    if (!idExisted.isEmpty()) {
      log.info("Start clear land transaction deleted not in: {}", idExisted);

      landTransactionRepository.deleteByUuidNotInAndOtherIncomeId(idExisted, otherIncomeId);
    } else {
      landTransactionRepository.deleteByOtherIncomeId(otherIncomeId);
    }
    log.info("Start save propertyTransaction: {}", entities);
    List<LandTransactionEntity> resultList = landTransactionRepository.saveAll(entities);
    log.info("Save successful");

    return LandTransactionMapper.INSTANCE.toResponses(resultList);
  }

  @Override
  public List<LandTransactionResponse> getByOtherIncomeId(String otherIncomeId) {
    log.info("Start find list property");
    List<LandTransactionEntity> entities = landTransactionRepository.findAllByOtherIncomeIdOrderByCreatedAt(
        otherIncomeId);
    log.info("find land transaction successful: {}", entities);
    return LandTransactionMapper.INSTANCE.toResponses(entities);
  }

  @Override
  public void deleteById(String uuid) {
    landTransactionRepository.deleteById(uuid);
  }

  @Override
  public void deleteByOtherIncomeId(String uuid) {
    landTransactionRepository.deleteByOtherIncomeId(uuid);
  }
}
