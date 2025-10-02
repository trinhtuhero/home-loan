package vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding;

import javax.xml.bind.annotation.XmlElement;

public class DoNo12Thang {
    private DuNoChiTiet duNoChiTiet;

    public DuNoChiTiet getDuNoChiTiet() {
        return duNoChiTiet;
    }

    @XmlElement(name = "DuNoChiTiet")
    public void setDuNoChiTiet(DuNoChiTiet duNoChiTiet) {
        this.duNoChiTiet = duNoChiTiet;
    }
}
