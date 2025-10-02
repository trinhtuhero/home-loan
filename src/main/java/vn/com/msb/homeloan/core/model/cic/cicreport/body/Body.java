package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement.HDTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance.DuNoTheTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.DSTCTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionslookuptwelvemonths.TCTDTraCuu12Thang;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.debtsneedingattentionintwelvemonths.NoCanChuYTrong12Thang;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory.LichSuChamTTTheTD36Thang;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.NoXau36Thang;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding.DoNo12Thang;

import javax.xml.bind.annotation.XmlElement;

public class Body {
    private TTKH ttkh;
    private TCTDHoiTin tctdHoiTin;
    private TTChung ttChung;
    private DSTCTD dstctd;
    private DoNo12Thang doNo12Thang;
    private NoXau36Thang noXau36Thang;
    private NoCanChuYTrong12Thang noCanChuYTrong12Thang;
    private NoVAMC noVAMC;
    private HDTD hdtd;
    private TCTDTraCuu12Thang tctdTraCuu12Thang;
    private DuNoTheTD duNoTheTD;
    private DuNoTheTD12Thang duNoTheTD12Thang;
    private LichSuChamTTTheTD36Thang lichSuChamTTTheTD36Thang;
    private TTKhac ttKhac;
    private TraiPhieu traiPhieu;
    private CamKetNB camKetNB;
    public TraiPhieu getTraiPhieu() {
        return traiPhieu;
    }

    @XmlElement(name = "TRAIPHIEU")
    public void setTraiPhieu(TraiPhieu traiPhieu) {
        this.traiPhieu = traiPhieu;
    }

    public CamKetNB getCamKetNB() {
        return camKetNB;
    }

    @XmlElement(name = "CAMKETNB")
    public void setCamKetNB(CamKetNB camKetNB) {
        this.camKetNB = camKetNB;
    }

    public TTKH getTtkh() {
        return ttkh;
    }
    @XmlElement(name = "TTKH")
    public void setTtkh(TTKH ttkh) {
        this.ttkh = ttkh;
    }

    public TCTDHoiTin getTctdHoiTin() {
        return tctdHoiTin;
    }

    @XmlElement(name = "TCTDHoiTin")
    public void setTctdHoiTin(TCTDHoiTin tctdHoiTin) {
        this.tctdHoiTin = tctdHoiTin;
    }

    public TTChung getTtChung() {
        return ttChung;
    }

    @XmlElement(name = "TTChung")
    public void setTtChung(TTChung ttChung) {
        this.ttChung = ttChung;
    }

    public DSTCTD getDstctd() {
        return dstctd;
    }

    @XmlElement(name = "DSTCTD")
    public void setDstctd(DSTCTD dstctd) {
        this.dstctd = dstctd;
    }

    public DoNo12Thang getDoNo12Thang() {
        return doNo12Thang;
    }

    @XmlElement(name = "DoNo12Thang")
    public void setDoNo12Thang(DoNo12Thang doNo12Thang) {
        this.doNo12Thang = doNo12Thang;
    }

    public NoXau36Thang getNoXau36Thang() {
        return noXau36Thang;
    }

    @XmlElement(name = "NoXau36Thang")
    public void setNoXau36Thang(NoXau36Thang noXau36Thang) {
        this.noXau36Thang = noXau36Thang;
    }

    public NoCanChuYTrong12Thang getNoCanChuYTrong12Thang() {
        return noCanChuYTrong12Thang;
    }

    @XmlElement(name = "NoCanChuYTrong12Thang")
    public void setNoCanChuYTrong12Thang(NoCanChuYTrong12Thang noCanChuYTrong12Thang) {
        this.noCanChuYTrong12Thang = noCanChuYTrong12Thang;
    }

    public NoVAMC getNoVAMC() {
        return noVAMC;
    }

    @XmlElement(name = "NoVAMC")
    public void setNoVAMC(NoVAMC noVAMC) {
        this.noVAMC = noVAMC;
    }

    public HDTD getHdtd() {
        return hdtd;
    }

    @XmlElement(name = "HDTD")
    public void setHdtd(HDTD hdtd) {
        this.hdtd = hdtd;
    }

    public TCTDTraCuu12Thang getTctdTraCuu12Thang() {
        return tctdTraCuu12Thang;
    }

    @XmlElement(name = "TCTDTraCuu12Thang")
    public void setTctdTraCuu12Thang(TCTDTraCuu12Thang tctdTraCuu12Thang) {
        this.tctdTraCuu12Thang = tctdTraCuu12Thang;
    }

    public DuNoTheTD getDuNoTheTD() {
        return duNoTheTD;
    }

    @XmlElement(name = "DuNoTheTD")
    public void setDuNoTheTD(DuNoTheTD duNoTheTD) {
        this.duNoTheTD = duNoTheTD;
    }

    public DuNoTheTD12Thang getDuNoTheTD12Thang() {
        return duNoTheTD12Thang;
    }

    @XmlElement(name = "DuNoTheTD12Thang")
    public void setDuNoTheTD12Thang(DuNoTheTD12Thang duNoTheTD12Thang) {
        this.duNoTheTD12Thang = duNoTheTD12Thang;
    }

    public LichSuChamTTTheTD36Thang getLichSuChamTTTheTD36Thang() {
        return lichSuChamTTTheTD36Thang;
    }

    @XmlElement(name = "LichSuChamTTTheTD36Thang")
    public void setLichSuChamTTTheTD36Thang(LichSuChamTTTheTD36Thang lichSuChamTTTheTD36Thang) {
        this.lichSuChamTTTheTD36Thang = lichSuChamTTTheTD36Thang;
    }

    public TTKhac getTtKhac() {
        return ttKhac;
    }

    @XmlElement(name = "TTKHAC")
    public void setTtKhac(TTKhac ttKhac) {
        this.ttKhac = ttKhac;
    }
}
