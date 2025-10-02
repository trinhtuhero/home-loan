package vn.com.msb.homeloan.core.model.request.mvalue;

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
public class CMSTsdg {
    private String collateralId;
    private String soGCN;
    private String  hdsksmbs;
    private int  collateralGroup;
    private int  collateralType;
    private String  province;
    private String  district;
    private String  ward;
    private String  street;
    private String  soNha;
    private String  khuVuc;
    private int  diaChiKinhDoanh;
    private String  chuSoHuu;
    private String  quanHeCshVaKh;
}
