package vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DuNoChiTiet {
    private List<TCTD> tctdList;

    public List<TCTD> getTctdList() {
        return tctdList;
    }
    @XmlElement(name = "TCTD")
    public void setTctdList(List<TCTD> tctdList) {
        this.tctdList = tctdList;
    }
}
