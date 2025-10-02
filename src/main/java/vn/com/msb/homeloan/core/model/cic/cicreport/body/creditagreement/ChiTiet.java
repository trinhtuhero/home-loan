package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "ChiTiet", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement")
public class ChiTiet {
    private String tenTctd;
    private Date ngayKyHd;
    private Date ngayKetThucHd;
    private String ngayKetThucHdString;
    private String ngayKyHdString;
    public String getTenTctd() {
        return tenTctd;
    }
    @XmlElement(name = "TenTCTD")
    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }
    public Date getNgayKyHd() {
        return ngayKyHd;
    }
    public void setNgayKyHd(Date ngayKyHd) {
        this.ngayKyHd = ngayKyHd;
    }
    public Date getNgayKetThucHd() {
        return ngayKetThucHd;
    }
    public void setNgayKetThucHd(Date ngayKetThucHd) {
        this.ngayKetThucHd = ngayKetThucHd;
    }
    public String getNgayKetThucHdString() {
        return ngayKetThucHdString;
    }
    @XmlElement(name = "NGAYKT")
    public void setNgayKetThucHdString(String ngayKetThucHdString) {
        this.ngayKetThucHdString = ngayKetThucHdString;
    }
    public String getNgayKyHdString() {
        return ngayKyHdString;
    }
    @XmlElement(name = "NGAYKY")
    public void setNgayKyHdString(String ngayKyHdString) {
        this.ngayKyHdString = ngayKyHdString;
    }
}
