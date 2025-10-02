package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.AdviseStatusEnum;
import vn.com.msb.homeloan.core.constant.MobioStatusEnum;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;
import vn.com.msb.homeloan.core.entity.ProvinceEntity;
import vn.com.msb.homeloan.core.model.AdviseCustomer;
import vn.com.msb.homeloan.core.model.mapper.CustomerAdviseMapper;
import vn.com.msb.homeloan.core.repository.CustomerAdviseRepository;
import vn.com.msb.homeloan.core.repository.ProvinceRepository;
import vn.com.msb.homeloan.core.service.CustomerAdviseService;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerAdviseServiceImpl implements CustomerAdviseService {

  private final CustomerAdviseRepository customerAdviseRepository;

  private final ProvinceRepository provinceRepository;

  @Override
  public AdviseCustomer save(
      AdviseCustomer adviseCustomer) {
    log.info("Start customerAdviseRegistry: {}", adviseCustomer);
    AdviseCustomerEntity entity = CustomerAdviseMapper.INSTANCE.toEntity(adviseCustomer);
    log.info("Find province by code: {}", adviseCustomer.getProvince());
    ProvinceEntity provinceEntity = provinceRepository.findByCode(
            adviseCustomer.getProvince())
        .orElseThrow(() -> new EntityNotFoundException("Province not found"));
    log.info("Province found: {}", provinceEntity);
    entity.setProvince(provinceEntity.getCode());
    entity.setStatus(AdviseStatusEnum.OPEN);
    entity.setMobioStatus(MobioStatusEnum.WAITING);
    entity = customerAdviseRepository.save(entity);
    log.info("Advise has been created: {}", entity);
    return CustomerAdviseMapper.INSTANCE.toDto(entity);
  }

}
