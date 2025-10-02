package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.request.DeleteFileRequest;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.response.DeleteFileResponse;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.UploadPresignedUrlResponse;

public interface FileService {

  boolean upload(UploadPresignedUrlRequest request, byte[] fileData, boolean dnvv);

  UploadPresignedUrlResponse generatePreSignedUrlToUpload(
      UploadPresignedUrlRequest request);

  DownloadPresignedUrlResponse generatePreSignedUrlToDownload(DownloadPresignedUrlRequest request);

  DeleteFileResponse deleteFile(DeleteFileRequest deleteFileRequest);
}
