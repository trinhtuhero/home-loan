package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;

public class TTKH {
    private String maCIC;
    private String maCIF;
    private String tenKhachHang;
    private String soCMT;
    private String giayToKhac;
    private String soDangKyKD;
    private String maSoThue;

    public String getMaCIC() {
        return maCIC;
    }

    @XmlElement(name = "MaCIC")
    public void setMaCIC(String maCIC) {
        this.maCIC = maCIC;
    }

    public String getMaCIF() {
        return maCIF;
    }

    @XmlElement(name = "MaKH")
    public void setMaCIF(String maCIF) {
        this.maCIF = maCIF;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    @XmlElement(name = "TenKhachHang")
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoCMT() {
        return soCMT;
    }

    @XmlElement(name = "CMND-HC")
    public void setSoCMT(String soCMT) {
        this.soCMT = soCMT;
    }

    public String getGiayToKhac() {
        return giayToKhac;
    }

    @XmlElement(name = "GiayToKhac")
    public void setGiayToKhac(String giayToKhac) {
        this.giayToKhac = giayToKhac;
    }

    public String getSoDangKyKD() {
        return soDangKyKD;
    }

    @XmlElement(name = "DKKD")
    public void setSoDangKyKD(String soDangKyKD) {
        this.soDangKyKD = soDangKyKD;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    @XmlElement(name = "MST")
    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }


}
