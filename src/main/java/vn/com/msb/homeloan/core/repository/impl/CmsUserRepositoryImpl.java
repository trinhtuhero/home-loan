package vn.com.msb.homeloan.core.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.model.CmsUserInfo;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;
import vn.com.msb.homeloan.core.model.PagingResponse;
import vn.com.msb.homeloan.core.repository.CmsUserRepositoryCustom;
import vn.com.msb.homeloan.core.util.StringUtils;

@Repository
public class CmsUserRepositoryImpl implements CmsUserRepositoryCustom {

  @PersistenceContext(unitName = "entityManagerFactory")
  private EntityManager entityManager;

  @Override
  public CmsUserSearch cmsUserSearch(CmsUserSearchParam param) {
    StringBuilder sql = new StringBuilder();
    List<CmsUserInfo> contents = new ArrayList<>();
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from cms_users cu " +
        " LEFT JOIN organization o ON cu.branch_code = o.code ");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getKeyWord())) {
      mapParam.put("key", param.getKeyWord());
      sql.append(
          " and (cu.full_name LIKE CONCAT('%', :key,'%') or cu.phone LIKE CONCAT('%', :key,'%') or cu.email LIKE CONCAT('%', :key,'%')) ");
    }

    if (!StringUtils.isEmpty(param.getFullName())) {
      mapParam.put("fullName", param.getFullName());
      sql.append(" and cu.full_name LIKE CONCAT('%', :fullName,'%') ");
    }

    if (!StringUtils.isEmpty(param.getPhone())) {
      mapParam.put("phone", param.getPhone());
      sql.append(" and cu.phone LIKE CONCAT('%', :phone,'%') ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and cu.status IN :status ");
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getRoles())) {
      for (int i = 0; i < param.getRoles().size(); i++) {
        if (i == 0) {
          mapParam.put("role" + i, param.getRoles().get(i));
          sql.append(" and (cu.role LIKE CONCAT('%', :role" + i + ",'%')");
        } else {
          mapParam.put("role" + i, param.getRoles().get(i));
          sql.append(" or cu.role LIKE CONCAT('%', :role" + i + ",'%') ");
        }
      }
      sql.append(")");
    }

    StringBuilder sqlCount = new StringBuilder();
    sqlCount.append("SELECT COUNT(1) ");
    sqlCount.append(sql);

    Query countQuery = entityManager.createNativeQuery(sqlCount.toString());
    for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
      countQuery.setParameter(entry.getKey(), entry.getValue());
    }
    Number totalRow = (Number) countQuery.getSingleResult();
    if (totalRow.intValue() > 0) {
      StringBuilder sqlSelect = new StringBuilder();
      sqlSelect.append(" SELECT cu.full_name as fullName, ");
      sqlSelect.append("        cu.email as email, ");
      sqlSelect.append("        cu.phone as phone, ");
      sqlSelect.append("        o.code as branchCode, ");
      sqlSelect.append("        o.name as branchName, ");
      sqlSelect.append("        cu.leader_email as leader, ");
      sqlSelect.append("        cu.updated_at as updatedAt, ");
      sqlSelect.append("        cu.created_by as createdBy, ");
      sqlSelect.append("        cu.status as status, ");
      sqlSelect.append("        cu.role as role ");
      sqlSelect.append(sql);
      Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsUserSearchResult");
      query.setFirstResult(param.getPaging().getPage() * param.getPaging().getSize());
      query.setMaxResults(param.getPaging().getSize());
      for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
        query.setParameter(entry.getKey(), entry.getValue());
      }
      contents = query.getResultList();
    }

    int totalPage = totalRow.intValue() / param.getPaging().getSize() + (
        (totalRow.intValue() % param.getPaging().getSize() == 0) ? 0 : 1);

    PagingResponse paging = PagingResponse.builder()
        .pageNumber(param.getPaging().getPage())
        .pageSize(param.getPaging().getSize())
        .totalRecord(totalRow.intValue())
        .totalPage(totalPage)
        .build();

    return CmsUserSearch.builder()
        .contents(contents)
        .paging(paging)
        .build();
  }
}
