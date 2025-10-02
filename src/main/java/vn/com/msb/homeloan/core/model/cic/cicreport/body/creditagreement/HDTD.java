package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class HDTD {
    private List<ChiTiet> chiTietList;

    public List<ChiTiet> getChiTietList() {
        return chiTietList;
    }
    @XmlElement(name = "CHITIET")
    public void setChiTietList(List<ChiTiet> chiTietList) {
        this.chiTietList = chiTietList;
    }
}
