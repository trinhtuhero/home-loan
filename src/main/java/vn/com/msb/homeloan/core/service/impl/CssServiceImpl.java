package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.client.CssClient;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CssEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Css;
import vn.com.msb.homeloan.core.model.CssParam;
import vn.com.msb.homeloan.core.model.mapper.CssMapper;
import vn.com.msb.homeloan.core.model.request.CssCommon;
import vn.com.msb.homeloan.core.model.request.CssInfo;
import vn.com.msb.homeloan.core.model.request.CssRequest;
import vn.com.msb.homeloan.core.model.response.css.CssResponse;
import vn.com.msb.homeloan.core.repository.CssRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CssService;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.feign.CssConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class CssServiceImpl implements CssService {

  private final CssClient cssClient;
  private final CssConfig cssConfig;
  private final CssRepository cssRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final EnvironmentProperties envProperties;

  @Override
  @Transactional
  public Css getScore(CssParam cssParam, String requestDate)
      throws ApplicationException, ParseException {
    LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findById(
            cssParam.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    CssCommon cssCommon = new CssCommon(cssConfig.getChannel(), requestDate,
        cssConfig.getUserAuthen(), cssConfig.getPassword());
    CssInfo cssInfo = new CssInfo(cssParam.getProfileId(), cssConfig.getSpecializedBank(),
        loanApplicationEntity.getIdNo());
    CssRequest cssRequest = new CssRequest(cssCommon, cssInfo);
    CssResponse response = callApi(cssRequest);
    CssEntity cssEntity = cssRepository.findByLoanApplicationId(cssParam.getLoanApplicationId());
    if (cssEntity == null) {
      cssEntity = new CssEntity();
    }
    fillData(cssEntity, cssParam, response);
    cssRepository.save(cssEntity);
    return CssMapper.INSTANCE.toModel(cssEntity);
  }

  private void fillData(CssEntity cssEntity, CssParam cssParam, CssResponse response)
      throws ParseException {
    cssEntity.setLoanApplicationId(cssParam.getLoanApplicationId());
    cssEntity.setProfileId(cssParam.getProfileId());
    if (response != null && response.getData() != null) {
      if (response.getData().getRB() != null) {
        cssEntity.setScore(
            !CollectionUtils.isEmpty(response.getData().getRB().getScoreRM()) ? response.getData()
                .getRB().getScoreRM().get(0) : null);
        cssEntity.setGrade(
            !CollectionUtils.isEmpty(response.getData().getRB().getRankRM()) ? response.getData()
                .getRB().getRankRM().get(0) : null);
      }
      String strScoringTime =
          response.getData().getResponse() != null ? response.getData().getResponse()
              .getScoringTime() : null;
      if (!StringUtils.isEmpty(strScoringTime)) {
        String[] splits = strScoringTime.split(" ");
        //bo ko lay gio
        if (splits.length > 0) {
          Date date = new SimpleDateFormat("dd/MM/yyyy").parse(splits[0]);
          cssEntity.setScoringDate(date);
        }
      }
    }
    cssEntity.setMetaData(response != null ? response.toString() : null);
  }

  @Override
  public Css getCss(String loanId) {
    CssEntity cssEntity = cssRepository.findByLoanApplicationId(loanId);
    return CssMapper.INSTANCE.toModel(cssEntity);
  }

  @Transactional
  private CssResponse callApi(CssRequest cssRequest) throws ApplicationException {
    ResponseEntity<CssResponse> responseEntity = cssClient.getScore(cssRequest,
        envProperties.getClientCode());
    if (responseEntity != null) {
      CssResponse cssResponse = responseEntity.getBody();
      log.info("Css ProfileId : " + cssRequest.getInfo().getProfileId() + " --> response: "
          + cssResponse);
      return cssResponse;
    } else {
      throw new ApplicationException(ErrorEnum.API_CSS_FAIL);
    }
  }
}
