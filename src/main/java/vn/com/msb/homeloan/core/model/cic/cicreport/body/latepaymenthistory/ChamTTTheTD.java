package vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class ChamTTTheTD {
    private Date ngayChamTt;
    private Double soTienChamTt;
    private Long soNgayChamTt;
    private String ngayChamTtString;
    public Date getNgayChamTt() {
        return ngayChamTt;
    }

    public void setNgayChamTt(Date ngayChamTt) {
        this.ngayChamTt = ngayChamTt;
    }

    public Double getSoTienChamTt() {
        return soTienChamTt;
    }

    @XmlElement(name = "SoTienChamTT")
    public void setSoTienChamTt(Double soTienChamTt) {
        this.soTienChamTt = soTienChamTt;
    }

    public Long getSoNgayChamTt() {
        return soNgayChamTt;
    }

    @XmlElement(name = "SoNgayChamTT")
    public void setSoNgayChamTt(Long soNgayChamTt) {
        this.soNgayChamTt = soNgayChamTt;
    }

    public String getNgayChamTtString() {
        return ngayChamTtString;
    }

    @XmlElement(name = "NgayChamTT")
    public void setNgayChamTtString(String ngayChamTtString) {
        this.ngayChamTtString = ngayChamTtString;
    }

}
