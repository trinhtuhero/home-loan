package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;

public class TCTDHoiTin {
    private Long maChiNhanh;
    private String diaChiTSC;
    private String tongGiamDoc;

    public Long getMaChiNhanh() {
        return maChiNhanh;
    }

    @XmlElement(name = "MaCN")
    public void setMaChiNhanh(Long maChiNhanh) {
        this.maChiNhanh = maChiNhanh;
    }

    public String getDiaChiTSC() {
        return diaChiTSC;
    }

    @XmlElement(name = "TruSoChinh-DiaChi")
    public void setDiaChiTSC(String diaChiTSC) {
        this.diaChiTSC = diaChiTSC;
    }

    public String getTongGiamDoc() {
        return tongGiamDoc;
    }

    @XmlElement(name = "TGD-DaiDienPL")
    public void setTongGiamDoc(String tongGiamDoc) {
        this.tongGiamDoc = tongGiamDoc;
    }

}
