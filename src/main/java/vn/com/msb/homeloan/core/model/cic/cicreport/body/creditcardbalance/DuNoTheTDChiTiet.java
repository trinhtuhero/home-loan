package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DuNoTheTDChiTiet {
    private List<TCTD> tctdList;

    public List<TCTD> getTctdList() {
        return tctdList;
    }
    @XmlElement(name = "TCTD")
    public void setTctdList(List<TCTD> tctdList) {
        this.tctdList = tctdList;
    }
}
