package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import java.util.Date;

public class NoVAMCChiTiet {
    private String tenTctd;
    private Long duNoDaBanVnd;
    private Date ngaySl;
    private String ngaySlString;

    public String getTenTctd() {
        return tenTctd;
    }

    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public Long getDuNoDaBanVnd() {
        return duNoDaBanVnd;
    }

    public void setDuNoDaBanVnd(Long duNoDaBanVnd) {
        this.duNoDaBanVnd = duNoDaBanVnd;
    }

    public Date getNgaySl() {
        return ngaySl;
    }

    public void setNgaySl(Date ngaySl) {
        this.ngaySl = ngaySl;
    }

    public String getNgaySlString() {
        return ngaySlString;
    }

    public void setNgaySlString(String ngaySlString) {
        this.ngaySlString = ngaySlString;
    }
}
