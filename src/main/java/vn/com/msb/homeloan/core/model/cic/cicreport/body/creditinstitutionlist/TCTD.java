package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist;

import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.ChiTiet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TCTD", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist")
public class TCTD {
    private TTChung ttChung;
    private ChiTiet chiTiet;

    public TTChung getTtChung() {
        return ttChung;
    }

    @XmlElement(name = "TTChung")
    public void setTtChung(TTChung ttChung) {
        this.ttChung = ttChung;
    }

    public ChiTiet getChiTiet() {
        return chiTiet;
    }

    @XmlElement(name = "ChiTiet")
    public void setChiTiet(ChiTiet chiTiet) {
        this.chiTiet = chiTiet;
    }

}
