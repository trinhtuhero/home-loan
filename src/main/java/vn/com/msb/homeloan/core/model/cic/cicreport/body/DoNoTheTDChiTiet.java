package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding.TCTD;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DoNoTheTDChiTiet {

    private List<TCTD> tctdList;

    public List<TCTD> getTctdList() {
        return tctdList;
    }

    @XmlElement(name = "TCTD")
    public void setTctdList(List<TCTD> tctdList) {
        this.tctdList = tctdList;
    }

}
