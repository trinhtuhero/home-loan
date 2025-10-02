package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.debt;

import javax.xml.bind.annotation.XmlElement;

public class DuNoDaiHan{
    private Long noDuTieuChuanVND;

    private Long noCanChuYVND;

    private Long noDuoiTieuChuanVND;

    private Long noNghiNgoVND;

    private Long noCoKhaNangMatVonVND;

    private Long noDuTieuChuanUSD;

    private Long noCanChuYUSD;

    private Long noDuoiTieuChuanUSD;

    private Long noNghiNgoUSD;

    private Long noCoKhaNangMatVonUSD;

    private Long sumDuNoDaiVND;

    private Long sumDuNoDaiUSD;

    public Long getSumDuNoDaiVND() {
        return sumDuNoDaiVND;
    }

    public void setSumDuNoDaiVND(Long sumDuNoDaiVND) {
        this.sumDuNoDaiVND = sumDuNoDaiVND;
    }

    public Long getSumDuNoDaiUSD() {
        return sumDuNoDaiUSD;
    }

    public void setSumDuNoDaiUSD(Long sumDuNoDaiUSD) {
        this.sumDuNoDaiUSD = sumDuNoDaiUSD;
    }

    public Long getNoDuTieuChuanVND() {
        return noDuTieuChuanVND;
    }

    @XmlElement(name = "NoDuTieuChuanVND")
    public void setNoDuTieuChuanVND(Long noDuTieuChuanVND) {
        this.noDuTieuChuanVND = noDuTieuChuanVND;
    }

    public Long getNoCanChuYVND() {
        return noCanChuYVND;
    }

    @XmlElement(name = "NoCanChuYVND")
    public void setNoCanChuYVND(Long noCanChuYVND) {
        this.noCanChuYVND = noCanChuYVND;
    }

    public Long getNoDuoiTieuChuanVND() {
        return noDuoiTieuChuanVND;
    }
    @XmlElement(name = "NoDuoiTieuChuanVND")
    public void setNoDuoiTieuChuanVND(Long noDuoiTieuChuanVND) {
        this.noDuoiTieuChuanVND = noDuoiTieuChuanVND;
    }

    public Long getNoNghiNgoVND() {
        return noNghiNgoVND;
    }
    @XmlElement(name = "NoNghiNgoVND")
    public void setNoNghiNgoVND(Long noNghiNgoVND) {
        this.noNghiNgoVND = noNghiNgoVND;
    }

    public Long getNoCoKhaNangMatVonVND() {
        return noCoKhaNangMatVonVND;
    }
    @XmlElement(name = "NoCoKhaNangMatVonVND")
    public void setNoCoKhaNangMatVonVND(Long noCoKhaNangMatVonVND) {
        this.noCoKhaNangMatVonVND = noCoKhaNangMatVonVND;
    }

    public Long getNoDuTieuChuanUSD() {
        return noDuTieuChuanUSD;
    }
    @XmlElement(name = "NoDuTieuChuanUSD")
    public void setNoDuTieuChuanUSD(Long noDuTieuChuanUSD) {
        this.noDuTieuChuanUSD = noDuTieuChuanUSD;
    }

    public Long getNoCanChuYUSD() {
        return noCanChuYUSD;
    }
    @XmlElement(name = "NoCanChuYUSD")
    public void setNoCanChuYUSD(Long noCanChuYUSD) {
        this.noCanChuYUSD = noCanChuYUSD;
    }

    public Long getNoDuoiTieuChuanUSD() {
        return noDuoiTieuChuanUSD;
    }
    @XmlElement(name = "NoDuoiTieuChuanUSD")
    public void setNoDuoiTieuChuanUSD(Long noDuoiTieuChuanUSD) {
        this.noDuoiTieuChuanUSD = noDuoiTieuChuanUSD;
    }

    public Long getNoNghiNgoUSD() {
        return noNghiNgoUSD;
    }
    @XmlElement(name = "NoNghiNgoUSD")
    public void setNoNghiNgoUSD(Long noNghiNgoUSD) {
        this.noNghiNgoUSD = noNghiNgoUSD;
    }

    public Long getNoCoKhaNangMatVonUSD() {
        return noCoKhaNangMatVonUSD;
    }
    @XmlElement(name = "NoCoKhaNangMatVonUSD")
    public void setNoCoKhaNangMatVonUSD(Long noCoKhaNangMatVonUSD) {
        this.noCoKhaNangMatVonUSD = noCoKhaNangMatVonUSD;
    }
}
