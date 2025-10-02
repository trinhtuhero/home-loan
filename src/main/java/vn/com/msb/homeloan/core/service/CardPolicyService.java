package vn.com.msb.homeloan.core.service;

import java.util.List;
import java.util.Map;
import vn.com.msb.homeloan.core.model.CardPolicy;

public interface CardPolicyService {

  List<CardPolicy> getCardPolicies();

  Map<String, String> getCardPolicyMapCodes(List<String> codes);
}
