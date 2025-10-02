package vn.com.msb.homeloan.core.service;

public interface PDFGenerationService {

    String convertToPDF(String loanHash, String customerId);

}
