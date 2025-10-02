package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;

public class DuNoTheTD12Thang {
    private DoNoTheTDChiTiet doNoTheTDChiTiet;

    public DoNoTheTDChiTiet getDoNoTheTDChiTiet() {
        return doNoTheTDChiTiet;
    }

    @XmlElement(name = "DoNoTheTDChiTiet")
    public void setDoNoTheTDChiTiet(DoNoTheTDChiTiet doNoTheTDChiTiet) {
        this.doNoTheTDChiTiet = doNoTheTDChiTiet;
    }
}
