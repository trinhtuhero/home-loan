package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import java.util.Date;

public class CamKetNB {
    private String tenTctd;
    private Long giaTri;
    private Date ngaySl;
    private Integer nhomNo;
    private String loaiTien;

    public String getTenTctd() {
        return tenTctd;
    }

    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public Long getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(Long giaTri) {
        this.giaTri = giaTri;
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

    public String getLoaiTien() {
        return loaiTien;
    }

    public void setLoaiTien(String loaiTien) {
        this.loaiTien = loaiTien;
    }
}
