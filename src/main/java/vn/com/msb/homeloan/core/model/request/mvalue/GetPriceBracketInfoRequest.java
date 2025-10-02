package vn.com.msb.homeloan.core.model.request.mvalue;

import lombok.Data;
import vn.com.msb.homeloan.core.constant.mvalue.AssetTypeEnum;

@Data
public class GetPriceBracketInfoRequest {
  private Common common;
  private Info info;

  @Data
  public static class Common {
    private String requestTime;
  }

  @Data
  public static class Info {
    private String assetType;
    private Apartment apartment;
    private Villa villa;
    private MeansOfTransport meansOfTransport;
    private ResidentialLand residentialLand;

    @Data
    public static class Apartment {
      private String buildingCode;
      private String floor;
      private String apartmentNumber;
    }

    @Data
    public static class Villa {
      private String projectCode;
      private String subdivision;
      private String road;
      private String landLotNumber;
    }

    @Data
    public static class MeansOfTransport {
      private String vehiclesCode;
      private String rangeOfVehicleCode;
    }

    @Data
    public static class ResidentialLand {
      private String provinceCode;
      private String districtCode;
      private String streetCode;
      private String roadSegmentCode;
    }
  }
}
