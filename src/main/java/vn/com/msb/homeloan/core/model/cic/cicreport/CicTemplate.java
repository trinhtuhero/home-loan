package vn.com.msb.homeloan.core.model.cic.cicreport;

import vn.com.msb.homeloan.core.model.cic.cicreport.body.Body;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "CicTemplate")
public class CicTemplate {
    private Header header;

    private Body body;

    public Header getHeader() {
        return header;
    }

    @XmlElement(name = "Header")
    public void setHeader(Header header) {
        this.header = header;
    }
    public Body getBody() {
        return body;
    }

    @XmlElement(name = "Body")
    public void setBody(Body body) {
        this.body = body;
    }

}
