package vn.com.msb.homeloan.core.repository.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.model.CmsExportReportParam;
import vn.com.msb.homeloan.core.model.CmsFeedbackReport;
import vn.com.msb.homeloan.core.model.CmsFeedbackReportSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplication;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcel;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcelParam;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearch;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearchParam;
import vn.com.msb.homeloan.core.model.CmsReportSearchParam;
import vn.com.msb.homeloan.core.model.CmsTATReport;
import vn.com.msb.homeloan.core.model.CmsTATReportSearch;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.PagingResponse;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepositoryCustom;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;
import vn.com.msb.homeloan.core.util.SHA256EncryptUtil;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j
public class LoanApplicationRepositoryImpl implements LoanApplicationRepositoryCustom {

  @PersistenceContext(unitName = "entityManagerFactory")
  private EntityManager entityManager;

  private final EnvironmentProperties environmentProperties;

  private final HomeLoanUtil homeLoanUtil;

  @Override
  public CmsLoanApplicationSearch cmsLoanApplicationSearch(CmsLoanApplicationSearchParam param,
      List<String> picRms) {
    log.info(picRms.toString());
    StringBuilder sql = new StringBuilder();
    List<CmsLoanApplication> contents = new ArrayList<>();
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" left join loan_application_item lai on lai.loan_application_id = la.uuid");
    // sql.append(" left join overdraft o on o.loan_application_id = la.uuid");
    sql.append(" left join cms_users cu ON la.pic_rm = cu.empl_id");
    sql.append(" left join loan_advise_customer lac ON lac.loan_application_id = la.uuid");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getKeyWord())) {
      mapParam.put("key", param.getKeyWord());
      sql.append(
          " and (la.id_no = :key or la.full_name LIKE CONCAT('%', :key,'%') or la.loan_code = :key or la.phone = :key)");
    }

    if (!CollectionUtils.isEmpty(picRms)) {
      if (picRms.size() == 1) {
        mapParam.put("userLogin", picRms.get(0));
        sql.append(" and la.pic_rm = :userLogin ");
      } else {
        mapParam.put("picRms", picRms);
        mapParam.put("userLogin", picRms.get(0));
        mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
        sql.append(
            " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
      }
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and la.status IN (:status) ");
    }

    List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    mapParam.put("statusNotShow", lstStatusNotShow);
    sql.append(" and la.status NOT IN :statusNotShow ");

    if (param.getAmountFrom() != null && param.getAmountFrom() > 0) {
      mapParam.put("loanAmountFrom", param.getAmountFrom());
      sql.append(" and lai.loan_amount >= :loanAmountFrom ");
    }

    if (param.getAmountTo() != null && param.getAmountTo() > 0) {
      mapParam.put("loanAmountTo", param.getAmountTo());
      sql.append("  and lai.loan_amount <= :loanAmountTo ");
    }

    if (param.getReceptionDateFrom() != null) {
      mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getReceptionDateFrom()));
      sql.append(" and la.receive_date >= :receptionDateFrom ");
    }

    if (param.getReceptionDateTo() != null) {
      mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getReceptionDateTo()));
      sql.append(" and la.receive_date < :receptionDateTo ");
    }

    if (!CollectionUtils.isEmpty(param.getChannelCodes())) {
      mapParam.put("receiveChannel", param.getChannelCodes());
      sql.append(" and la.receive_channel IN (:receiveChannel) ");
    }

    if (!StringUtils.isEmpty(param.getLoanProduct())) {
      mapParam.put("loanProduct", param.getLoanProduct());
      sql.append(" and lai.loan_purpose = :loanProduct ");
    }

    sql.append(" order by la.created_at DESC ");

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
      sqlSelect.append("SELECT la.uuid as uuid, ");
      sqlSelect.append("la.loan_code as loanCode, ");
      sqlSelect.append("la.full_name as fullName, ");
      sqlSelect.append("la.phone as phone, ");
      sqlSelect.append("la.id_no as idNo, ");
      sqlSelect.append(
          "if(lai.loan_purpose = 'THAU_CHI',(select coalesce(form_of_credit, 'THAU_CHI') from overdraft o where loan_application_id = la.uuid limit 1),lai.loan_purpose) as loanPurpose, ");
      sqlSelect.append("lai.loan_amount as loanAmount, ");
      sqlSelect.append("cu.email as picRmEmail, ");
      sqlSelect.append("cu.branch_code as branchCode, ");
      sqlSelect.append("(select name from organization where branchCode = code) as branchName, ");
      sqlSelect.append("la.status as status, ");
      sqlSelect.append(
          "if(la.receive_channel = 'CMS', la.created_at, (select created_at from loan_status_change where loan_application_id = la.uuid and status_to IN ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION', 'ACCEPT_LOAN_REQUEST', 'ACCEPT_LOAN_APPLICATION') and la.receive_channel = 'LDP' order by created_at asc limit 1)) as submittedDate, ");
      sqlSelect.append("la.receive_date as receiveDate, ");
      sqlSelect.append(
          "(select note from loan_status_change where loan_application_id = la.uuid and status_to = 'CLOSED' LIMIT 1) as note, ");
      sqlSelect.append("null as link, ");
      sqlSelect.append("lac.advise_date as adviseDate, ");
      sqlSelect.append("lac.status as adviseStatus, ");
      sqlSelect.append("lac.advise_time_frame as adviseTimeFrame");
      sqlSelect.append(sql);

      log.info("sql final: {}", sqlSelect);
      Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsSearchResult");
      query.setFirstResult(param.getPaging().getPage() * param.getPaging().getSize());
      query.setMaxResults(param.getPaging().getSize());
      for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
        query.setParameter(entry.getKey(), entry.getValue());
      }

      contents = query.getResultList();

      if (homeLoanUtil.containRole(environmentProperties.getCj5Admin()) && !CollectionUtils.isEmpty(
          contents)) {
        contents.forEach(cmsLoanApplication -> {
              // link mobio
              String hash = SHA256EncryptUtil.createLinkMobio(environmentProperties.getSha256Secret(),
                  cmsLoanApplication.getUuid());
              cmsLoanApplication.setLink(
                  String.format("%s%s/%s?%s", environmentProperties.getDealApplicationLink(),
                      cmsLoanApplication.getUuid(), hash, "type=READONLY"));
            }
        );
      }
    }

    int totalPage = totalRow.intValue() / param.getPaging().getSize() + (
        (totalRow.intValue() % param.getPaging().getSize() == 0) ? 0 : 1);

    PagingResponse paging = PagingResponse.builder()
        .pageNumber(param.getPaging().getPage())
        .pageSize(param.getPaging().getSize())
        .totalRecord(totalRow.intValue())
        .totalPage(totalPage)
        .build();

    return CmsLoanApplicationSearch.builder()
        .contents(contents)
        .paging(paging)
        .build();
  }

  @Override
  public CmsLoanApplicationExportExcel cmsLoanApplicationExportExcel(
      CmsLoanApplicationExportExcelParam param, List<String> picRms) {
    StringBuilder sql = new StringBuilder();
    Map<String, Object> mapParam = new HashMap<>();
    List<CmsLoanApplication> contents;
    if (param.getLoanIds() != null && !param.getLoanIds().isEmpty()) {
      sql.append(" from loan_applications la ");
      sql.append(" LEFT JOIN loan_application_item lai on lai.loan_application_id = la.uuid");
      sql.append(" LEFT JOIN cms_users cu ON la.pic_rm = cu.empl_id ");
      mapParam.put("loanIds", param.getLoanIds());
      sql.append(" where la.uuid IN (:loanIds)");
    } else {
      sql.append(" from loan_applications la ");
      sql.append(" LEFT JOIN loan_application_item lai on lai.loan_application_id = la.uuid");
      sql.append(" LEFT JOIN cms_users cu ON la.pic_rm = cu.empl_id ");
      sql.append(" where 1 = 1 ");

      if (!StringUtils.isEmpty(param.getKeyWord())) {
        mapParam.put("key", param.getKeyWord());
        sql.append(
            " and (la.id_no = :key or la.full_name LIKE CONCAT('%', :key,'%') or la.loan_code = :key or la.phone = :key) ");
      }

      if (!CollectionUtils.isEmpty(picRms)) {
        if (picRms.size() == 1) {
          mapParam.put("userLogin", picRms.get(0));
          sql.append(" and la.pic_rm = :userLogin ");
        } else {
          mapParam.put("picRms", picRms);
          mapParam.put("userLogin", picRms.get(0));
          mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
          sql.append(
              " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
        }
      }

      if (!StringUtils.isEmpty(param.getBranchCode())) {
        mapParam.put("branchCode", param.getBranchCode());
        sql.append(" and cu.branch_code = :branchCode ");
      }

      if (!CollectionUtils.isEmpty(param.getStatus())) {
        mapParam.put("status", param.getStatus());
        sql.append(" and la.status IN :status ");
      }

      List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
          LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
          LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
      mapParam.put("statusNotShow", lstStatusNotShow);
      sql.append(" and la.status NOT IN :statusNotShow ");

      if (param.getAmountFrom() != null && param.getAmountFrom() > 0) {
        mapParam.put("loanAmountFrom", param.getAmountFrom());
        sql.append(" and lai.loan_amount >= :loanAmountFrom ");
      }

      if (param.getAmountTo() != null && param.getAmountTo() > 0) {
        mapParam.put("loanAmountTo", param.getAmountTo());
        sql.append("  and lai.loan_amount <= :loanAmountTo ");
      }

      if (param.getReceptionDateFrom() != null) {
        mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getReceptionDateFrom()));
        sql.append(" and la.receive_date >= :receptionDateFrom ");
      }

      if (param.getReceptionDateTo() != null) {
        mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getReceptionDateTo()));
        sql.append("  and la.receive_date < :receptionDateTo ");
      }

      if (!CollectionUtils.isEmpty(param.getChannelCodes())) {
        mapParam.put("receiveChannel", param.getChannelCodes());
        sql.append(" and la.receive_channel IN (:receiveChannel) ");
      }

      if (!StringUtils.isEmpty(param.getLoanProduct())) {
        mapParam.put("loanProduct", param.getLoanProduct());
        sql.append(" and lai.loan_purpose = :loanProduct ");
      }
    }
    sql.append(" order by la.created_at DESC ");

    StringBuilder sqlSelect = new StringBuilder();
    sqlSelect.append(" SELECT la.uuid as uuid, ");
    sqlSelect.append("        la.loan_code as loanCode, ");
    sqlSelect.append("        la.full_name as fullName, ");
    sqlSelect.append("        la.phone as phone, ");
    sqlSelect.append("        la.id_no as idNo, ");
    sqlSelect.append("        lai.loan_purpose as loanPurpose, ");
    sqlSelect.append("        lai.loan_amount as loanAmount, ");
    sqlSelect.append("        cu.email as picRmEmail, ");
    sqlSelect.append("        cu.branch_code as branchCode, ");
    sqlSelect.append(
        "        (select name from organization where branchCode = code) as branchName, ");
    sqlSelect.append("        la.status as status, ");
    sqlSelect.append(
        "        if(la.receive_channel = 'CMS', la.created_at, (select created_at from loan_status_change where loan_application_id = la.uuid and status_to IN ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION', 'ACCEPT_LOAN_REQUEST', 'ACCEPT_LOAN_APPLICATION') and la.receive_channel = 'LDP' order by created_at asc limit 1)) as submittedDate, ");
    sqlSelect.append("        la.receive_date as receiveDate ");
    sqlSelect.append(sql);
    Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsExport");
    for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    contents = query.getResultList();

    return CmsLoanApplicationExportExcel.builder()
        .contents(contents)
        .build();
  }

  @Override
  public List<LoanApplication> getLoansByProfileId(String loanCode, List<String> status,
      Long loanAmountFrom, Long loanAmountTo, Instant from, Instant to, String userId) {
    StringBuilder sql = new StringBuilder();
    List<LoanApplication> lstResult;
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" where 1 = 1 ");

    mapParam.put("profileId", userId);
    sql.append(" and la.profile_id = :profileId ");

    if (!StringUtils.isEmpty(loanCode)) {
      mapParam.put("loanCode", loanCode);
      sql.append(" and la.loan_code LIKE CONCAT('%', :loanCode,'%') ");
    }

    if (!CollectionUtils.isEmpty(status)) {
      mapParam.put("status", status);
      sql.append(" and la.status IN :status ");
    }

    if (loanAmountFrom != null) {
      mapParam.put("loanAmountFrom", loanAmountFrom);
      sql.append(" and lai.loan_amount >= :loanAmountFrom ");
    }

    if (loanAmountFrom != null) {
      mapParam.put("loanAmountTo", loanAmountTo);
      sql.append(" and lai.loan_amount <= :loanAmountTo ");
    }

    if (from != null) {
      mapParam.put("from", from);
      sql.append(" and la.created_at >= :from ");
    }

    if (to != null) {
      mapParam.put("to", to);
      sql.append("  and la.created_at <= :to ");
    }
    sql.append(" order by la.created_at DESC ");

    StringBuilder sqlSelect = new StringBuilder();
    sqlSelect.append(" SELECT la.uuid as uuid, ");
    sqlSelect.append("        la.loan_code as loanCode, ");
    sqlSelect.append("        la.status as status, ");
    sqlSelect.append("        la.receive_channel as receiveChannel, ");
    sqlSelect.append("        la.pic_rm as picRm, ");
    sqlSelect.append("        la.ref_code as refCode ");
    sqlSelect.append(sql);
    Query query = entityManager.createNativeQuery(sqlSelect.toString(), "GetListLoanInfo");
    for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    lstResult = query.getResultList();

    return lstResult;
  }

  @Override
  public CmsTATReportSearch cmsTATReportSearch(CmsReportSearchParam param, List<String> picRms) {
    StringBuilder sql = new StringBuilder();
    List<CmsTATReport> contents = new ArrayList<>();
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" left join loan_application_item lai on lai.loan_application_id = la.uuid");
    sql.append(" left join cms_users cu ON la.pic_rm = cu.empl_id ");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getLoanCode())) {
      mapParam.put("loanCode", param.getLoanCode());
      sql.append(" and la.loan_code like CONCAT('%', :loanCode,'%') ");
    }

    if (!CollectionUtils.isEmpty(picRms)) {
      if (picRms.size() == 1) {
        mapParam.put("userLogin", picRms.get(0));
        sql.append(" and la.pic_rm = :userLogin ");
      } else {
        mapParam.put("picRms", picRms);
        mapParam.put("userLogin", picRms.get(0));
        mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
        sql.append(
            " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
      }
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and la.status IN (:status) ");
    }

    List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    mapParam.put("statusNotShow", lstStatusNotShow);
    sql.append(" and la.status NOT IN :statusNotShow ");

    if (param.getDateFrom() != null) {
      mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getDateFrom()));
      sql.append(" and la.receive_date >= :receptionDateFrom ");
    }

    if (param.getDateTo() != null) {
      mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getDateTo()));
      sql.append(" and la.receive_date < :receptionDateTo ");
    }

    if (!StringUtils.isEmpty(param.getLoanPurpose())) {
      mapParam.put("loanPurpose", param.getLoanPurpose());
      sql.append(" and lai.loan_purpose = :loanPurpose ");
    }

    sql.append(" order by la.created_at DESC ");

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
      sqlSelect.append("SELECT la.uuid as uuid, ");
      sqlSelect.append("la.loan_code as loanCode, ");
      sqlSelect.append("cu.branch_code as branchCode, ");
      sqlSelect.append("(select name from organization where branchCode = code) as branchName, ");
      sqlSelect.append("la.full_name as fullName, ");
      sqlSelect.append("la.id_no as idNo, ");
      sqlSelect.append(
          "if(lai.loan_purpose = 'THAU_CHI',(select coalesce(form_of_credit, 'THAU_CHI') from overdraft o where loan_application_id = la.uuid limit 1),lai.loan_purpose) as loanPurpose, ");
      sqlSelect.append("la.phone as phone, ");
      sqlSelect.append("lai.loan_amount as loanAmount, ");
      sqlSelect.append("cu.full_name as picRmFullName, ");
      sqlSelect.append("cu.email as picRmEmail, ");
      sqlSelect.append(
          "if(la.receive_channel = 'CMS', null, (select created_at from loan_status_change where loan_application_id = la.uuid and status_to IN ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION', 'ACCEPT_LOAN_REQUEST', 'ACCEPT_LOAN_APPLICATION') and la.receive_channel = 'LDP' order by created_at asc limit 1)) as submittedDate, ");
      sqlSelect.append("la.receive_date as receiveDate, ");
      sqlSelect.append(
          "(select created_at from loan_status_change where loan_application_id = la.uuid and status_to = 'RM_COMPLETED' limit 1) as finishDate, ");
      sqlSelect.append("la.status as status ");
      sqlSelect.append(sql);
      Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsTATReport");
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

    return CmsTATReportSearch.builder()
        .contents(contents)
        .paging(paging)
        .build();
  }

  @Override
  public CmsFeedbackReportSearch cmsFeedBackReportSearch(CmsReportSearchParam param,
      List<String> picRms) {
    StringBuilder sql = new StringBuilder();
    List<CmsFeedbackReport> contents = new ArrayList<>();
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" left join loan_application_item lai on lai.loan_application_id = la.uuid");
    sql.append(" left join cms_users cu ON la.pic_rm = cu.empl_id ");
    sql.append(" left join feedback fb ON fb.loan_application_id = la.uuid ");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getLoanCode())) {
      mapParam.put("loanCode", param.getLoanCode());
      sql.append(" and la.loan_code like CONCAT('%', :loanCode,'%') ");
    }

    if (!CollectionUtils.isEmpty(picRms)) {
      if (picRms.size() == 1) {
        mapParam.put("userLogin", picRms.get(0));
        sql.append(" and la.pic_rm = :userLogin ");
      } else {
        mapParam.put("picRms", picRms);
        mapParam.put("userLogin", picRms.get(0));
        mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
        sql.append(
            " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
      }
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and la.status IN (:status) ");
    }

    List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    mapParam.put("statusNotShow", lstStatusNotShow);
    sql.append(" and la.status NOT IN :statusNotShow ");

    if (param.getDateFrom() != null) {
      mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getDateFrom()));
      sql.append(" and la.receive_date >= :receptionDateFrom ");
    }

    if (param.getDateTo() != null) {
      mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getDateTo()));
      sql.append(" and la.receive_date < :receptionDateTo ");
    }

    if (!StringUtils.isEmpty(param.getLoanPurpose())) {
      mapParam.put("loanPurpose", param.getLoanPurpose());
      sql.append(" and lai.loan_purpose = :loanPurpose ");
    }

    sql.append(" order by la.created_at DESC ");

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
      sqlSelect.append("SELECT la.uuid as uuid, ");
      sqlSelect.append("la.loan_code as loanCode, ");
      sqlSelect.append("cu.branch_code as branchCode, ");
      sqlSelect.append("(select name from organization where branchCode = code) as branchName, ");
      sqlSelect.append("la.full_name as fullName, ");
      sqlSelect.append("la.id_no as idNo, ");
      sqlSelect.append(
          "if(lai.loan_purpose = 'THAU_CHI',(select coalesce(form_of_credit, 'THAU_CHI') from overdraft o where loan_application_id = la.uuid limit 1),lai.loan_purpose) as loanPurpose, ");
      sqlSelect.append("la.phone as phone, ");
      sqlSelect.append("lai.loan_amount as loanAmount, ");
      sqlSelect.append("cu.full_name as picRmFullName, ");
      sqlSelect.append("cu.email as picRmEmail, ");
      sqlSelect.append("fb.rate as rate, ");
      sqlSelect.append("fb.additional_comment as additionalComment, ");
      sqlSelect.append("la.status as status ");
      sqlSelect.append(sql);
      Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsFeedbackReport");
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

    return CmsFeedbackReportSearch.builder()
        .contents(contents)
        .paging(paging)
        .build();
  }

  @Override
  public List<CmsTATReport> cmsTATReportExport(CmsExportReportParam param, List<String> picRms) {
    StringBuilder sql = new StringBuilder();
    List<CmsTATReport> result;
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" left join loan_application_item lai on lai.loan_application_id = la.uuid");
    sql.append(" left join cms_users cu ON la.pic_rm = cu.empl_id ");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getLoanCode())) {
      mapParam.put("loanCode", param.getLoanCode());
      sql.append(" and la.loan_code like CONCAT('%', :loanCode,'%') ");
    }

    if (!CollectionUtils.isEmpty(picRms)) {
      if (picRms.size() == 1) {
        mapParam.put("userLogin", picRms.get(0));
        sql.append(" and la.pic_rm = :userLogin ");
      } else {
        mapParam.put("picRms", picRms);
        mapParam.put("userLogin", picRms.get(0));
        mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
        sql.append(
            " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
      }
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and la.status IN (:status) ");
    }

    List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    mapParam.put("statusNotShow", lstStatusNotShow);
    sql.append(" and la.status NOT IN :statusNotShow ");

    if (param.getDateFrom() != null) {
      mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getDateFrom()));
      sql.append(" and la.receive_date >= :receptionDateFrom ");
    }

    if (param.getDateTo() != null) {
      mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getDateTo()));
      sql.append(" and la.receive_date < :receptionDateTo ");
    }

    if (!StringUtils.isEmpty(param.getLoanPurpose())) {
      mapParam.put("loanPurpose", param.getLoanPurpose());
      sql.append(" and lai.loan_purpose = :loanPurpose ");
    }

    sql.append(" order by la.created_at DESC ");

    StringBuilder sqlSelect = new StringBuilder();
    sqlSelect.append("SELECT la.loan_code as loanCode, ");
    sqlSelect.append("cu.branch_code as branchCode, ");
    sqlSelect.append("(select name from organization where branchCode = code) as branchName, ");
    sqlSelect.append("la.full_name as fullName, ");
    sqlSelect.append("la.id_no as idNo, ");
    sqlSelect.append(
        "if(lai.loan_purpose = 'THAU_CHI',(select coalesce(form_of_credit, 'THAU_CHI') from overdraft o where loan_application_id = la.uuid limit 1),lai.loan_purpose) as loanPurpose, ");
    sqlSelect.append("lai.loan_amount as loanAmount, ");
    sqlSelect.append("cu.full_name as picRmFullName, ");
    sqlSelect.append("cu.email as picRmEmail, ");
    sqlSelect.append(
        "if(la.receive_channel = 'CMS', null, (select created_at from loan_status_change where loan_application_id = la.uuid and status_to IN ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION', 'ACCEPT_LOAN_REQUEST', 'ACCEPT_LOAN_APPLICATION') and la.receive_channel = 'LDP' order by created_at asc limit 1)) as submittedDate, ");
    sqlSelect.append("la.receive_date as receiveDate, ");
    sqlSelect.append(
        "(select created_at from loan_status_change where loan_application_id = la.uuid and status_to = 'RM_COMPLETED' limit 1) as finishDate, ");
    sqlSelect.append("la.status as status ");
    sqlSelect.append(sql);
    Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsTATReportExport");
    for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    result = query.getResultList();

    return result;
  }

  @Override
  public List<CmsFeedbackReport> cmsFeedBackReportExport(CmsExportReportParam param,
      List<String> picRms) {
    StringBuilder sql = new StringBuilder();
    List<CmsFeedbackReport> result = new ArrayList<>();
    Map<String, Object> mapParam = new HashMap<>();
    sql.append(" from loan_applications la ");
    sql.append(" left join loan_application_item lai on lai.loan_application_id = la.uuid");
    sql.append(" left join cms_users cu ON la.pic_rm = cu.empl_id ");
    sql.append(" left join feedback fb ON fb.loan_application_id = la.uuid ");
    sql.append(" where 1 = 1 ");

    if (!StringUtils.isEmpty(param.getLoanCode())) {
      mapParam.put("loanCode", param.getLoanCode());
      sql.append(" and la.loan_code like CONCAT('%', :loanCode,'%') ");
    }

    if (!CollectionUtils.isEmpty(picRms)) {
      if (picRms.size() == 1) {
        mapParam.put("userLogin", picRms.get(0));
        sql.append(" and la.pic_rm = :userLogin ");
      } else {
        mapParam.put("picRms", picRms);
        mapParam.put("userLogin", picRms.get(0));
        mapParam.put("completedStatus", LoanInfoStatusEnum.RM_COMPLETED.getCode());
        sql.append(
            " and ((la.pic_rm IN (:picRms) and la.status = :completedStatus) or la.pic_rm = :userLogin) ");
      }
    }

    if (!StringUtils.isEmpty(param.getBranchCode())) {
      mapParam.put("branchCode", param.getBranchCode());
      sql.append(" and cu.branch_code = :branchCode ");
    }

    if (!CollectionUtils.isEmpty(param.getStatus())) {
      mapParam.put("status", param.getStatus());
      sql.append(" and la.status IN (:status) ");
    }

    List<String> lstStatusNotShow = Arrays.asList(LoanInfoStatusEnum.DRAFT.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_REQUEST.getCode(),
        LoanInfoStatusEnum.SUBMIT_LOAN_APPLICATION.getCode());
    mapParam.put("statusNotShow", lstStatusNotShow);
    sql.append(" and la.status NOT IN :statusNotShow ");

    if (param.getDateFrom() != null) {
      mapParam.put("receptionDateFrom", DateUtils.toUTCDate(param.getDateFrom()));
      sql.append(" and la.receive_date >= :receptionDateFrom ");
    }

    if (param.getDateTo() != null) {
      mapParam.put("receptionDateTo", DateUtils.toUTCEndOfDate(param.getDateTo()));
      sql.append(" and la.receive_date < :receptionDateTo ");
    }

    if (!StringUtils.isEmpty(param.getLoanPurpose())) {
      mapParam.put("loanPurpose", param.getLoanPurpose());
      sql.append(" and lai.loan_purpose = :loanPurpose ");
    }

    sql.append(" order by la.created_at DESC ");

    StringBuilder sqlSelect = new StringBuilder();
    sqlSelect.append("SELECT la.uuid as uuid, ");
    sqlSelect.append("la.loan_code as loanCode, ");
    sqlSelect.append("cu.branch_code as branchCode, ");
    sqlSelect.append("(select name from organization where branchCode = code) as branchName, ");
    sqlSelect.append("la.full_name as fullName, ");
    sqlSelect.append("la.id_no as idNo, ");
    sqlSelect.append(
        "if(lai.loan_purpose = 'THAU_CHI',(select coalesce(form_of_credit, 'THAU_CHI') from overdraft o where loan_application_id = la.uuid limit 1),lai.loan_purpose) as loanPurpose, ");
    sqlSelect.append("la.phone as phone, ");
    sqlSelect.append("lai.loan_amount as loanAmount, ");
    sqlSelect.append("cu.full_name as picRmFullName, ");
    sqlSelect.append("cu.email as picRmEmail, ");
    sqlSelect.append("fb.rate as rate, ");
    sqlSelect.append("fb.additional_comment as additionalComment, ");
    sqlSelect.append("la.status as status ");
    sqlSelect.append(sql);
    Query query = entityManager.createNativeQuery(sqlSelect.toString(), "CmsFeedbackReport");
    for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    result = query.getResultList();

    return result;
  }
}
