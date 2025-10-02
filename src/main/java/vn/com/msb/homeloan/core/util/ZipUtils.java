package vn.com.msb.homeloan.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.model.download.DownloadFile;
import vn.com.msb.homeloan.core.model.download.ZipBlock;

public class ZipUtils {

  // Zip file từng block
  public static byte[] zipBlock(ZipBlock zipBlock)
      throws Exception {

    // Used to create byte array from resulting zip
    ByteArrayOutputStream dest = new ByteArrayOutputStream();

    // The stream that will contain our zip file representation
    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

    // Buffer
    byte data[] = new byte[2048];

    Iterator<DownloadFile> itr = zipBlock.getDownloadFileList().iterator();

    //Insert files
    while (itr.hasNext()) {
      DownloadFile downloadFile = itr.next();

      byte[] tempFile = downloadFile.getContent();

      ByteArrayInputStream bytesIn = new ByteArrayInputStream(tempFile);
      // Take one InputStream from array as a new file to add to zip
      BufferedInputStream origin = new BufferedInputStream(bytesIn, 2048);

      // Name entry using key in map
      ZipEntry entry = new ZipEntry(downloadFile.getFileName());
      out.putNextEntry(entry);

      // Write ByteArrayInputStream contents to zip output
      int count;
      while ((count = origin.read(data, 0, 2048)) != -1) {
        out.write(data, 0, count);
      }
      bytesIn.close();
      origin.close();
    }
    out.close();

    byte[] outBytes = dest.toByteArray();

    dest.close();

    // Return zip as byte[]
    return outBytes;
  }

  //Nested Zip files
  public static byte[] zipAllInOne(Map<String, byte[]> files)
      throws Exception {

    // Used to create byte array from resulting zip
    ByteArrayOutputStream dest = new ByteArrayOutputStream();

    // The stream that will contain our zip file representation
    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

    // Buffer
    byte data[] = new byte[2048];

    Iterator<String> itr = files.keySet().iterator();

    //Insert files
    while (itr.hasNext()) {
      String tempName = itr.next();
      byte[] tempFile = files.get(tempName);

      ByteArrayInputStream bytesIn = new ByteArrayInputStream(tempFile);
      // Take one InputStream from array as a new file to add to zip
      BufferedInputStream origin = new BufferedInputStream(bytesIn, 2048);

      // Name entry using key in map
      ZipEntry entry = new ZipEntry(tempName);
      out.putNextEntry(entry);

      // Write ByteArrayInputStream contents to zip output
      int count;
      while ((count = origin.read(data, 0, 2048)) != -1) {
        out.write(data, 0, count);
      }
      bytesIn.close();
      origin.close();
    }
    out.close();

    byte[] outBytes = dest.toByteArray();

    dest.close();

    // Return zip as byte[]
    return outBytes;
  }

  //Nested Zip files
  public static byte[] zipAll(Map<String, byte[]> files)
      throws Exception {

    // Used to create byte array from resulting zip
    ByteArrayOutputStream dest = new ByteArrayOutputStream();

    // The stream that will contain our zip file representation
    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

    // Buffer
    byte data[] = new byte[2048];

    Iterator<String> itr = files.keySet().iterator();

    ZipEntry entry1 = new ZipEntry(Constants.ZipLoanApplication.HSPL_ZIP_FOLDER);
    out.putNextEntry(entry1);
    ZipEntry entry2 = new ZipEntry(Constants.ZipLoanApplication.HSNT_ZIP_FOLDER);
    out.putNextEntry(entry2);
    ZipEntry entry3 = new ZipEntry(Constants.ZipLoanApplication.HSTSBĐ_ZIP_FOLDER);
    out.putNextEntry(entry3);
    ZipEntry entry4 = new ZipEntry(Constants.ZipLoanApplication.HSVV_ZIP_FOLDER);
    out.putNextEntry(entry4);
    ZipEntry entry5 = new ZipEntry(Constants.ZipLoanApplication.HSQHTD_ZIP_FOLDER);
    out.putNextEntry(entry5);
    ZipEntry entry6 = new ZipEntry(Constants.ZipLoanApplication.HSK_ZIP_FOLDER);
    out.putNextEntry(entry6);

    //Insert files
    while (itr.hasNext()) {
      String tempName = itr.next();
      byte[] tempFile = files.get(tempName);

      ByteArrayInputStream bytesIn = new ByteArrayInputStream(tempFile);
      // Take one InputStream from array as a new file to add to zip
      BufferedInputStream origin = new BufferedInputStream(bytesIn, 2048);

      // Name entry using key in map
      ZipEntry entry = new ZipEntry(getZipName(tempName));
      out.putNextEntry(entry);

      // Write ByteArrayInputStream contents to zip output
      int count;
      while ((count = origin.read(data, 0, 2048)) != -1) {
        out.write(data, 0, count);
      }
      bytesIn.close();
      origin.close();
    }

    out.close();

    byte[] outBytes = dest.toByteArray();

    dest.close();

    // Return zip as byte[]
    return outBytes;
  }

  public static String getZipName(String zipName) {
    if (zipName.contains(Constants.ZipLoanApplication.HSPL_ZIP_PREFIX_1)
        || zipName.contains(Constants.ZipLoanApplication.HSPL_ZIP_PREFIX_2)
        || zipName.contains(Constants.ZipLoanApplication.HSPL_ZIP_PREFIX_3)) {
      return Constants.ZipLoanApplication.HSPL_ZIP_FOLDER + zipName;
    }
    if (zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_1)
        || zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_2)
        || zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_3)
        || zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_4)
        || zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_5)
        || zipName.contains(Constants.ZipLoanApplication.HSNT_ZIP_PREFIX_6)) {
      return Constants.ZipLoanApplication.HSNT_ZIP_FOLDER + zipName;
    }
    if (zipName.contains(Constants.ZipLoanApplication.HSTSBĐ_ZIP_PREFIX_1)
        || zipName.contains(Constants.ZipLoanApplication.HSTSBĐ_ZIP_PREFIX_2)) {
      return Constants.ZipLoanApplication.HSTSBĐ_ZIP_FOLDER + zipName;
    }
    if (zipName.contains(Constants.ZipLoanApplication.HSQHTD_ZIP_PREFIX_1)
        || zipName.contains(Constants.ZipLoanApplication.HSQHTD_ZIP_PREFIX_2)) {
      return Constants.ZipLoanApplication.HSQHTD_ZIP_FOLDER + zipName;
    }
    if (zipName.contains(Constants.ZipLoanApplication.HSVV_ZIP_PREFIX_1)) {
      return Constants.ZipLoanApplication.HSVV_ZIP_FOLDER + zipName;
    }
    if (zipName.contains(Constants.ZipLoanApplication.HSK_ZIP_PREFIX_1)
        || zipName.contains(Constants.ZipLoanApplication.HSK_ZIP_PREFIX_2)) {
      return Constants.ZipLoanApplication.HSK_ZIP_FOLDER + zipName;
    }
    return zipName;
  }
}
