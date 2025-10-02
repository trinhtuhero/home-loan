package vn.com.msb.homeloan.core.model.request.mvalue;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSCreateProfileRequest {
    private String mucDichDinhGia;
    private String mucDichDinhGiaKhac;
    private String dvthDinhGia;
    private String hinhThucDinhGia;
    private String lyDoThueNgoai;
    private String ctyThueNgoai;
    private String loanId;
    private List<CMSTsdg> listTsdg;
    private List<Long> lsTS;
}
