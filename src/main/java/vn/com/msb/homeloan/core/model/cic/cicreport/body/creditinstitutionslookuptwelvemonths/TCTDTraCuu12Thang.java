package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionslookuptwelvemonths;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class TCTDTraCuu12Thang {
    private List<TCTDTraCuu> tctdTraCuuList;

    public List<TCTDTraCuu> getTctdTraCuuList() {
        return tctdTraCuuList;
    }
    @XmlElement(name = "TCTDTraCuu")
    public void setTctdTraCuuList(List<TCTDTraCuu> tctdTraCuuList) {
        this.tctdTraCuuList = tctdTraCuuList;
    }
}
