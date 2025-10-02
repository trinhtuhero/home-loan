package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.*;
import org.springframework.util.ObjectUtils;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.model.cic.cicreport.CicTemplate;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.DanhSachTCTDTraCuu;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.NoVAMCChiTiet;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.TraiPhieu;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement.ChiTiet;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.TCTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.details.debt.*;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory.ChamTTTheTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListChamTT36ThangJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListDataJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListNoCanChuY12ThangJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.TTChungKhachHangJRXML;
import vn.com.msb.homeloan.core.util.DateUtils;

import java.text.ParseException;
import java.util.Date;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = {
        DateUtils.class
})
public interface CicReportMapper {
    @Mapping(target = "maCIC", source = "cicTemplate.body.ttkh.maCIC")
    @Mapping(target = "maCIF", source = "cicTemplate.body.ttkh.maCIF")
    @Mapping(target = "tenKhachHang", source = "cicTemplate.body.ttkh.tenKhachHang")
    @Mapping(target = "soCMT", source = "cicTemplate.body.ttkh.soCMT")
    @Mapping(target = "giayToKhac", source = "cicTemplate.body.ttkh.giayToKhac")
    @Mapping(target = "soDangKyKD", source = "cicTemplate.body.ttkh.soDangKyKD")
    @Mapping(target = "maSoThue", source = "cicTemplate.body.ttkh.maSoThue")
    @Mapping(target = "maChiNhanh", source = "cicTemplate.body.tctdHoiTin.maChiNhanh")
    @Mapping(target = "diaChiTSC", source = "cicTemplate.body.tctdHoiTin.diaChiTSC")
    @Mapping(target = "tongGiamDoc", source = "cicTemplate.body.tctdHoiTin.tongGiamDoc")
    @Mapping(target = "ghiChu", constant = "")
    TTChungKhachHangJRXML mapCustomerInformation(CicTemplate cicTemplate);

    @Mapping(target = "tenTctd", source = "cicTemplate.body.lichSuChamTTTheTD36Thang.tctd.tenTctd")
    @Mapping(target = "ngaySl", source = "cicTemplate.body.lichSuChamTTTheTD36Thang.tctd.ngaySlString", qualifiedByName = "convertNgaySl")
    @Mapping(target = "soTienChamTt", source = "chamTTTheTD.soTienChamTt")
    @Mapping(target = "soNgayChamTt", source = "chamTTTheTD.soNgayChamTt")
    @Mapping(target = "ngayChamTt", source = "chamTTTheTD.ngayChamTtString", qualifiedByName = "convertNgayChamTt")
    ListChamTT36ThangJRXML mapChamThanhToan36Thang(CicTemplate cicTemplate, ChamTTTheTD chamTTTheTD);

    @Named("convertNgaySl")
    default Date convertNgaySl(String ngaySlString) throws ParseException {
        return DateUtils.convertStringToDate(ngaySlString, Constants.DD_MM_YYYY_FLASH);
    }

    @Named("convertNgayChamTt")
    default Date convertNgayChamTt(String ngayChamTtString) throws ParseException {
        return DateUtils.convertStringToDate(ngayChamTtString, Constants.DD_MM_YYYY_FLASH);
    }

    @Mapping(target = "tenTctd", source = "tctd.ttChung.tenTctd")
    @Mapping(target = "maTcTD", source = "tctd.ttChung.maTcTD")
    @Mapping(target = "tsbdYes", source = "tctd.chiTiet.tsbd.tsbdYes")
    @Mapping(target = "tsbdNo", source = "tctd.chiTiet.tsbd.tsbdNo")
    @Mapping(target = "loaiDuNo", source = "tctd.ttChung.ngayBaoCaoGanNhat", qualifiedByName = "setLoaiDuNo")
    @Mapping(target = "duNoDuTCNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noDuTieuChuanVND")
    @Mapping(target = "duNoCCYNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noCanChuYVND")
    @Mapping(target = "duNoDuoiTCNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noDuoiTieuChuanVND")
    @Mapping(target = "duNoNNNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noNghiNgoVND")
    @Mapping(target = "duNoCKNMVNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noCoKhaNangMatVonVND")
    @Mapping(target = "duNoDuTCNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noDuTieuChuanUSD")
    @Mapping(target = "duNoCCYNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noCanChuYUSD")
    @Mapping(target = "duNoDuoiTCNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noDuoiTieuChuanUSD")
    @Mapping(target = "duNoNNNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noNghiNgoUSD")
    @Mapping(target = "duNoCKNMVNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.noCoKhaNangMatVonUSD")
    @Mapping(target = "duNoDuTCTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noDuTieuChuanVND")
    @Mapping(target = "duNoCCYTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noCanChuYVND")
    @Mapping(target = "duNoDuoiTCTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noDuoiTieuChuanVND")
    @Mapping(target = "duNoNNTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noNghiNgoVND")
    @Mapping(target = "duNoCKNMVTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noCoKhaNangMatVonVND")
    @Mapping(target = "duNoDuTCTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noDuTieuChuanUSD")
    @Mapping(target = "duNoCCYTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noCanChuYUSD")
    @Mapping(target = "duNoDuoiTCTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noDuoiTieuChuanUSD")
    @Mapping(target = "duNoNNTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noNghiNgoUSD")
    @Mapping(target = "duNoCKNMVTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.noCoKhaNangMatVonUSD")
    @Mapping(target = "duNoDuTCDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noDuTieuChuanVND")
    @Mapping(target = "duNoCCYDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noCanChuYVND")
    @Mapping(target = "duNoDuoiTCDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noDuoiTieuChuanVND")
    @Mapping(target = "duNoNNDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noNghiNgoVND")
    @Mapping(target = "duNoCKNMVDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noCoKhaNangMatVonVND")
    @Mapping(target = "duNoDuTCDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noDuTieuChuanUSD")
    @Mapping(target = "duNoCCYDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noCanChuYUSD")
    @Mapping(target = "duNoDuoiTCDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noDuoiTieuChuanUSD")
    @Mapping(target = "duNoNNDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noNghiNgoUSD")
    @Mapping(target = "duNoCKNMVDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.noCoKhaNangMatVonUSD")
    @Mapping(target = "duNoDuTCKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noDuTieuChuanVND")
    @Mapping(target = "duNoCCYKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noCanChuYVND")
    @Mapping(target = "duNoDuoiTCKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noDuoiTieuChuanVND")
    @Mapping(target = "duNoNNKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noNghiNgoVND")
    @Mapping(target = "duNoCKNMVKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noCoKhaNangMatVonVND")
    @Mapping(target = "duNoDuTCKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noDuTieuChuanUSD")
    @Mapping(target = "duNoCCYKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noCanChuYUSD")
    @Mapping(target = "duNoDuoiTCKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noDuoiTieuChuanUSD")
    @Mapping(target = "duNoNNKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noNghiNgoUSD")
    @Mapping(target = "duNoCKNMVKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.noCoKhaNangMatVonUSD")
    @Mapping(target = "noXauKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoBoSung.noXauKhacVND")
    @Mapping(target = "noXauKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoBoSung.noXauKhacUSD")
    @Mapping(target = "noTNHPTVND", source = "tctd.chiTiet.loaiDuNo.duNoBoSung.noTaiNHPTVND")
    @Mapping(target = "noTNHPTUSD", source = "tctd.chiTiet.loaiDuNo.duNoBoSung.noTaiNHPTUSD")
    @Mapping(target = "sumDuNoNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.sumDuNoNganVND")
    @Mapping(target = "sumDuNoNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan.sumDuNoNganUSD")
    @Mapping(target = "sumDuNoTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.sumDuNoTrungUSD")
    @Mapping(target = "sumDuNoTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan.sumDuNoTrungVND")
    @Mapping(target = "sumDuNoDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.sumDuNoDaiUSD")
    @Mapping(target = "sumDuNoDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan.sumDuNoDaiVND")
    @Mapping(target = "sumDuNoKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac.sumDuNoKhacUSD")
    @Mapping(target = "sumDuNoKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac.sumDuNoKhacVND")
    @Mapping(target = "vnd", source = "tctd.chiTiet.loaiDuNo.vnd")
    @Mapping(target = "usd", source = "tctd.chiTiet.loaiDuNo.usd")
    ListDataJRXML mapListData(TCTD tctd);

    @Named("setLoaiDuNo")
    default String setLoaiDuNo(String ngayBaoCaoGanNhat) {
        String dateStr = DateUtils.convertDateToString(new Date(), Constants.DD_MM_YYYY_FLASH);
        return "Ngày báo cáo gần nhất: " + dateStr;
    }

    @Mapping(target = "sumDuNoNganVND", source = "tctd.chiTiet.loaiDuNo.duNoNganHan", qualifiedByName = "tongDuNoNganVND")
    void sumDuNoNganVND(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoNganUSD", source = "tctd.chiTiet.loaiDuNo.duNoNganHan", qualifiedByName = "tongDuNoNganUSD")
    void sumDuNoNganUSD(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoTrungUSD", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan", qualifiedByName = "tongDuNoTrungUSD")
    void sumDuNoTrungUSD(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoTrungVND", source = "tctd.chiTiet.loaiDuNo.duNoTrungHan", qualifiedByName = "tongDuNoTrungVND")
    void sumDuNoTrungVND(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoDaiUSD", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan", qualifiedByName = "tongDuNoDaiUSD")
    void sumDuNoDaiUSD(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoDaiVND", source = "tctd.chiTiet.loaiDuNo.duNoDaiHan", qualifiedByName = "tongDuNoDaiVND")
    void sumDuNoDaiVND(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoKhacUSD", source = "tctd.chiTiet.loaiDuNo.duNoKhac", qualifiedByName = "tongDuNoKhacUSD")
    void sumDuNoKhacUSD(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "sumDuNoKhacVND", source = "tctd.chiTiet.loaiDuNo.duNoKhac", qualifiedByName = "tongDuNoKhacVND")
    void sumDuNoKhacVND(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "vnd", source = "tctd.chiTiet.loaiDuNo", qualifiedByName = "tongVnd")
    void sumVnd(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Mapping(target = "usd", source = "tctd.chiTiet.loaiDuNo", qualifiedByName = "tongUsd")
    void sumUsd(@MappingTarget ListDataJRXML listDataJRXML, TCTD tctd);

    @Named("tongDuNoNganVND")
    default Long tongDuNoNganVND(DuNoNganHan duNoNganHan) {
        return duNoNganHan.getNoDuTieuChuanVND() +
          duNoNganHan.getNoCanChuYVND() +
          duNoNganHan.getNoDuoiTieuChuanVND() +
          duNoNganHan.getNoNghiNgoVND() +
          duNoNganHan.getNoCoKhaNangMatVonVND();
    }

    @Named("tongDuNoNganUSD")
    default Long tongDuNoNganUSD(DuNoNganHan duNoNganHan) {
        return duNoNganHan.getNoDuTieuChuanUSD() +
          duNoNganHan.getNoCanChuYUSD() +
          duNoNganHan.getNoDuoiTieuChuanUSD() +
          duNoNganHan.getNoNghiNgoUSD() +
          duNoNganHan.getNoCoKhaNangMatVonUSD();
    }

    @Named("tongDuNoTrungUSD")
    default Long tongDuNoTrungUSD(DuNoTrungHan duNoTrungHan) {
        return duNoTrungHan.getNoDuTieuChuanUSD() +
          duNoTrungHan.getNoCanChuYUSD() +
          duNoTrungHan.getNoDuoiTieuChuanUSD() +
          duNoTrungHan.getNoNghiNgoUSD() +
          duNoTrungHan.getNoCoKhaNangMatVonUSD();
    }

    @Named("tongDuNoTrungVND")
    default Long tongDuNoTrungVND(DuNoTrungHan duNoTrungHan) {
        return duNoTrungHan.getNoDuTieuChuanVND() +
          duNoTrungHan.getNoCanChuYVND() +
          duNoTrungHan.getNoDuoiTieuChuanVND() +
          duNoTrungHan.getNoNghiNgoVND() +
          duNoTrungHan.getNoCoKhaNangMatVonVND();
    }

    @Named("tongDuNoDaiUSD")
    default Long tongDuNoDaiUSD(DuNoDaiHan duNoDaiHan) {
        return duNoDaiHan.getNoDuTieuChuanUSD() +
          duNoDaiHan.getNoCanChuYUSD() +
          duNoDaiHan.getNoDuoiTieuChuanUSD() +
          duNoDaiHan.getNoNghiNgoUSD() +
          duNoDaiHan.getNoCoKhaNangMatVonUSD();
    }

    @Named("tongDuNoDaiVND")
    default Long tongDuNoDaiVND(DuNoDaiHan duNoDaiHan) {
        return duNoDaiHan.getNoDuTieuChuanVND() +
          duNoDaiHan.getNoCanChuYVND() +
          duNoDaiHan.getNoDuoiTieuChuanVND() +
          duNoDaiHan.getNoNghiNgoVND() +
          duNoDaiHan.getNoCoKhaNangMatVonVND();
    }

    @Named("tongDuNoKhacUSD")
    default Long tongDuNoKhacUSD(DuNoKhac duNoKhac) {
        return duNoKhac.getNoDuTieuChuanUSD() +
          duNoKhac.getNoCanChuYUSD() +
          duNoKhac.getNoDuoiTieuChuanUSD() +
          duNoKhac.getNoNghiNgoUSD() +
          duNoKhac.getNoCoKhaNangMatVonUSD();
    }

    @Named("tongDuNoKhacVND")
    default Long tongDuNoKhacVND(DuNoKhac duNoKhac) {
        return duNoKhac.getNoDuTieuChuanVND() +
          duNoKhac.getNoCanChuYVND() +
          duNoKhac.getNoDuoiTieuChuanVND() +
          duNoKhac.getNoNghiNgoVND() +
          duNoKhac.getNoCoKhaNangMatVonVND();
    }

    @Named("tongVnd")
    default Long tongVnd(DuNo duNo) {
        return duNo.getDuNoNganHan().getNoDuTieuChuanVND()
          + duNo.getDuNoNganHan().getNoCanChuYVND()
          + duNo.getDuNoNganHan().getNoDuoiTieuChuanVND()
          + duNo.getDuNoNganHan().getNoNghiNgoVND()
          + duNo.getDuNoNganHan().getNoCoKhaNangMatVonVND()
          + duNo.getDuNoTrungHan().getNoDuTieuChuanVND()
          + duNo.getDuNoTrungHan().getNoCanChuYVND()
          + duNo.getDuNoTrungHan().getNoDuoiTieuChuanVND()
          + duNo.getDuNoTrungHan().getNoNghiNgoVND()
          + duNo.getDuNoTrungHan().getNoCoKhaNangMatVonVND()
          + duNo.getDuNoDaiHan().getNoDuTieuChuanVND()
          + duNo.getDuNoDaiHan().getNoCanChuYVND()
          + duNo.getDuNoDaiHan().getNoDuoiTieuChuanVND()
          + duNo.getDuNoDaiHan().getNoNghiNgoVND()
          + duNo.getDuNoDaiHan().getNoCoKhaNangMatVonVND()
          + duNo.getDuNoKhac().getNoDuTieuChuanVND()
          + duNo.getDuNoKhac().getNoCanChuYVND()
          + duNo.getDuNoKhac().getNoDuoiTieuChuanVND()
          + duNo.getDuNoKhac().getNoNghiNgoVND()
          + duNo.getDuNoKhac().getNoCoKhaNangMatVonVND();
    }

    @Named("tongUsd")
    default Long tongUsd(DuNo duNo) {
        return duNo.getDuNoNganHan().getNoDuTieuChuanUSD()
          + duNo.getDuNoNganHan().getNoCanChuYUSD()
          + duNo.getDuNoNganHan().getNoDuoiTieuChuanUSD()
          + duNo.getDuNoNganHan().getNoNghiNgoUSD()
          + duNo.getDuNoNganHan().getNoCoKhaNangMatVonUSD()
          + duNo.getDuNoTrungHan().getNoDuTieuChuanUSD()
          + duNo.getDuNoTrungHan().getNoCanChuYUSD()
          + duNo.getDuNoTrungHan().getNoDuoiTieuChuanUSD()
          + duNo.getDuNoTrungHan().getNoNghiNgoUSD()
          + duNo.getDuNoTrungHan().getNoCoKhaNangMatVonUSD()
          + duNo.getDuNoDaiHan().getNoDuTieuChuanUSD()
          + duNo.getDuNoDaiHan().getNoCanChuYUSD()
          + duNo.getDuNoDaiHan().getNoDuoiTieuChuanUSD()
          + duNo.getDuNoDaiHan().getNoNghiNgoUSD()
          + duNo.getDuNoDaiHan().getNoCoKhaNangMatVonUSD()
          + duNo.getDuNoKhac().getNoDuTieuChuanUSD()
          + duNo.getDuNoKhac().getNoCanChuYUSD()
          + duNo.getDuNoKhac().getNoDuoiTieuChuanUSD()
          + duNo.getDuNoKhac().getNoNghiNgoUSD()
          + duNo.getDuNoKhac().getNoCoKhaNangMatVonUSD();
    }

    @Mapping(target = "npsnxcc", source = "tctdInput.npsnxccString", qualifiedByName = "convertNgayPsnxcc")
    @Mapping(target = "ngaySl", source = "tctdInput.ngaySlString", qualifiedByName = "convertNgaySl36T")
    void mapListNoXau36T(@MappingTarget vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.TCTD tctd, vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.TCTD tctdInput);

    @Named("convertNgayPsnxcc")
    default Date convertNgayPsnxcc(String npsnxccString) throws ParseException {
        return DateUtils.convertStringToDate(npsnxccString, Constants.DD_MM_YYYY_FLASH);
    }

    @Named("convertNgaySl36T")
    default Date convertNgaySl36T(String ngaySlString) throws ParseException {
        return DateUtils.convertStringToDate(ngaySlString, Constants.DD_MM_YYYY_FLASH);
    }

    @Mapping(target = "ngayHoiTin", source = "cicTemplate.header.thoiGian", qualifiedByName = "convertNgayHoiTin")
    @Mapping(target = "tenTctd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.tenTctd")
    @Mapping(target = "t1vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t1vnd")
    @Mapping(target = "t2vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t2vnd")
    @Mapping(target = "t3vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t3vnd")
    @Mapping(target = "t4vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t4vnd")
    @Mapping(target = "t5vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t5vnd")
    @Mapping(target = "t6vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t6vnd")
    @Mapping(target = "t7vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t7vnd")
    @Mapping(target = "t8vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t8vnd")
    @Mapping(target = "t9vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t9vnd")
    @Mapping(target = "t10vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t10vnd")
    @Mapping(target = "t11vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t11vnd")
    @Mapping(target = "t12vnd", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.t12vnd")
    @Mapping(target = "ngaySl", source = "cicTemplate.body.noCanChuYTrong12Thang.noCanChuYChiTiet.tctd.ngaySl")
    ListNoCanChuY12ThangJRXML mapListNoCanChuY12T(CicTemplate cicTemplate);

    @Named("convertNgayHoiTin")
    default Date convertNgayHoiTin(String thoiGian) throws ParseException {
        return DateUtils.convertStringToDate(thoiGian, Constants.DD_MM_YYYY_FLASH);
    }

    @Mapping(target = "ngaySl", source = "noVAMCChiTietInput.ngaySlString", qualifiedByName = "convertNgaySlNoVAMC")
    void mapNoVAMCChiTiet(@MappingTarget NoVAMCChiTiet noVAMCChiTiet, NoVAMCChiTiet noVAMCChiTietInput);

    @Named("convertNgaySlNoVAMC")
    default Date convertNgaySlNoVAMC(String ngaySlString) throws ParseException {
        return DateUtils.convertStringToDate(ngaySlString, Constants.DD_MM_YYYY_FLASH);
    }

    @Mapping(target = "ngayDenHan", source = "traiPhieuInput.ngayDenHanString", qualifiedByName = "convertNgayDenHan")
    @Mapping(target = "ngayPhatHanh", source = "traiPhieuInput.ngayPhatHanhString", qualifiedByName = "convertNgayPhatHanh")
    @Mapping(target = "ngaySl", source = "traiPhieuInput.ngaySl", qualifiedByName = "convertSlTraiPhieu")
    void mapTraiPhieu(@MappingTarget TraiPhieu traiPhieu, TraiPhieu traiPhieuInput);

    @Named("convertNgayDenHan")
    default Date convertNgayDenHan(String ngayDenHanString) throws ParseException {
        return DateUtils.convertStringToDate(ngayDenHanString, Constants.DD_MM_YYYY_FLASH);
    }

    @Named("convertNgayPhatHanh")
    default Date convertNgayPhatHanh(String ngayPhatHanhString) throws ParseException {
        return DateUtils.convertStringToDate(ngayPhatHanhString, Constants.DD_MM_YYYY_FLASH);
    }

    @Named("convertSlTraiPhieu")
    default Date convertSlTraiPhieu(Date ngaySl) {
        return new Date();
    }

    @Mapping(target = "ngayKyHd", source = "chiTietInput.ngayKyHdString", qualifiedByName = "convertNgayKyHd")
    @Mapping(target = "ngayKetThucHd", source = "chiTietInput.ngayKetThucHdString", qualifiedByName = "convertNgayKetThucHd")
    void mapListHopDongTinDung(@MappingTarget ChiTiet chiTiet, ChiTiet chiTietInput);

    @Named("convertNgayKyHd")
    default Date convertNgayKyHd(String ngayKyHdString) throws ParseException {
        if (!ObjectUtils.isEmpty(ngayKyHdString)) {
            return DateUtils.convertStringToDate(ngayKyHdString, Constants.DD_MM_YYYY_FLASH);
        } else {
            return null;
        }
    }

    @Named("convertNgayKetThucHd")
    default Date convertNgayKetThucHd(String ngayKetThucHdString) throws ParseException {
        if (!ObjectUtils.isEmpty(ngayKetThucHdString)) {
            return DateUtils.convertStringToDate(ngayKetThucHdString, Constants.DD_MM_YYYY_FLASH);
        } else {
            return null;
        }
    }

    @Mapping(target = "ngaySl", source = "tctdInput.ngaySlString", qualifiedByName = "convertNgaySlListTheTD")
    void mapListTheTD(@MappingTarget vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance.TCTD tctd, vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance.TCTD tctdInput);

    @Named("convertNgaySlListTheTD")
    default Date convertNgaySlListTheTD(String ngaySlString) throws ParseException {
        return DateUtils.convertStringToDate(ngaySlString, Constants.DD_MM_YYYY_FLASH);
    }

    @Mapping(target = "ngayTraCuu", source = "danhSachTCTDTraCuuInput.ngayTraCuuString", qualifiedByName = "convertNgayTraCuu")
    void mapListTCTDTT(@MappingTarget DanhSachTCTDTraCuu danhSachTCTDTraCuu, DanhSachTCTDTraCuu danhSachTCTDTraCuuInput);

    @Named("convertNgayTraCuu")
    default Date convertNgayTraCuu(String ngayTraCuuString) throws ParseException {
        return DateUtils.convertStringToDate(ngayTraCuuString, Constants.DD_MM_YYYY_FLASH);
    }

}
