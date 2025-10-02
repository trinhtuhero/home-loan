package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details;

import javax.xml.bind.annotation.XmlElement;

public class TSBD {
    private String tsbdYes;

    private String tsbdNo;

    public String getTsbdYes() {
        return tsbdYes;
    }

    @XmlElement(name = "CoTSBD")
    public void setTsbdYes(String tsbdYes) {
        this.tsbdYes = tsbdYes;
    }

    public String getTsbdNo() {
        return tsbdNo;
    }

    @XmlElement(name = "KhongTSBD")
    public void setTsbdNo(String tsbdNo) {
        this.tsbdNo = tsbdNo;
    }

}
