package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class DanhSachTCTDTraCuu {
    private String tenTctd;
    private Date ngayTraCuu;
    private String ngayTraCuuString;

    public String getTenTctd() {
        return tenTctd;
    }

    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public Date getNgayTraCuu() {
        return ngayTraCuu;
    }


    public void setNgayTraCuu(Date ngayTraCuu) {
        this.ngayTraCuu = ngayTraCuu;
    }

    public String getNgayTraCuuString() {
        return ngayTraCuuString;
    }
    @XmlElement(name = "NgayTraCuu")
    public void setNgayTraCuuString(String ngayTraCuuString) {
        this.ngayTraCuuString = ngayTraCuuString;
    }
}
