package vn.com.msb.homeloan.infras.configs;

import java.util.Arrays;
import java.util.List;

public class MockSecretRetriever implements SecretRetriever {

  @Override
  public List<ClientKey> getClientKeys() {
    ClientKey clientKeyDev = new ClientKey();
    clientKeyDev.setClientName("cj4_dev");
    clientKeyDev.setApiKey("YTE5YmRhMzctYjhkZS00YzQ5LTg4NjktM2EyYTc0NWNhZjll");

    ClientKey clientKeyProd = new ClientKey();
    clientKeyProd.setClientName("cj4_prod");
    clientKeyProd.setApiKey("92f14edf805515ae3587df5271a870fdd42f1532de9d23745e24d65e80348044");
    return Arrays.asList(clientKeyDev, clientKeyProd);
  }
}
