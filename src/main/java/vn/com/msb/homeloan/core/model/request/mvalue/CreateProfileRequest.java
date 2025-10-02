package vn.com.msb.homeloan.core.model.request.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class CreateProfileRequest {
    @JsonProperty(value = "username_cbkt")
    private String usernameCbkt;

    @JsonProperty(value = "tenKH")
    private String tenKH;

    @JsonProperty(value = "dkkd_cmnd")
    private String dkkdCmnd;

    @JsonProperty(value = "phankhuckh")
    private String phanKhucKh;

    @JsonProperty(value = "loai_giayto")
    private String loaiGiayTo;

    @JsonProperty(value = "donvidndg")
    private String donVidndg;

    @JsonProperty(value = "gdqhkh")
    private String gdqhkh;

    @JsonProperty(value = "sdt_gdqhkh")
    private String sdtgdqhkh;

    @JsonProperty(value = "canbophutrach")
    private String canBoPhuTrach;

    @JsonProperty(value = "sdt_cbpt")
    private String sdtCbpt;

    @JsonProperty(value = "daumoilienhe")
    private String dauMoiLienHe;

    @JsonProperty(value = "sdt_daumoi")
    private String sdtDauMoi;

    @JsonProperty(value = "diachi_daumoi")
    private String diaChiDauMoi;

    @JsonProperty(value = "mucdichdinhgia")
    private String mucDichDinhGia;

    @JsonProperty(value = "mucdichdinhgiakhac")
    private String mucDichDinhGiaKhac;

    @JsonProperty(value = "giatri_khoanvaydukien")
    private Long giaTriKhoanVayDuKien;

    @JsonProperty(value = "ngaykshtdukien_from")
    private String ngaykshtdukienfrom;

    @JsonProperty(value = "bendndg")
    private String benDndg;

    @JsonProperty(value = "dvthDinhGia")
    private String dvthDinhGia;

    @JsonProperty(value = "hinhthucDinhGia")
    private String hinhThucDinhGia;

    @JsonProperty(value = "chitiet_ben_dndg")
    private String chiTietBendndg;

    @JsonProperty(value = "lydothuengoai")
    private String lyDoThueNgoai;

    @JsonProperty(value = "ctythuengoai")
    private String ctyThueNgoai;

    @JsonProperty(value = "listTsdg")
    private List<Tsdg> listTsdg;

}
