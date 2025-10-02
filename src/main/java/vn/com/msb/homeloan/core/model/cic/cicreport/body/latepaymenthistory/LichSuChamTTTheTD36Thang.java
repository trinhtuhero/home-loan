package vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory;

import javax.xml.bind.annotation.XmlElement;

public class LichSuChamTTTheTD36Thang {
    private TCTD tctd;

    public TCTD getTctd() {
        return tctd;
    }

    @XmlElement(name = "TCTD")
    public void setTctd(TCTD tctd) {
        this.tctd = tctd;
    }
}
