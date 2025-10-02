package vn.com.msb.homeloan.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.MobioNotiConsumeMessageLogEntity;
import vn.com.msb.homeloan.core.entity.PicRmHistoryEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MobioNotifyMessage;
import vn.com.msb.homeloan.core.model.MobioNotifyMessageValue;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MobioNotiConsumeMessageLogRepository;
import vn.com.msb.homeloan.core.repository.PicRmHistoryRepository;
import vn.com.msb.homeloan.core.service.InsuranceService;
import vn.com.msb.homeloan.core.service.MobioNotifyService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
@AllArgsConstructor
public class MobioNotifyServiceImpl implements MobioNotifyService {

  private final MobioNotiConsumeMessageLogRepository mobioNotiConsumeMessageLogRepository;

  private final LoanApplicationRepository loanApplicationRepository;

  private final CmsUserRepository cmsUserRepository;

  private final ObjectMapper objectMapper;

  private final PicRmHistoryRepository picRmHistoryRepository;

  @Qualifier("fixedThreadPool")
  private final ExecutorService executorService;

  private final InsuranceService insuranceService;

  @Override
  public void process(MobioNotifyMessage mobioNotifyMessage) throws JsonProcessingException {
    // log
    log.info("mobioNotifyMessage: {}", mobioNotifyMessage != null);

    MobioNotiConsumeMessageLogEntity mobioNotiConsumeMessageLogEntity = MobioNotiConsumeMessageLogEntity.builder()
        .topic(mobioNotifyMessage.getTopic())
        .partition(mobioNotifyMessage.getPartition())
        .offset(mobioNotifyMessage.getOffset())
        .key(mobioNotifyMessage.getKey())
        .value(mobioNotifyMessage.getValue())
        .timestamp(mobioNotifyMessage.getTimestamp())
        .timestampType(mobioNotifyMessage.getTimestampType())
        .consumerRecord(mobioNotifyMessage.getConsumerRecord())
        .build();

    // log
    log.info("mobioNotiConsumeMessageLogEntity: {}", mobioNotiConsumeMessageLogEntity != null);

    mobioNotiConsumeMessageLogRepository.save(mobioNotiConsumeMessageLogEntity);

    // get value of message
    MobioNotifyMessageValue mobioNotifyMessageValue = objectMapper.readValue(
        mobioNotifyMessage.getValue(), MobioNotifyMessageValue.class);

    // log
    log.info("mobioNotifyMessageValue: {}", mobioNotifyMessageValue != null);

    // logic
    log.info(
        "mobioNotifyMessageValue.getDealCode: {}, mobioNotifyMessageValue.getAssigneeEmail: {}, mobioNotifyMessageValue.getLastUpdate: {}",
        mobioNotifyMessageValue.getDealCode(), mobioNotifyMessageValue.getAssigneeEmail(),
        mobioNotifyMessageValue.getLastUpdate());
    if (mobioNotifyMessageValue.getDealCode() != null
        && mobioNotifyMessageValue.getAssigneeEmail() != null
        && mobioNotifyMessageValue.getLastUpdate() != null) {
      LoanApplicationEntity loanApplicationEntity = loanApplicationRepository.findByLoanCode(
          mobioNotifyMessageValue.getDealCode());

      log.info("loanApplicationEntity: {}", loanApplicationEntity != null);
      if (loanApplicationEntity != null) {
        String picFrom = loanApplicationEntity.getPicRm();
        log.info("picFrom: {}", picFrom);
        CmsUserEntity cmsUserEntity = cmsUserRepository.findByEmail(
            mobioNotifyMessageValue.getAssigneeEmail()).orElse(null);

        // lastUpdate -> timestamp to date
        long lastUpdate = Double.valueOf(mobioNotifyMessageValue.getLastUpdate()).longValue();
        Date lastUpdateDate = new Date(lastUpdate * 1000);

        log.info("cmsUserEntity: {}", cmsUserEntity != null);
        if (cmsUserEntity != null) {
          // compare
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
          log.info("getReceiveDate: {}",
              loanApplicationEntity.getReceiveDate() != null ? dateFormat.format(
                  Date.from(loanApplicationEntity.getReceiveDate())) : null);
          log.info("lastUpdateDate: {}",
              lastUpdateDate != null ? dateFormat.format(lastUpdateDate) : null);

          if (loanApplicationEntity.getReceiveDate() == null
              || lastUpdateDate.compareTo(Date.from(loanApplicationEntity.getReceiveDate())) > 0
              && !Objects.equals(cmsUserEntity.getEmplId(), picFrom)) {

            log.info("picTo: {}", cmsUserEntity.getEmplId());

            loanApplicationEntity.setPicRm(cmsUserEntity.getEmplId());
            loanApplicationEntity.setReceiveDate(Instant.now());
            // history
            loanApplicationRepository.save(loanApplicationEntity);

            PicRmHistoryEntity picRmHistoryEntity = PicRmHistoryEntity.builder()
                .loanApplicationId(loanApplicationEntity.getUuid())
                .picFrom(picFrom)
                .picTo(cmsUserEntity.getEmplId())
                .system("MOBIO")
                .messageId(mobioNotiConsumeMessageLogEntity.getUuid())
                .build();
            log.info("picRmHistoryEntity: {}", picRmHistoryEntity != null);
            picRmHistoryRepository.save(picRmHistoryEntity);

            // update lead to CJ4
            executorService.execute(() -> {
              try {
                String picEmplId;
                PicRmHistoryEntity firstPicEntity = picRmHistoryRepository.findFirstPic(
                    loanApplicationEntity.getUuid());
                if (firstPicEntity != null) {
                  picEmplId = !StringUtils.isEmpty(firstPicEntity.getPicFrom())
                      ? firstPicEntity.getPicFrom() : firstPicEntity.getPicTo();
                } else {
                  picEmplId = cmsUserEntity.getEmplId();
                }

                insuranceService.updateLead(loanApplicationEntity.getUuid(), picEmplId,
                    InterestedEnum.INTERESTED.equals(loanApplicationEntity.getCj4Interested())
                        ? InterestedEnum.INTERESTED : InterestedEnum.NOT_INTERESTED, null);
              } catch (Exception ex) {
                log.error("update lead to CJ4 error: ", ex);
              }
            });
          }
        } else {
          // user not found
          throw new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "cms_users:email",
              mobioNotifyMessageValue.getAssigneeEmail());
        }
      }
    }
  }
}
