package vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "TCTD", namespace = "vn.com.msb.df.upl.customerportalservice.model.cicreport.body.twelvemonthsoutstanding")
public class TCTD {
    private String ngaySl;
    private Date ngayHoiTin;
    private String tenTctd;
    private Double t1vnd;
    private Double t2vnd;
    private Double t3vnd;
    private Double t4vnd;
    private Double t5vnd;
    private Double t6vnd;
    private Double t7vnd;
    private Double t8vnd;
    private Double t9vnd;
    private Double t10vnd;
    private Double t11vnd;
    private Double t12vnd;

    public String getNgaySl() {
        return ngaySl;
    }
    @XmlElement(name = "NgaySL")
    public void setNgaySl(String ngaySl) {
        this.ngaySl = ngaySl;
    }

    public Date getNgayHoiTin() {
        return ngayHoiTin;
    }
    @XmlElement(name = "NgayHoiTin")
    public void setNgayHoiTin(Date ngayHoiTin) {
        this.ngayHoiTin = ngayHoiTin;
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

    public Double getT2vnd() {
        return t2vnd;
    }
    @XmlElement(name = "T2VND")
    public void setT2vnd(Double t2vnd) {
        this.t2vnd = t2vnd;
    }

    public Double getT3vnd() {
        return t3vnd;
    }
    @XmlElement(name = "T3VND")
    public void setT3vnd(Double t3vnd) {
        this.t3vnd = t3vnd;
    }

    public Double getT4vnd() {
        return t4vnd;
    }

    @XmlElement(name = "T4VND")
    public void setT4vnd(Double t4vnd) {
        this.t4vnd = t4vnd;
    }

    public Double getT5vnd() {
        return t5vnd;
    }

    @XmlElement(name = "T5VND")
    public void setT5vnd(Double t5vnd) {
        this.t5vnd = t5vnd;
    }

    public Double getT6vnd() {
        return t6vnd;
    }

    @XmlElement(name = "T6VND")
    public void setT6vnd(Double t6vnd) {
        this.t6vnd = t6vnd;
    }

    public Double getT7vnd() {
        return t7vnd;
    }

    @XmlElement(name = "T7VND")
    public void setT7vnd(Double t7vnd) {
        this.t7vnd = t7vnd;
    }

    public Double getT8vnd() {
        return t8vnd;
    }

    @XmlElement(name = "T8VND")
    public void setT8vnd(Double t8vnd) {
        this.t8vnd = t8vnd;
    }

    public Double getT9vnd() {
        return t9vnd;
    }

    @XmlElement(name = "T9VND")
    public void setT9vnd(Double t9vnd) {
        this.t9vnd = t9vnd;
    }

    public Double getT10vnd() {
        return t10vnd;
    }

    @XmlElement(name = "T10VND")
    public void setT10vnd(Double t10vnd) {
        this.t10vnd = t10vnd;
    }

    public Double getT11vnd() {
        return t11vnd;
    }
    @XmlElement(name = "T11VND")
    public void setT11vnd(Double t11vnd) {
        this.t11vnd = t11vnd;
    }

    public Double getT12vnd() {
        return t12vnd;
    }

    @XmlElement(name = "T12VND")
    public void setT12vnd(Double t12vnd) {
        this.t12vnd = t12vnd;
    }
}
