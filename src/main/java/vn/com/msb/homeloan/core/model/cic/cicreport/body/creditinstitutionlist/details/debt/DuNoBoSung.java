package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.debt;

import javax.xml.bind.annotation.XmlElement;

public class DuNoBoSung {
    private Long noXauKhacVND;
    private Long noXauKhacUSD;
    private Long noTaiNHPTVND;
    private Long noTaiNHPTUSD;

    public Long getNoXauKhacVND() {
        return noXauKhacVND;
    }

    @XmlElement(name = "NoXauKhacVND")
    public void setNoXauKhacVND(Long noXauKhacVND) {
        this.noXauKhacVND = noXauKhacVND;
    }

    public Long getNoXauKhacUSD() {
        return noXauKhacUSD;
    }

    @XmlElement(name = "NoXauKhacUSD")
    public void setNoXauKhacUSD(Long noXauKhacUSD) {
        this.noXauKhacUSD = noXauKhacUSD;
    }

    public Long getNoTaiNHPTVND() {
        return noTaiNHPTVND;
    }

    @XmlElement(name = "NoTaiNHPTVND")
    public void setNoTaiNHPTVND(Long noTaiNHPTVND) {
        this.noTaiNHPTVND = noTaiNHPTVND;
    }

    public Long getNoTaiNHPTUSD() {
        return noTaiNHPTUSD;
    }

    @XmlElement(name = "NoTaiNHPTUSD")
    public void setNoTaiNHPTUSD(Long noTaiNHPTUSD) {
        this.noTaiNHPTUSD = noTaiNHPTUSD;
    }
}
