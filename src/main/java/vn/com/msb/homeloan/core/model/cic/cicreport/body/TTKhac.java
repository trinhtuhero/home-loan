package vn.com.msb.homeloan.core.model.cic.cicreport.body;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class TTKhac {
    private List<ThongTinHopDongTinDung> thongTinHopDongTinDungList;

    private List<DanhSachTCTDTraCuu> danhSachTCTDTraCuuList;

    public List<ThongTinHopDongTinDung> getThongTinHopDongTinDungList() {
        return thongTinHopDongTinDungList;
    }

    @XmlElement(name = "NoiDung", type = ThongTinHopDongTinDung.class)
    public void setThongTinHopDongTinDungList(List<ThongTinHopDongTinDung> thongTinHopDongTinDungList) {
        this.thongTinHopDongTinDungList = thongTinHopDongTinDungList;
    }

    public List<DanhSachTCTDTraCuu> getDanhSachTCTDTraCuuList() {
        return danhSachTCTDTraCuuList;
    }

    @XmlElement(name = "DanhSachTCTDTraCuu", type = DanhSachTCTDTraCuu.class)
    public void setDanhSachTCTDTraCuuList(List<DanhSachTCTDTraCuu> danhSachTCTDTraCuuList) {
        this.danhSachTCTDTraCuuList = danhSachTCTDTraCuuList;
    }
}
