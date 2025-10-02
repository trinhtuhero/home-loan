package vn.com.msb.homeloan.core.model.response.opRisk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpsRiskCheckCResponse {

  @JsonProperty(value = "data")
  private Data data;

  @JsonProperty(value = "status")
  private Integer status;

  @lombok.Data
  public static class Data {

    @JsonProperty(value = "checkBlackListC")
    private CheckBlackListC checkBlackListC;

    @lombok.Data
    public static class CheckBlackListC {

      @JsonProperty(value = "respMessage")
      private RespMessage respMessage;

      @JsonProperty(value = "respDomain")
      private RespDomain respDomain;

      @lombok.Data
      public static class RespDomain {

        @JsonProperty(value = "BlackList4LosC")
        private BlackList4LosC blackList4LosC;

        @lombok.Data
        public static class BlackList4LosC {

          @JsonProperty(value = "borrowersByThirdParty")
          private String borrowersByThirdParty;
          @JsonProperty(value = "cId")
          private String cId;
          @JsonProperty(value = "classification")
          private String classification;
          @JsonProperty(value = "dateAdded")
          private String dateAdded;
          @JsonProperty(value = "division")
          private String division;
          @JsonProperty(value = "endDate")
          private String endDate;
          @JsonProperty(value = "errorMess")
          private String errorMess;
          @JsonProperty(value = "iDcard_passport")
          private String iDcardPassport;
          @JsonProperty(value = "iDcard_passportBusinessRegistration")
          private String iDcardPassportBusinessRegistration;
          @JsonProperty(value = "id")
          private String id;
          @JsonProperty(value = "identificationInformation")
          private String identificationInformation;
          @JsonProperty(value = "inputUser")
          private String inputUser;
          @JsonProperty(value = "listedReasons")
          private String listedReasons;
          @JsonProperty(value = "owner")
          private String owner;
          @JsonProperty(value = "result")
          private String result;
          @JsonProperty(value = "propertyDescription")
          private String propertyDescription;
          @JsonProperty(value = "typeOfProperty")
          private String typeOfProperty;
          @JsonProperty(value = "version")
          private String version;
        }
      }
    }
  }
}
