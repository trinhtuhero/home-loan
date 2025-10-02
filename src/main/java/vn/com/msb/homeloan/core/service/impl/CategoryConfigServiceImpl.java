package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.Status;
import vn.com.msb.homeloan.core.entity.CategoryConfigEntity;
import vn.com.msb.homeloan.core.repository.CategoryConfigRepository;
import vn.com.msb.homeloan.core.service.CategoryConfigService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryConfigServiceImpl implements CategoryConfigService {
    private final CategoryConfigRepository categoryConfigRepository;

    @Override
    public List<CategoryConfigEntity> getCategoryConfig(String type) {
        List<CategoryConfigEntity> entity = categoryConfigRepository.findByTypeAndStatus(type, Status.ACTIVE.getValue());
        return entity;
    }
}
