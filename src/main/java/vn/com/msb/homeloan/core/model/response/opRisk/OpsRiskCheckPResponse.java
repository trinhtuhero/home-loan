package vn.com.msb.homeloan.core.model.response.opRisk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpsRiskCheckPResponse {

  @JsonProperty(value = "data")
  private Data data;

  @JsonProperty(value = "status")
  private Integer status;

  @lombok.Data
  public static class Data {

    @JsonProperty(value = "checkBlackListP")
    private CheckBlackListP checkBlackListP;

    @lombok.Data
    public static class CheckBlackListP {

      @JsonProperty(value = "respMessage")
      private RespMessage respMessage;

      @JsonProperty(value = "respDomain")
      private OpsRiskResponseDomain respDomain;

      @lombok.Data
      public static class OpsRiskResponseDomain {

        @JsonProperty(value = "BlackList4LosP")
        private BlackList4LosP blackList4LosP;

        @lombok.Data
        public static class BlackList4LosP {

          @JsonProperty(value = "address")
          private String address;
          @JsonProperty(value = "branchInput")
          private String branchInput;
          @JsonProperty(value = "cusId")
          private String cusId;
          @JsonProperty(value = "dateEx")
          private String dateEx;
          @JsonProperty(value = "dateInput")
          private String dateInput;
          @JsonProperty(value = "dob")
          private String dob;
          @JsonProperty(value = "dscn")
          private String dscn;
          @JsonProperty(value = "dtcn")
          private String dtcn;
          @JsonProperty(value = "errorMess")
          private String errorMess;
          @JsonProperty(value = "id")
          private String id;
          @JsonProperty(value = "inputUser")
          private String inputUser;
          @JsonProperty(value = "name")
          private String name;
          @JsonProperty(value = "recordId")
          private String recordId;
          @JsonProperty(value = "result")
          private String result;
          @JsonProperty(value = "version")
          private String version;
        }
      }
    }
  }
}
