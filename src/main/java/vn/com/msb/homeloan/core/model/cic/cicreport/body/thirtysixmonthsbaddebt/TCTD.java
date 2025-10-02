package vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "TCTD", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt")
public class TCTD {

    private String tenTctd;
    private String nhomNoCaoNhat;
    private String nhomNo;
    private Date npsnxcc;
    private String npsnxccString;
    private Long soTienVnd;
    private Long soTienUsd;
    private Date ngaySl;
    private String ngaySlString;

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

    public String getNhomNoCaoNhat() {
        return nhomNoCaoNhat;
    }

    @XmlElement(name = "NhomNoCaoNhat")
    public void setNhomNoCaoNhat(String nhomNoCaoNhat) {
        this.nhomNoCaoNhat = nhomNoCaoNhat;
    }

    public String getNhomNo() {
        return nhomNo;
    }

    @XmlElement(name = "NhomNo")
    public void setNhomNo(String nhomNo) {
        this.nhomNo = nhomNo;
    }

    public Date getNpsnxcc() {
        return npsnxcc;
    }

    public void setNpsnxcc(Date npsnxcc) {
        this.npsnxcc = npsnxcc;
    }

    public Long getSoTienVnd() {
        return soTienVnd;
    }

    @XmlElement(name = "SoTienVND")
    public void setSoTienVnd(Long soTienVnd) {
        this.soTienVnd = soTienVnd;
    }

    public Long getSoTienUsd() {
        return soTienUsd;
    }

    @XmlElement(name = "SoTienUSD")
    public void setSoTienUsd(Long soTienUsd) {
        this.soTienUsd = soTienUsd;
    }

    public String getNpsnxccString() {
        return npsnxccString;
    }

    @XmlElement(name = "NgayPhatSinhNoXauCuoiCung")
    public void setNpsnxccString(String npsnxccString) {
        this.npsnxccString = npsnxccString;
    }

    public String getNgaySlString() {
        return ngaySlString;
    }

    @XmlElement(name = "NgaySL")
    public void setNgaySlString(String ngaySlString) {
        this.ngaySlString = ngaySlString;
    }
}
