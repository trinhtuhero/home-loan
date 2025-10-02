package vn.com.msb.homeloan.core.service.impl;


import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import vn.com.msb.homeloan.api.dto.request.DownloadPresignedUrlRequest;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.core.client.FileFeignClient;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.request.DeleteFileRequest;
import vn.com.msb.homeloan.core.model.request.UploadPresignedUrlRequest;
import vn.com.msb.homeloan.core.model.response.DeleteFileResponse;
import vn.com.msb.homeloan.core.model.response.DownloadPresignedUrlResponse;
import vn.com.msb.homeloan.core.model.response.UploadPresignedUrlResponse;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.FileService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

  private final FileFeignClient fileClient;
  private final RestTemplate restTemplate;
  private final EnvironmentProperties environmentProperties;
  private final LoanApplicationRepository loanApplicationRepository;

  @Override
  public boolean upload(UploadPresignedUrlRequest request, byte[] fileData, boolean dnvv) {
    UploadPresignedUrlResponse generateUrlResponse = generatePreSignedUrlToUpload(request);
    if (!generateUrlResponse.getStatus().equals("200")) {
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_ERROR);
    }

    URI uploadUrl = URI.create(generateUrlResponse.getData().getUrl());

    HttpHeaders headers = new HttpHeaders();

    if (dnvv) {
      headers.setContentType(MediaType.APPLICATION_PDF);
    }

    Map<String, String> map = generateUrlResponse.getData().getHeadersNeedToAdd();
    if (map != null) {
      for (Map.Entry<String, String> entry : map.entrySet()) {
        headers.set(entry.getKey(), entry.getValue());
      }
    }

    HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileData, headers);
    ResponseEntity<String> response;
    try {
      ParameterizedTypeReference<String> parameterizedTypeReference
          = new ParameterizedTypeReference<String>() {
      };
      response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity,
          parameterizedTypeReference);
    } catch (HttpClientErrorException exception) {
      log.error("Upload file error: {} - {}", generateUrlResponse.getData().getUrl(), exception);
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_ERROR);
    }

    return response.getStatusCode().value() == 200;
  }

  @Override
  public UploadPresignedUrlResponse generatePreSignedUrlToUpload(
      UploadPresignedUrlRequest request) {
    try {
      ResponseEntity<UploadPresignedUrlResponse> apiResponse = fileClient.generatePreSignedUrlToUpload(
          request, environmentProperties.getClientCode(), environmentProperties.getClientScopes(),
          request.getDocumentType());
      return apiResponse.getBody();
    } catch (FeignException e) {
      log.error("Generate PreSigned Url To Upload error {}", e.getMessage());
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_ERROR);
    }
  }

  @Override
  public DownloadPresignedUrlResponse generatePreSignedUrlToDownload(
      DownloadPresignedUrlRequest request) {
    try {
      ResponseEntity<DownloadPresignedUrlResponse> apiResponse = fileClient.generatePreSignedUrlToDownload(
          request, environmentProperties.getClientCode(), environmentProperties.getClientScopes());
      return apiResponse.getBody();
    } catch (FeignException e) {
      log.error("Generate PreSigned Url To download error {}", e.getMessage());
      throw new ApplicationException(ErrorEnum.LOAN_UPLOAD_FILE_NOT_FOUND);
    }
  }

  @Override
  public DeleteFileResponse deleteFile(DeleteFileRequest deleteFileRequest) {
    ApiInternalResponse<DeleteFileResponse> apiResponse = fileClient.deleteFile(
        deleteFileRequest, environmentProperties.getClientCode(),
        environmentProperties.getClientScopes());
    return apiResponse.getData();
  }
}
