package vn.com.msb.homeloan.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class BASE64DecodedMultipartFile implements MultipartFile {

  private final byte[] fileContent;
  private final String fileName;

  public BASE64DecodedMultipartFile(byte[] fileContent, String filename) {
    this.fileContent = fileContent;
    this.fileName = filename;
  }

  @Override
  public String getName() {
    return this.fileName;
  }

  @Override
  public String getOriginalFilename() {
    return this.fileName;
  }

  @Override
  public String getContentType() {
    return "pdf";
  }

  @Override
  public boolean isEmpty() {
    return fileContent == null || fileContent.length == 0;
  }

  @Override
  public long getSize() {
    return fileContent.length;
  }

  @Override
  public byte[] getBytes() throws IOException {
    return fileContent;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(fileContent);
  }

  @Override
  public void transferTo(File dest) {
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(dest);
      fileOutputStream.write(fileContent);
      fileOutputStream.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}