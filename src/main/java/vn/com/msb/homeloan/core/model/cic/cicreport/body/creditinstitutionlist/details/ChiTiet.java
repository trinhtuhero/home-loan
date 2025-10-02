package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details;

import lombok.Getter;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.debt.DuNo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ChiTiet", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details")
@Getter
@Setter
public class ChiTiet {
    private DuNo loaiDuNo;
    private TSBD tsbd;

    public DuNo getLoaiDuNo() {
        return loaiDuNo;
    }
    @XmlElement(name = "DuNo", type = DuNo.class)
    public void setLoaiDuNo(DuNo loaiDuNo) {
        this.loaiDuNo = loaiDuNo;
    }

    public TSBD getTsbd() {
        return tsbd;
    }
    @XmlElement(name = "TSBD", type = TSBD.class)
    public void setTsbd(TSBD tsbd) {
        this.tsbd = tsbd;
    }
}
