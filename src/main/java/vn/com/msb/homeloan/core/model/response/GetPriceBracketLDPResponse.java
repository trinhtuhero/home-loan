package vn.com.msb.homeloan.core.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPriceBracketLDPResponse {
  private ResponseGetPriceBracketInfo data;
  private Integer status;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ResponseGetPriceBracketInfo {
    private ResponseGetPriceBracket response;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseGetPriceBracket {

      private String code;

      private String message;

      private List<ResidentialLandResponse> residentialLand;

      private List<ApartmentResponse> apartment;

      private List<VillaResponse> villa;

      private List<MeansOfTransportResponse> meansOfTransport;

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class ResidentialLandResponse {

        private RoadSegment roadSegment;

        private Options province;

        private Options street;

        private Options district;

        private Price price;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class RoadSegment {
          private String code;

          private String roadSegmentFrom;

          private String roadSegmentTo;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Price {

          private String statePrice;

          private String grade1Price;

          private String grade2Price;
        }
      }

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class ApartmentResponse {

        private String price;

        private String floor;

        private Options building;

        private String apartmentNumber;
      }

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class VillaResponse {

        private String landLotNumber;

        private String subdivision;

        private String road;

        private Options project;

        private String price;
      }

      @Getter
      @Setter
      @NoArgsConstructor
      @AllArgsConstructor
      @Builder
      public static class MeansOfTransportResponse {

        private Options rangeOfVehicle;

        private Options vehicles;

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
}
