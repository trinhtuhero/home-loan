package vn.com.msb.homeloan.core.service;

import java.util.List;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

public interface OverdraftService {

  Overdraft save(Overdraft overdraft, ClientTypeEnum clientTypeEnum);

  List<Overdraft> saves(List<Overdraft> overdraft, ClientTypeEnum clientTypeEnum);

  void delete(String uuid, ClientTypeEnum clientTypeEnum);

  List<Overdraft> getListOverdraft(String loanId);
}
