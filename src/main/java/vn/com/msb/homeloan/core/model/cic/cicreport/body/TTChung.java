package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TTChung", namespace = "vn.com.msb.homeloan.core.model.cic.CICReport.Body")
public class TTChung {
    private String ngaySL;

    public String getNgaySL() {
        return ngaySL;
    }

    @XmlElement(name = "NgaySL")
    public void setNgaySL(String ngaySL) {
        this.ngaySL = ngaySL;
    }

}
