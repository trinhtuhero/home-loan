package vn.com.msb.homeloan.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;

@Slf4j
public class SHA256EncryptUtil {

  public static boolean valid(String hash, String secret, String loanId)
      throws NoSuchAlgorithmException {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(
          String.format("%s%s", secret, loanId).getBytes(StandardCharsets.UTF_8));
      String str = new String(Hex.encode(encodedHash));
      if (hash.equals(str)) {
        return true;
      }
    } catch (Exception ex) {
      log.info("[SHA256EncryptUtil] error: ", ex);
    }
    return false;
  }

  public static String createLinkMobio(String secret, String loanId) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(
          String.format("%s%s", secret, loanId).getBytes(StandardCharsets.UTF_8));
      return new String(Hex.encode(encodedHash));
    } catch (Exception ex) {
      log.info("[SHA256EncryptUtil] error: ", ex);
    }
    return "";
  }

  public static String createCJ4Token(String secret, String str) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(
          String.format("%s%s", secret, str).getBytes(StandardCharsets.UTF_8));
      return new String(Hex.encode(encodedHash));
    } catch (Exception ex) {
      log.info("[SHA256EncryptUtil] error: ", ex);
    }
    return "";
  }
}
