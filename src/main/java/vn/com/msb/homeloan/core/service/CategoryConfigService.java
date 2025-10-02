package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.entity.CategoryConfigEntity;

import java.util.List;

public interface CategoryConfigService {
    List<CategoryConfigEntity> getCategoryConfig(String type);
}
