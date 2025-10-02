package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetPriceBracketInfo {
  @JsonProperty(value = "response")
  private ResponseGetPriceBracket response;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ResponseGetPriceBracket {
    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "residentialLand")
    private List<ResidentialLandResponse> residentialLand;

    @JsonProperty(value = "apartment")
    private List<ApartmentResponse> apartment;

    @JsonProperty(value = "villa")
    private List<VillaResponse> villa;

    @JsonProperty(value = "meansOfTransport")
    private List<MeansOfTransportResponse> meansOfTransport;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class  ResidentialLandResponse {
      @JsonProperty(value = "roadSegment")
      private RoadSegment roadSegment;

      @JsonProperty(value = "province")
      private Options province;

      @JsonProperty(value = "street")
      private Options street;

      @JsonProperty(value = "district")
      private Options district;

      @JsonProperty(value = "price")
      private Price price;

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class RoadSegment {
        private String code;

        @JsonProperty(value = "roadSegmentFrom")
        private String roadSegmentFrom;

        @JsonProperty(value = "roadSegmentTo")
        private String roadSegmentTo;
      }

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class  Price {
        @JsonProperty(value = "statePrice")
        private String statePrice;

        @JsonProperty(value = "grade1Price")
        private String grade1Price;

        @JsonProperty(value = "grade2Price")
        private String grade2Price;
      }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApartmentResponse {
      @JsonProperty(value = "price")
      private String price;

      @JsonProperty(value = "floor")
      private String floor;

      @JsonProperty(value = "building")
      private Options building;

      @JsonProperty(value = "apartmentNumber")
      private String apartmentNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VillaResponse {
      @JsonProperty(value = "landLotNumber")
      private String landLotNumber;

      @JsonProperty(value = "subdivision")
      private String subdivision;

      @JsonProperty(value = "road")
      private String road;

      @JsonProperty(value = "project")
      private Options project;

      @JsonProperty(value = "price")
      private String price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MeansOfTransportResponse {
      @JsonProperty(value = "rangeOfVehicle")
      private Options rangeOfVehicle;

      @JsonProperty(value = "vehicles")
      private Options vehicles;

      @JsonProperty(value = "basicPrice")
      private String basicPrice;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Options {
      private String code;
      private String name;
    }
  }
}
