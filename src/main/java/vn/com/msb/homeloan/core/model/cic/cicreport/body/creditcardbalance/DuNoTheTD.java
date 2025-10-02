package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance;

import javax.xml.bind.annotation.XmlElement;

public class DuNoTheTD {
    private DuNoTheTDChiTiet duNoTheTDChiTiet;

    public DuNoTheTDChiTiet getDuNoTheTDChiTiet() {
        return duNoTheTDChiTiet;
    }
    @XmlElement(name = "DuNoTheTDChiTiet")
    public void setDuNoTheTDChiTiet(DuNoTheTDChiTiet duNoTheTDChiTiet) {
        this.duNoTheTDChiTiet = duNoTheTDChiTiet;
    }
}
