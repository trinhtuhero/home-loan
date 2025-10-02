package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionslookuptwelvemonths;

import javax.xml.bind.annotation.XmlElement;

public class TCTDTraCuu {
    private String tenTCTD;
    private String ngayTraCuu;

    public String getTenTCTD() {
        return tenTCTD;
    }
    @XmlElement(name = "TenTCTD")
    public void setTenTCTD(String tenTCTD) {
        this.tenTCTD = tenTCTD;
    }

    public String getNgayTraCuu() {
        return ngayTraCuu;
    }
    @XmlElement(name = "NgayTraCuu")
    public void setNgayTraCuu(String ngayTraCuu) {
        this.ngayTraCuu = ngayTraCuu;
    }
}
