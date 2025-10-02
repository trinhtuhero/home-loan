package vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "TCTD", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory")
public class TCTD {
    private DSChamTTTheTD dsChamTTTheTD;
    private Date ngaySl;
    private String tenTctd;
    private String ngaySlString;

    public DSChamTTTheTD getDsChamTTTheTD() {
        return dsChamTTTheTD;
    }
    @XmlElement(name = "DSChamTTTheTD")
    public void setDsChamTTTheTD(DSChamTTTheTD dsChamTTTheTD) {
        this.dsChamTTTheTD = dsChamTTTheTD;
    }

    public Date getNgaySl() {
        return ngaySl;
    }

    public void setNgaySl(Date ngaySl) {
        this.ngaySl = ngaySl;
    }

    public String getTenTctd() {
        return tenTctd;
    }
    @XmlElement(name = "TenTCTD")
    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public String getNgaySlString() {
        return ngaySlString;
    }

    @XmlElement(name = "NgaySL")
    public void setNgaySlString(String ngaySlString) {
        this.ngaySlString = ngaySlString;
    }
}
