package vn.com.msb.homeloan.core.constant.mvalue;

public enum AssetTypeEnum {
    APARTMENT("apartment", "Chung cư"),
    MEANS_OF_TRANSPORT("meansOfTransport", "Phương tiện vận tải"),
    RESIDENTIAL_LAND("residentialLand", "Nhà đất"),
    VILLA("villa", "Nhà liền kề/Biệt thự/Shophouse");
    private String code;
    private String name;

    AssetTypeEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
