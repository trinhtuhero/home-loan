package vn.com.msb.homeloan.core.parser;

import vn.com.msb.homeloan.core.model.cic.content.CicContent;

/**
 * CIC content parser
 */
public interface ProcessCicParser {

  CicContent getCicContentFromXml();
}
