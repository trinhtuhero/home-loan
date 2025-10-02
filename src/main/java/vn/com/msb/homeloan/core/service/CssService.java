package vn.com.msb.homeloan.core.service;

import java.text.ParseException;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Css;
import vn.com.msb.homeloan.core.model.CssParam;

public interface CssService {

  Css getScore(CssParam cssParam, String requestDate) throws ApplicationException, ParseException;

  Css getCss(String loanId);
}
