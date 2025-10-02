package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "TCTD", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance")
public class TCTD {
    private Date ngaySl;
    private String tenTctdPht;
    private Long hanMucTtd;
    private Long soTienPtt;
    private Long soNgayCtt;
    private Long soTienCtt;

    private String ngaySlString;

    public Date getNgaySl() {
        return ngaySl;
    }

    public void setNgaySl(Date ngaySl) {
        this.ngaySl = ngaySl;
    }

    public String getTenTctdPht() {
        return tenTctdPht;
    }

    @XmlElement(name = "TenTCTDPhatHanh")
    public void setTenTctdPht(String tenTctdPht) {
        this.tenTctdPht = tenTctdPht;
    }

    public Long getHanMucTtd() {
        return hanMucTtd;
    }

    @XmlElement(name = "HMTheTD")
    public void setHanMucTtd(Long hanMucTtd) {
        this.hanMucTtd = hanMucTtd;
    }

    public Long getSoTienPtt() {
        return soTienPtt;
    }

    @XmlElement(name = "SoTienPhaiThanhToan")
    public void setSoTienPtt(Long soTienPtt) {
        this.soTienPtt = soTienPtt;
    }

    public Long getSoNgayCtt() {
        return soNgayCtt;
    }

    @XmlElement(name = "SoNgayChamThanhToan")
    public void setSoNgayCtt(Long soNgayCtt) {
        this.soNgayCtt = soNgayCtt;
    }

    public Long getSoTienCtt() {
        return soTienCtt;
    }

    @XmlElement(name = "SoTienChamThanhToan")
    public void setSoTienCtt(Long soTienCtt) {
        this.soTienCtt = soTienCtt;
    }

    public String getNgaySlString() {
        return ngaySlString;
    }

    @XmlElement(name = "NgaySL")
    public void setNgaySlString(String ngaySlString) {
        this.ngaySlString = ngaySlString;
    }
}
