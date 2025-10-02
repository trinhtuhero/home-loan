package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;

public class NoVAMC {
    private NoVAMCChiTiet noVAMCChiTiet;

    public NoVAMCChiTiet getNoVAMCChiTiet() {
        return noVAMCChiTiet;
    }

    @XmlElement(name = "NoVAMCChiTiet")
    public void setNoVAMCChiTiet(NoVAMCChiTiet noVAMCChiTiet) {
        this.noVAMCChiTiet = noVAMCChiTiet;
    }
}
