package vn.com.msb.homeloan.core.model.response.cj4;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.class)
public class CalculateInsurance {

  List<Main> main;
  List<Additional> additional;
  List<AdditionalCJ5> additionalCJ5;
  Long yearlyFee;

  @lombok.Data
  public static class Main {

    String code;
    String value;
    String valueFormula;
    String title;
    List<Data> data;

    @lombok.Data
    public static class Data {

      String value;
      String title;
    }
  }

  @lombok.Data
  public static class Additional {

    String code;
    String title;
    String[] benefit;
  }

  @lombok.Data
  public static class AdditionalCJ5 {

    String code;
    String value;
    String title;
  }
}
