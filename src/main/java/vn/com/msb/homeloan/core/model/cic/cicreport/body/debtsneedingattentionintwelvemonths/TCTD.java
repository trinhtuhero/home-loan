package vn.com.msb.homeloan.core.model.cic.cicreport.body.debtsneedingattentionintwelvemonths;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TCTD", namespace = "vn.com.msb.homeloan.core.model.cic.cicreport.body.debtsneedingattentionintwelvemonths")
public class TCTD {
    private String ngaySl;
    private String tenTctd;
    private Double t1vnd;
    private Long t2vnd;
    private Long t3vnd;
    private Long t4vnd;
    private Long t5vnd;
    private Long t6vnd;
    private Long t7vnd;
    private Long t8vnd;
    private Long t9vnd;
    private Long t10vnd;
    private Long t11vnd;
    private Long t12vnd;

    public String getNgaySl() {
        return ngaySl;
    }
    @XmlElement(name = "NgaySL")
    public void setNgaySl(String ngaySl) {
        this.ngaySl = ngaySl;
    }

    public String getTenTctd() {
        return tenTctd;
    }
    @XmlElement(name = "TenTCTD")
    public void setTenTctd(String tenTctd) {
        this.tenTctd = tenTctd;
    }

    public Double getT1vnd() {
        return t1vnd;
    }
    @XmlElement(name = "T1VND")
    public void setT1vnd(Double t1vnd) {
        this.t1vnd = t1vnd;
    }

    public Long getT2vnd() {
        return t2vnd;
    }
    @XmlElement(name = "T2VND")
    public void setT2vnd(Long t2vnd) {
        this.t2vnd = t2vnd;
    }

    public Long getT3vnd() {
        return t3vnd;
    }
    @XmlElement(name = "T3VND")
    public void setT3vnd(Long t3vnd) {
        this.t3vnd = t3vnd;
    }

    public Long getT4vnd() {
        return t4vnd;
    }

    @XmlElement(name = "T4VND")
    public void setT4vnd(Long t4vnd) {
        this.t4vnd = t4vnd;
    }

    public Long getT5vnd() {
        return t5vnd;
    }

    @XmlElement(name = "T5VND")
    public void setT5vnd(Long t5vnd) {
        this.t5vnd = t5vnd;
    }

    public Long getT6vnd() {
        return t6vnd;
    }

    @XmlElement(name = "T6VND")
    public void setT6vnd(Long t6vnd) {
        this.t6vnd = t6vnd;
    }

    public Long getT7vnd() {
        return t7vnd;
    }

    @XmlElement(name = "T7VND")
    public void setT7vnd(Long t7vnd) {
        this.t7vnd = t7vnd;
    }

    public Long getT8vnd() {
        return t8vnd;
    }

    @XmlElement(name = "T8VND")
    public void setT8vnd(Long t8vnd) {
        this.t8vnd = t8vnd;
    }

    public Long getT9vnd() {
        return t9vnd;
    }

    @XmlElement(name = "T9VND")
    public void setT9vnd(Long t9vnd) {
        this.t9vnd = t9vnd;
    }

    public Long getT10vnd() {
        return t10vnd;
    }

    @XmlElement(name = "T10VND")
    public void setT10vnd(Long t10vnd) {
        this.t10vnd = t10vnd;
    }

    public Long getT11vnd() {
        return t11vnd;
    }
    @XmlElement(name = "T11VND")
    public void setT11vnd(Long t11vnd) {
        this.t11vnd = t11vnd;
    }

    public Long getT12vnd() {
        return t12vnd;
    }

    @XmlElement(name = "T12VND")
    public void setT12vnd(Long t12vnd) {
        this.t12vnd = t12vnd;
    }
}
