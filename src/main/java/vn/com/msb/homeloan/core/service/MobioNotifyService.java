package vn.com.msb.homeloan.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.com.msb.homeloan.core.model.MobioNotifyMessage;

public interface MobioNotifyService {

  void process(MobioNotifyMessage mobioNotifyMessage) throws JsonProcessingException;
}
