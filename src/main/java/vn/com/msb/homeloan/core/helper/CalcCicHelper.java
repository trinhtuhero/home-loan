package vn.com.msb.homeloan.core.helper;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.entity.CicItemEntity;
import vn.com.msb.homeloan.core.model.cic.content.CicContent;
import vn.com.msb.homeloan.core.model.cic.content.DuNo;
import vn.com.msb.homeloan.core.model.cic.content.DuNoTheTD;
import vn.com.msb.homeloan.core.model.cic.content.TCTD;
import vn.com.msb.homeloan.core.parser.ProcessCicParserImpl;

@Component
@Slf4j
public class CalcCicHelper {

  public static CicContent getCicContent(String stringCicContent) {
    ProcessCicParserImpl xmlCicParserImpl = ProcessCicParserImpl.of(stringCicContent);
    CicContent cicContent = xmlCicParserImpl.getCicContentFromXml();
    return cicContent;
  }

  public static void calculate(CicContent cicContent, CicItemEntity cicItem) {
    List<TCTD> tctdList = cicContent.getDstctd().getTctdList();
    List<DuNoTheTD> duNoTheTDList = cicContent.getDuNoTheTDList();
    Long loanMsbSecure = 0l;
    Long loanMsbUnSecure = 0l;
    Long loanNonMsbSecure = 0l;
    Long loanNonMsbUnSecure = 0l;
    Long amountCardNonMsb = 0L;
    Long amountCardMsb = 0L;
    for (TCTD tctd : tctdList) {
      List<DuNo> duNoList = tctd.getChiTiet().getDuNoList();
      if (cicContent.getDstctd().getMsbCicCode().contains(tctd.getTtChung().getMaTCTD())) {
        if (tctd.getChiTiet().getTsbd().isCoTsdb()) {
          loanMsbSecure += calculateLoan(duNoList);
        } else {
          loanMsbUnSecure += calculateLoan(duNoList);
        }
      } else {
        if (tctd.getChiTiet().getTsbd().isCoTsdb()) {
          loanNonMsbSecure += calculateLoan(duNoList);
        } else {
          loanNonMsbUnSecure += calculateLoan(duNoList);
        }
      }
    }
    for (DuNoTheTD duNoTheTD : duNoTheTDList) {
      if (Constants.MSB_NAME.equals(duNoTheTD.getTenTCTDPhatHanh())) {
        amountCardMsb += duNoTheTD.getHMTheTD().multiply(Constants.ONE_MILLION).longValue();
      } else {
        amountCardNonMsb += duNoTheTD.getHMTheTD().multiply(Constants.ONE_MILLION).longValue();
      }
    }
    cicItem.setAmountLoanMsbSecure(loanMsbSecure);
    cicItem.setAmountLoanMsbUnsecure(loanMsbUnSecure);
    cicItem.setAmountLoanNonMsbSecure(loanNonMsbSecure);
    cicItem.setAmountLoanNonMsbUnsecure(loanNonMsbUnSecure);
    cicItem.setAmountCardMsb(amountCardMsb);
    cicItem.setAmountCardNonMsb(amountCardNonMsb);
  }

  private static Long calculateLoan(List<DuNo> duNoList) {
    Long loan = 0l;
    for (DuNo duNo : duNoList) {
      loan += duNo.sumTotalAmount().longValue();
    }
    return loan;
  }
}
