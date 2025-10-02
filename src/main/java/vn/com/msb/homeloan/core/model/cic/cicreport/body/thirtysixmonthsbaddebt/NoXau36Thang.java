package vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt;

import javax.xml.bind.annotation.XmlElement;

public class NoXau36Thang {
    private NoXauChiTiet noXauChiTiet;

    public NoXauChiTiet getNoXauChiTiet() {
        return noXauChiTiet;
    }

    @XmlElement(name = "NoXauChiTiet")
    public void setNoXauChiTiet(NoXauChiTiet noXauChiTiet) {
        this.noXauChiTiet = noXauChiTiet;
    }
}
