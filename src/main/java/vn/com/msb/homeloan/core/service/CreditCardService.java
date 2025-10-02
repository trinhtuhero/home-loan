package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.CreditCard;

public interface CreditCardService {

  void deleteByUuid(String uuid, ClientTypeEnum clientType);

  CreditCard save(CreditCard creditCard, ClientTypeEnum clientTypeEnum);

  String validateCreditCard(String loanId);
}
