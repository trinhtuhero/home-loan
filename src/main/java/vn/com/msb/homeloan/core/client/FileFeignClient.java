package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.core.model.request.DeleteFileRequest;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.response.DeleteFileResponse;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.UploadPresignedUrlResponse;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;


@FeignClient(name = "file-common-feign-client", url = "${feign.common.file.url}", configuration = {
    EncoderFeignConfig.class})
public interface FileFeignClient {

  @PostMapping(value = "/api/v1/file-management/generate-presigned-url/upload")
  ResponseEntity<UploadPresignedUrlResponse> generatePreSignedUrlToUpload(
      @RequestBody UploadPresignedUrlRequest request,
      @RequestHeader(value = "client_code", required = false) String clientCode,
      @RequestHeader(value = "scopes", required = false) String scopes,
      @RequestHeader(value = "document_type", required = false) String documentType);

  @PostMapping(value = "/api/v1/file-management/generate-presigned-url/download")
  ResponseEntity<DownloadPresignedUrlResponse> generatePreSignedUrlToDownload(
      @RequestBody DownloadPresignedUrlRequest request,
      @RequestHeader(value = "client_code", required = false) String clientCode,
      @RequestHeader(value = "scopes", required = false) String scopes);

  @PostMapping(value = "/api/v1/file-management/delete")
  ApiInternalResponse<DeleteFileResponse> deleteFile(
      @RequestBody DeleteFileRequest request,
      @RequestHeader(value = "client_code", required = true) String clientCode,
      @RequestHeader(value = "scopes", required = true) String scopes);
}
