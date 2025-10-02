package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.entity.CicItemEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.cic.cicreport.CicTemplate;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.*;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditagreement.ChiTiet;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist.TCTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.body.latepaymenthistory.ChamTTTheTD;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListChamTT36ThangJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListDataJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.ListNoCanChuY12ThangJRXML;
import vn.com.msb.homeloan.core.model.cic.cicreport.objectjrxml.TTChungKhachHangJRXML;
import vn.com.msb.homeloan.core.model.mapper.CicReportMapper;
import vn.com.msb.homeloan.core.repository.CicItemRepository;
import vn.com.msb.homeloan.core.service.PDFGenerationService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class PDFGenerationServiceImpl implements PDFGenerationService {

    private final CicReportMapper cicReportMapper;
    private final CicItemRepository cicItemRepository;

    @Override
    public String convertToPDF(String cicId, String identityCard) {
        try {
            CicTemplate cicTemplate = stringXMLToObject(cicId, identityCard);
            Map<String, Object> parameter = new HashMap<>();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(Constants.DD_MM_YYYY_FLASH);
            String strDate = formatter.format(date);
            parameter.put("msgNgayCapNhat", strDate);
            parameter.put("message", "");
            parameter.put("messageNoXau36t", "");
            parameter.put("messageDuNoVay12t", "");
            parameter.put("messageNoVAMC", "");
            parameter.put("messageTheTD", "");
            parameter.put("messageChamTT36t", "");
            parameter.put("messageTheTD12t", "");
            parameter.put("messageDauTuTraiPhieu", "");
            parameter.put("messageCamKetNgoaiBang", "");
            parameter.put("messageHopDongTinDung", "");
            parameter.put("messageTCTDTT", "");
            parameter.put("messageNoCanCY12t", "");
            parameter.put("note", "");
            parameter.put("note1", "");
            parameter.put("note2", "");
            parameter.put("note3", "");
            parameter.put("sercurity", "No security");
            parameter.put("ngayHoiTin", cicTemplate.getHeader().getThoiGian());
            parameter.put("ngayTraTin", strDate);
            parameter.put("ngayInBC", strDate);
            parameter.put("ngayCapNhat", strDate);
            parameter.put("cus", setTTChungKhachHang(cicTemplate));
            parameter.put("list12Thang", setList12Thang(cicTemplate));
            parameter.put("lstChamTT36t", setListChamTT36T(cicTemplate)); //table 8
            parameter.put("lstDuNoVay12t",
                          cicTemplate.getBody().getDoNo12Thang().getDuNoChiTiet().getTctdList()); //table 2
            parameter.put("lstData", setListData(cicTemplate)); //table 1
            parameter.put("lstNoXau36t", setListNoXau36T(cicTemplate)); //table 3
            parameter.put("lstNoCanCY12t", setNoCanChuY12T(cicTemplate)); //table 4
            parameter.put("lstNoVAMC", setNoVAMC(cicTemplate)); //table 5
            parameter.put("lstTheTD", setListTheTD(cicTemplate)); //table 6
            parameter.put("lstTheTD12t", setListTheTD12Thang(cicTemplate)); //table 7
            parameter.put("lstDauTuTraiPhieu", setListTraiPhieu(cicTemplate)); //table 9
            parameter.put("lstCamKetNgoaiBang", setListCamKetNB(cicTemplate)); //table 10
            parameter.put("lstHopDongTinDung", setListHopDongTinDung(cicTemplate)); //table 11
            parameter.put("lstTCTDTT", setListTCTDTT(cicTemplate)); //table 12
            try (InputStream in = getClass().getResourceAsStream("/jrxml/rptTNPN2.jrxml")) {
                JasperReport jasperReport = JasperCompileManager.compileReport(in);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(setOutput(cicTemplate));
                exporter.setConfiguration(new SimplePdfExporterConfiguration());
                exporter.exportReport();
                return setFileName(cicTemplate);
            }
        } catch (Exception exception) {
            log.error("Export file cic pdf exception ", exception.getMessage());
            throw new ApplicationException(500, "error.download_file");
        }
    }

    private OutputStreamExporterOutput setOutput(CicTemplate cicTemplate) {
        return new SimpleOutputStreamExporterOutput(setFileName(cicTemplate));
    }

    private String setFileName(CicTemplate cicTemplate) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter.ofPattern(Constants.MM_DD_YYYY_FLASH, Locale.ENGLISH).format(ldt);
        String substring = ldt.toString().replace("-", "").substring(0, 8);
        return "CIC_" + cicTemplate.getBody().getTtkh().getSoCMT() + "_" +
               cicTemplate.getBody().getTtkh().getTenKhachHang() + "_" + substring + ".pdf";
    }

    private CicTemplate stringXMLToObject(String cicId, String identityCard) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CicTemplate.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        CicItemEntity cicItem = cicItemRepository.findByCicIdAndIdentityCard(cicId,identityCard);
        return (CicTemplate) jaxbUnmarshaller.unmarshal(new StringReader(cicItem.getCicContent()));
    }

    private TTChungKhachHangJRXML setTTChungKhachHang(CicTemplate cicTemplate) {
        return cicReportMapper.mapCustomerInformation(cicTemplate);
    }

    private List<String> setList12Thang(CicTemplate cicTemplate) throws ParseException {
        String strDate = cicTemplate.getHeader().getThoiGian();
        List<String> allDates = new ArrayList<>();
        SimpleDateFormat monthDate = new SimpleDateFormat(Constants.DD_MM_YYYY_FLASH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate.parse(strDate));
        for (int i = 0; i < 12; i++) {
            cal.add(Calendar.MONTH, -1);
            String monthName = monthDate.format(cal.getTime());
            allDates.add("T" + monthName.substring(3));
        }
        return allDates;
    }

    private List<ListChamTT36ThangJRXML> setListChamTT36T(CicTemplate cicTemplate) {
        List<ListChamTT36ThangJRXML> listChamTT36TListJRXML = new ArrayList<>();
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getLichSuChamTTTheTD36Thang().getTctd())) {
            return null;
        } else {
            for (ChamTTTheTD chamTTTheTD : cicTemplate.getBody()
                                                      .getLichSuChamTTTheTD36Thang()
                                                      .getTctd()
                                                      .getDsChamTTTheTD()
                                                      .getChamTTTheTDList()) {
                listChamTT36TListJRXML.add(cicReportMapper.mapChamThanhToan36Thang(cicTemplate, chamTTTheTD));
            }
            return listChamTT36TListJRXML;
        }
    }

    private List<ListNoCanChuY12ThangJRXML> setNoCanChuY12T(CicTemplate cicTemplate) {
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getNoCanChuYTrong12Thang()) ||
            ObjectUtils.isEmpty(cicTemplate.getBody().getNoCanChuYTrong12Thang().getNoCanChuYChiTiet()) ||
            ObjectUtils.isEmpty(cicTemplate.getBody().getNoCanChuYTrong12Thang().getNoCanChuYChiTiet().getTctd())) {
            return null;
        } else {
            List<ListNoCanChuY12ThangJRXML> list = new ArrayList<>();
            list.add(cicReportMapper.mapListNoCanChuY12T(cicTemplate));
            return list;
        }
    }

    private List<NoVAMCChiTiet> setNoVAMC(CicTemplate cicTemplate) {
        NoVAMCChiTiet noVAMCChiTiet = cicTemplate.getBody().getNoVAMC().getNoVAMCChiTiet();
        if (ObjectUtils.isEmpty(noVAMCChiTiet.getNgaySlString())) {
            return null;
        } else {
            List<NoVAMCChiTiet> noVAMCChiTietList = new ArrayList<>();
            cicReportMapper.mapNoVAMCChiTiet(noVAMCChiTiet, noVAMCChiTiet);
            noVAMCChiTietList.add(noVAMCChiTiet);
            return noVAMCChiTietList;
        }
    }

    private List<vn.com.msb.homeloan.core.model.cic.cicreport.body.twelvemonthsoutstanding.TCTD> setListTheTD12Thang(CicTemplate cicTemplate) {
        DoNoTheTDChiTiet doNoTheTDChiTiet = cicTemplate.getBody().getDuNoTheTD12Thang().getDoNoTheTDChiTiet();
        return ObjectUtils.isEmpty(doNoTheTDChiTiet.getTctdList()) ? null : doNoTheTDChiTiet.getTctdList();
    }

    private List<TraiPhieu> setListTraiPhieu(CicTemplate cicTemplate) {
        TraiPhieu traiPhieu = cicTemplate.getBody().getTraiPhieu();
        if (ObjectUtils.isEmpty(traiPhieu.getTenTctd())) {
            return null;
        } else {
            List<TraiPhieu> traiPhieuList = new ArrayList<>();
            cicReportMapper.mapTraiPhieu(traiPhieu, traiPhieu);
            traiPhieuList.add(traiPhieu);
            return traiPhieuList;
        }
    }

    private List<CamKetNB> setListCamKetNB(CicTemplate cicTemplate) {
        CamKetNB camKetNB = cicTemplate.getBody().getCamKetNB();
        if (ObjectUtils.isEmpty(camKetNB.getTenTctd())) {
            return null;
        } else {
            List<CamKetNB> camKetNBList = new ArrayList<>();
            camKetNB.setNgaySl(new Date());
            camKetNBList.add(camKetNB);
            return camKetNBList;
        }
    }

    private List<ListDataJRXML> setListData(CicTemplate cicTemplate) {
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getDstctd().getTctdList())) {
            return null;
        } else {
            List<ListDataJRXML> listResult = new ArrayList<>();
            int stt = 1;
            List<TCTD> tctdList = cicTemplate.getBody().getDstctd().getTctdList();
            tctdList.stream()
                    .filter(s -> ObjectUtils.isEmpty(s.getChiTiet().getLoaiDuNo().getDuNoBoSung().getNoTaiNHPTVND()))
                    .forEach(s -> s.getChiTiet().getLoaiDuNo().getDuNoBoSung().setNoTaiNHPTVND(0L));
            tctdList.stream()
                    .filter(x -> ObjectUtils.isEmpty(x.getChiTiet().getLoaiDuNo().getDuNoBoSung().getNoTaiNHPTUSD()))
                    .forEach(x -> x.getChiTiet().getLoaiDuNo().getDuNoBoSung().setNoTaiNHPTUSD(0L));
            tctdList.stream()
                    .filter(x -> ObjectUtils.isEmpty(x.getChiTiet().getLoaiDuNo().getDuNoBoSung().getNoXauKhacVND()))
                    .forEach(x -> x.getChiTiet().getLoaiDuNo().getDuNoBoSung().setNoXauKhacVND(0L));
            tctdList.stream()
                    .filter(x -> ObjectUtils.isEmpty(x.getChiTiet().getLoaiDuNo().getDuNoBoSung().getNoXauKhacUSD()))
                    .forEach(x -> x.getChiTiet().getLoaiDuNo().getDuNoBoSung().setNoXauKhacUSD(0L));
            for (TCTD tctd : tctdList) {
                ListDataJRXML d = cicReportMapper.mapListData(tctd);
                d.setStt(String.valueOf(stt));
                cicReportMapper.sumDuNoNganVND(d, tctd);
                cicReportMapper.sumDuNoNganUSD(d, tctd);
                cicReportMapper.sumDuNoTrungVND(d, tctd);
                cicReportMapper.sumDuNoTrungUSD(d, tctd);
                cicReportMapper.sumDuNoDaiVND(d, tctd);
                cicReportMapper.sumDuNoDaiUSD(d, tctd);
                cicReportMapper.sumDuNoKhacVND(d, tctd);
                cicReportMapper.sumDuNoKhacUSD(d, tctd);
                cicReportMapper.sumVnd(d, tctd);
                cicReportMapper.sumUsd(d, tctd);
                listResult.add(d);
                stt++;
            }
            return listResult;
        }
    }

    private List<vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.TCTD> setListNoXau36T(
        CicTemplate cicTemplate) {
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getNoXau36Thang().getNoXauChiTiet().getTctdList())) {
            return null;
        } else {
            List<vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.TCTD> tctdList
                = cicTemplate.getBody().getNoXau36Thang().getNoXauChiTiet().getTctdList();
            for (vn.com.msb.homeloan.core.model.cic.cicreport.body.thirtysixmonthsbaddebt.TCTD tctd :
                tctdList) {
                cicReportMapper.mapListNoXau36T(tctd, tctd);
            }
            return tctdList;
        }
    }

    private List<vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance.TCTD> setListTheTD(
        CicTemplate cicTemplate) {
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getDuNoTheTD().getDuNoTheTDChiTiet().getTctdList())) {
            return null;
        } else {
            for (vn.com.msb.homeloan.core.model.cic.cicreport.body.creditcardbalance.TCTD tctd :
                cicTemplate.getBody()
                                                                                                                       .getDuNoTheTD()
                                                                                                                       .getDuNoTheTDChiTiet()
                                                                                                                       .getTctdList()) {
                cicReportMapper.mapListTheTD(tctd, tctd);
            }
            return cicTemplate.getBody().getDuNoTheTD().getDuNoTheTDChiTiet().getTctdList();
        }
    }

    private List<ChiTiet> setListHopDongTinDung(CicTemplate cicTemplate) {
        List<ChiTiet> chiTietList = cicTemplate.getBody().getHdtd().getChiTietList();
        if (ObjectUtils.isEmpty(cicTemplate.getBody().getHdtd().getChiTietList())) {
            return null;
        } else {
            for (ChiTiet chiTiet : chiTietList) {
                cicReportMapper.mapListHopDongTinDung(chiTiet, chiTiet);
            }
            return chiTietList;
        }
    }

    private List<DanhSachTCTDTraCuu> setListTCTDTT(CicTemplate cicTemplate) {
        List<DanhSachTCTDTraCuu> danhSachTCTDTraCuuList = cicTemplate.getBody().getTtKhac().getDanhSachTCTDTraCuuList();
        if (ObjectUtils.isEmpty(danhSachTCTDTraCuuList)) {
            return null;
        } else {
            for (DanhSachTCTDTraCuu danhSachTCTDTraCuu : danhSachTCTDTraCuuList) {
                cicReportMapper.mapListTCTDTT(danhSachTCTDTraCuu, danhSachTCTDTraCuu);
            }
            return danhSachTCTDTraCuuList;
        }
    }

}
