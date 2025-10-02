package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.debt;

import javax.xml.bind.annotation.XmlElement;

public class DuNo {
    private DuNoNganHan duNoNganHan;
    private DuNoTrungHan duNoTrungHan;
    private DuNoDaiHan duNoDaiHan;
    private DuNoKhac duNoKhac;
    private DuNoBoSung duNoBoSung;
    private Long vnd;
    private Long usd;

    public Long getVnd() {
        return vnd;
    }

    public void setVnd(Long vnd) {
        this.vnd = vnd;
    }

    public Long getUsd() {
        return usd;
    }

    public void setUsd(Long usd) {
        this.usd = usd;
    }

    public DuNoNganHan getDuNoNganHan() {
        return duNoNganHan;
    }
    @XmlElement(name = "DuNoNganHan", type = DuNoNganHan.class)
    public void setDuNoNganHan(DuNoNganHan duNoNganHan) {
        this.duNoNganHan = duNoNganHan;
    }

    public DuNoTrungHan getDuNoTrungHan() {
        return duNoTrungHan;
    }
    @XmlElement(name = "DuNoTrungHan", type = DuNoTrungHan.class)
    public void setDuNoTrungHan(DuNoTrungHan duNoTrungHan) {
        this.duNoTrungHan = duNoTrungHan;
    }

    public DuNoDaiHan getDuNoDaiHan() {
        return duNoDaiHan;
    }
    @XmlElement(name = "DuNoDaiHan", type = DuNoDaiHan.class)
    public void setDuNoDaiHan(DuNoDaiHan duNoDaiHan) {
        this.duNoDaiHan = duNoDaiHan;
    }

    public DuNoKhac getDuNoKhac() {
        return duNoKhac;
    }
    @XmlElement(name = "DuNoKhac", type = DuNoKhac.class)
    public void setDuNoKhac(DuNoKhac duNoKhac) {
        this.duNoKhac = duNoKhac;
    }

    public DuNoBoSung getDuNoBoSung() {
        return duNoBoSung;
    }
    @XmlElement(name = "DuNoBoSung", type = DuNoBoSung.class)
    public void setDuNoBoSung(DuNoBoSung duNoBoSung) {
        this.duNoBoSung = duNoBoSung;
    }
}
