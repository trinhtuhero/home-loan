package vn.com.msb.homeloan.core.service;

import java.util.List;
import java.util.Map;
import vn.com.msb.homeloan.core.model.CardType;

public interface CardTypeService {

  List<CardType> getCardTypes();

  Map<String, String> getCardPolicyMapCodes(List<String> codes);
}
