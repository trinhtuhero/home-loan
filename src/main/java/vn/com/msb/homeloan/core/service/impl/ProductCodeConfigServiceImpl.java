package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.entity.ProductCodeConfigEntity;
import vn.com.msb.homeloan.core.model.ProductCodeConfig;
import vn.com.msb.homeloan.core.model.mapper.ProductCodeConfigMapper;
import vn.com.msb.homeloan.core.repository.ProductCodeConfigRepository;
import vn.com.msb.homeloan.core.service.ProductCodeConfigService;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCodeConfigServiceImpl implements ProductCodeConfigService {

  private final ProductCodeConfigRepository productCodeConfigRepository;

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<ProductCodeConfig> getProductCodeConfigs(HashMap<String, Object> hm) {
    List<ProductCodeConfigEntity> productCodeConfigEntities = productCodeConfigRepository.findAll();
    List<ProductCodeConfig> list = new ArrayList<>();
    for (ProductCodeConfigEntity productCodeConfigEntity : productCodeConfigEntities) {
      String exp = productCodeConfigEntity.getExpression();
      if (HomeLoanUtil.evalExpression(hm, exp)) {
        list.add(ProductCodeConfigMapper.INSTANCE.toModel(productCodeConfigEntity));
      }
    }
    return list;
  }

  @Override
  public HashMap<String, Object> getMapKeys(String loanId, String loanItemId) {
    Connection connection = null;
    LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
    try {
      connection = jdbcTemplate.getDataSource().getConnection();
      CallableStatement callableStatement = null;

      callableStatement = connection.prepareCall("{call get_product_code_config(?, ?)}");

      callableStatement.setString(1, loanId);
      callableStatement.setString(2, loanItemId);

      ResultSet rs = callableStatement.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      // The column count starts from 1
      if (rs.next()) {
        for (int i = 1; i <= columnCount; i++) {
          String key = rsmd.getColumnName(i);
          Object value = rs.getObject(i);
          hashMap.put(key, value);
        }
      }
    } catch (Exception ignored) {
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          log.error("getMapKeys error: " + ex.getMessage());
        }
      }
    }
    return hashMap;
  }
}
