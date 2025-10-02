package vn.com.msb.homeloan.core.model.cic.cicreport.body.debtsneedingattentionintwelvemonths;

import javax.xml.bind.annotation.XmlElement;

public class NoCanChuYTrong12Thang {

    private NoCanChuYChiTiet noCanChuYChiTiet;

    public NoCanChuYChiTiet getNoCanChuYChiTiet() {
        return noCanChuYChiTiet;
    }

    @XmlElement(name = "NoCanChuYChiTiet")
    public void setNoCanChuYChiTiet(NoCanChuYChiTiet noCanChuYChiTiet) {
        this.noCanChuYChiTiet = noCanChuYChiTiet;
    }
}
