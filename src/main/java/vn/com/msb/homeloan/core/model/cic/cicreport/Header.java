package vn.com.msb.homeloan.core.model.cic.cicreport;

import javax.xml.bind.annotation.XmlElement;

public class Header {
    private String maYeuCauHoiTin;
    private String userID;
    private Long maTCTD;
    private String sanPham;
    private String thoiGian;

    public String getMaYeuCauHoiTin() {
        return maYeuCauHoiTin;
    }

    @XmlElement(name = "MaYeuCauHoiTin")
    public void setMaYeuCauHoiTin(String maYeuCauHoiTin) {
        this.maYeuCauHoiTin = maYeuCauHoiTin;
    }

    public String getUserID() {
        return userID;
    }

    @XmlElement(name = "UserID")
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getMaTCTD() {
        return maTCTD;
    }

    @XmlElement(name = "MaTCTD")
    public void setMaTCTD(Long maTCTD) {
        this.maTCTD = maTCTD;
    }

    public String getSanPham() {
        return sanPham;
    }

    @XmlElement(name = "SanPham")
    public void setSanPham(String sanPham) {
        this.sanPham = sanPham;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    @XmlElement(name = "ThoiGian")
    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

}
