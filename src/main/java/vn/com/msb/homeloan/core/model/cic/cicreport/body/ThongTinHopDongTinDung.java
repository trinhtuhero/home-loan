package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class ThongTinHopDongTinDung {
    private String tenTctd;
    private Date ngayKyHd;
    private Date ngayKetThucHd;

    private String ngayKyHdString;

    private String ngayKetThucHdString;

    public String getTenTctd() {
        return tenTctd;
    }

    @XmlElement(name = "tenTctd")
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

    public String getNgayKyHdString() {
        return ngayKyHdString;
    }
    @XmlElement(name = "NgayKyHd")
    public void setNgayKyHdString(String ngayKyHdString) {
        this.ngayKyHdString = ngayKyHdString;
    }
    public String getNgayKetThucHdString() {
        return ngayKetThucHdString;
    }
    @XmlElement(name = "NgayKetThucHd")
    public void setNgayKetThucHdString(String ngayKetThucHdString) {
        this.ngayKetThucHdString = ngayKetThucHdString;
    }
}
