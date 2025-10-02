package vn.com.msb.homeloan.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.apache.commons.codec.digest.DigestUtils;

public class StringUtils {

  private StringUtils() {
    throw new IllegalStateException("StringUtils class");
  }

  /**
   * Check whether the given String contains any whitespace characters.
   *
   * @param str the String to check (may be <code>null</code>)
   * @return <code>true</code> if the String is not empty and
   * contains at least 1 whitespace character
   * @see Character#isWhitespace
   */
  public static boolean containsWhitespace(String str) {
    if (!hasLength(str)) {
      return false;
    }
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check that the given String is neither <code>null</code> nor of length 0. Note: Will return
   * <code>true</code> for a String that purely consists of whitespace.
   * <p><pre>
   * StringUtils.hasLength(null) = false
   * StringUtils.hasLength("") = false
   * StringUtils.hasLength(" ") = true
   * StringUtils.hasLength("Hello") = true
   * </pre>
   *
   * @param str the String to check (may be <code>null</code>)
   * @return <code>true</code> if the String is not null and has length
   */
  public static boolean hasLength(String str) {
    return (str != null && str.length() > 0);
  }

  public static String sha256(String dataRaw) {
    String secureHash = DigestUtils.sha256Hex(dataRaw);
    return secureHash;
  }

  public static LocalDate stringToLocalDate(String date, String format) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
      return LocalDate.parse(date, formatter);
    } catch (Exception e) {
      return null;
    }
  }


  // Returns true if d is in format
  // /dd/mm/yyyy
  public static boolean isValidDate(String date) {
    String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    return date.matches(regex);
  }

  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty() || str.trim().isEmpty();
  }

  public static boolean isNameValidate(String name) {
    String regex = "[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÌÍỈĨỊÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ\\s]+$";
    return name.matches(regex);
  }

  public static boolean isPhone(String string) {
    String exp = "((0)[3|5|7|8|9])+([0-9]{8})\\b";
    return string.matches(exp);
  }

  public static String hashPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || !isPhone(phoneNumber)) {
      return null;
    }
    if (phoneNumber.startsWith("84")) {
      return phoneNumber.substring(0, 4) + "-XXXX-" + phoneNumber.substring(8,
          phoneNumber.length());
    } else {
      return phoneNumber.substring(0, 3) + "-XXXX-" + phoneNumber.substring(7,
          phoneNumber.length());
    }
  }

  public static boolean isIdNumber(String idNumber) {
    String regexNumber = null;
    switch (idNumber.length()) {
      case 8:
        regexNumber = "[A-Z]{1}[0-9]{7}";
        return idNumber.matches(regexNumber);
      case 9:
        regexNumber = "[0]{1}[0-9]{8}";
        return idNumber.matches(regexNumber);
      case 12:
        regexNumber = "[0]{1}[0-9]{11}";
        return idNumber.matches(regexNumber);
      default:
        return false;
    }
  }

  public static String trim(String str) {
    return isEmpty(str) ? "" : str.trim();
  }

  public static boolean isContain(String[] array, String item) {
    return Arrays.asList(array).contains(item);
  }
}
