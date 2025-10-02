package vn.com.msb.homeloan.infras.configs;

import java.util.List;

public interface SecretRetriever {

  List<ClientKey> getClientKeys();
}