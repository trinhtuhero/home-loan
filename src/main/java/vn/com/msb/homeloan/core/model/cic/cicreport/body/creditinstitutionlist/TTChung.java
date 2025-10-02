package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "TTChung", namespace = "vn.com.msb.homeloan.core.model.cic.CICReport.Body.DSTCTD")
public class TTChung {
    private Date ngaySL;

    private String tenTctd;

    private String maTcTD;

    private String ngayBaoCaoGanNhat;

    public String getNgayBaoCaoGanNhat() {
        return ngayBaoCaoGanNhat;
    }

    public void setNgayBaoCaoGanNhat(String ngayBaoCaoGanNhat) {
        this.ngayBaoCaoGanNhat = ngayBaoCaoGanNhat;
    }

    public Date getNgaySL() {
        return ngaySL;
    }

    @XmlElement(name = "NgaySL")
    public void setNgaySL(Date ngaySL) {
        this.ngaySL = ngaySL;
    }

    public String getTenTctd() {
        return tenTctd;
    }

    @XmlElement(name = "TenTCTD")
    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public String getMaTcTD() {
        return maTcTD;
    }

    @XmlElement(name = "MaTCTD")
    public void setMaTcTD(String maTcTD) {
        this.maTcTD = maTcTD;
    }
}