package vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class DSChamTTTheTD {
    private List<ChamTTTheTD> chamTTTheTDList;

    public List<ChamTTTheTD> getChamTTTheTDList() {
        return chamTTTheTDList;
    }

    @XmlElement(name = "ChamTTTheTD")
    public void setChamTTTheTDList(List<ChamTTTheTD> chamTTTheTDList) {
        this.chamTTTheTDList = chamTTTheTDList;
    }
}
