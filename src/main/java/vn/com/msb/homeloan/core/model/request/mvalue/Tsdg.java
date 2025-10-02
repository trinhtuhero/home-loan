package vn.com.msb.homeloan.core.model.request.mvalue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class Tsdg {
    @JsonProperty(value = "soGCN")
    private String soGCN;

    @JsonProperty(value = "hd_sk_sm_bs")
    private String  hdsksmbs;

    @JsonProperty(value = "collateralgroup")
    private int  collateralGroup;

    @JsonProperty(value = "collateraltype")
    private int  collateralType;

    @JsonProperty(value = "tents")
    private String  tenTs;

    @JsonProperty(value = "province")
    private String  province;

    @JsonProperty(value = "district")
    private String  district;

    @JsonProperty(value = "ward")
    private String  ward;

    @JsonProperty(value = "street")
    private String  street;

    @JsonProperty(value = "sonha")
    private String  soNha;

    @JsonProperty(value = "khuvuc")
    private String  khuVuc;

    @JsonProperty(value = "diachikinhdoanh")
    private int  diaChiKinhDoanh;

    @JsonProperty(value = "chusohuu")
    private String  chuSoHuu;

    @JsonProperty(value = "quanhe_csh_va_kh")
    private String  quanHeCshVaKh;

    @JsonProperty(value = "chitiet_quanhe")
    private String  chiTietQuanHe;

    @JsonProperty(value = "mota")
    private String  moTa;

    @JsonProperty(value = "giatrivat")
    private String  giaTriVat;

    @JsonProperty(value = "giatrikhauhao")
    private String  giaTriKhauHao;

    @JsonProperty(value = "giatri_uoctinh")
    private String  giaTriUocTinh;

    @JsonProperty(value = "giatri_dinhgia_gannhat")
    private String  giaTriDinhGiaGanNhat;

    @JsonProperty(value = "thoigianKSDuKien")
    private String  thoiGianKSDuKien;
}
