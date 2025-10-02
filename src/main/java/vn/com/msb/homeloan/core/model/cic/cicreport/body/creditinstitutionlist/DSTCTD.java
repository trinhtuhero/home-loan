package vn.com.msb.homeloan.core.model.cic.cicreport.body.creditinstitutionlist;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Setter
@Getter
public class DSTCTD {
    private List<TCTD> tctdList;

    public List<TCTD> getTctdList() {
        return tctdList;
    }

    @XmlElement(name = "TCTD")
    public void setTctdList(List<TCTD> tctdList) {
        this.tctdList = tctdList;
    }
}
