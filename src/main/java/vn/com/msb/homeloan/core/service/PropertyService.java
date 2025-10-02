package vn.com.msb.homeloan.core.service;

public interface PropertyService {

  <T> T getByName(String name, Class<T> object);

  String getHomePageConfiguration(String type);

  String saveOrUpdate(String name, String currentValue);
}
