package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class TraiPhieu {
    private String tenTctd;
    private Long tongGiaTri;
    private Date ngaySl;
    private Integer nhomNo;
    private String ngayPhatHanhString;
    private String ngayDenHanString;
    private Date ngayPhatHanh;
    private Date ngayDenHan;

    public String getTenTctd() {
        return tenTctd;
    }

    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public Long getTongGiaTri() {
        return tongGiaTri;
    }

    public void setTongGiaTri(Long tongGiaTri) {
        this.tongGiaTri = tongGiaTri;
    }

    public Date getNgaySl() {
        return ngaySl;
    }

    public void setNgaySl(Date ngaySl) {
        this.ngaySl = ngaySl;
    }

    public Integer getNhomNo() {
        return nhomNo;
    }

    public void setNhomNo(Integer nhomNo) {
        this.nhomNo = nhomNo;
    }

    public String getNgayPhatHanhString() {
        return ngayPhatHanhString;
    }

    @XmlElement(name = "NgayPhatHanh")
    public void setNgayPhatHanhString(String ngayPhatHanhString) {
        this.ngayPhatHanhString = ngayPhatHanhString;
    }

    public String getNgayDenHanString() {
        return ngayDenHanString;
    }

    @XmlElement(name = "NgayDenHan")
    public void setNgayDenHanString(String ngayDenHanString) {
        this.ngayDenHanString = ngayDenHanString;
    }

    public Date getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(Date ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public Date getNgayDenHan() {
        return ngayDenHan;
    }

    public void setNgayDenHan(Date ngayDenHan) {
        this.ngayDenHan = ngayDenHan;
    }
}
