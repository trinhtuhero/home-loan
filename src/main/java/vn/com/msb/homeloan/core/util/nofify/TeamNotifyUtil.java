package vn.com.msb.homeloan.core.util.nofify;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.client.NotifyClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamNotifyUtil {

  private final NotifyClient notifyClient;

  public String createAdaptiveCard(String filePath, String exception) throws IOException {
    InputStream inputStream = null;
    try {
      // Read JSON
      inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
      String adaptiveCardJson = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      // Replace placeholders with the actual values
      adaptiveCardJson = StringUtils.replace(adaptiveCardJson, "<EXCEPTION>", exception);
      return adaptiveCardJson;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  public void send(String str) {
    try {
      notifyClient.send(createAdaptiveCard("card.json", str));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
