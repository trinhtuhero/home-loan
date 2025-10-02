package vn.com.msb.homeloan.core.model.response.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @JsonProperty("profileId")
    private String profileId;

    @JsonProperty("lstTaiSan")
    private List<Asset> assets;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Asset {
        @JsonProperty("id")
        private String id;

        @JsonProperty("hd_sk_sm_bs")
        private String hdsksmbs;

        @JsonProperty("soGCN")
        private String soGCN;
    }
}
