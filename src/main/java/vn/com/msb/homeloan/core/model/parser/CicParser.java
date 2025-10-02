package vn.com.msb.homeloan.core.model.parser;

import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.model.cic.CicDebtReport;

/**
 * CIC content parser
 */
public interface CicParser {

  /**
   * Find current debt group.
   *
   * @return Current debt group
   */
  CicGroupEnum findCurrentDebtGroupNaturalPerson();

  CicGroupEnum findCurrentDebtGroupLegalPerson();

  /**
   * Find debt group within x months prior.
   *
   * @param months Number of months to query
   * @return Debt group in x months prior
   */
  CicGroupEnum findLastXMonthsDebtGroupNaturalPerson(int months, String xmlContent);

  CicGroupEnum findLastXMonthsDebtGroupLegalPerson(int months, String xmlContent);

  /**
   * Find list of total debt report for each TCTD.
   *
   * @return List of CIC debt report
   */
  CicDebtReport findCurrentTotalDebtReport();

}
