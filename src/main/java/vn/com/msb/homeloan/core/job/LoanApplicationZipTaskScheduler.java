package vn.com.msb.homeloan.core.job;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;

@Slf4j
@Component
class LoanApplicationZipTaskScheduler {


  @Autowired
  private LoanUploadFileService loanUploadFileService;

  @Autowired
  private LoanApplicationService loanApplicationService;


  @Scheduled(cron = "0 0/1 * * * ?")
  @SchedulerLock(name = "TaskScheduler_loanApplicationZipJob", lockAtLeastForString = "PT1M", lockAtMostForString = "PT10M")
  public void scheduledTask() throws Exception {
    // Action
    log.info("Job started:");
    List<LoanApplication> loanApplications = loanApplicationService.checkAndAssignLoanAppNeedToUploadZip();
    for (LoanApplication loanApplication : loanApplications) {
      loanUploadFileService.zipLoanApplicationAndThenUploadToS3(loanApplication);
    }
  }
}
