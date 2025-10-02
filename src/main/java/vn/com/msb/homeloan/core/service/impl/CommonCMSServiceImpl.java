package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.core.client.CommonCMSRestClient;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.service.CommonCMSService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommonCMSServiceImpl implements CommonCMSService {

  private final CommonCMSRestClient commonCMSRestClient;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public List<Role> getAuthorInfo(String token) throws IOException {

    ResponseEntity<ApiInternalResponse> apiResponse = commonCMSRestClient.authorInfo(token);
    if (apiResponse != null && apiResponse.getBody() != null
        && apiResponse.getBody().getData() != null) {
      return Arrays.asList(modelMapper.map(apiResponse.getBody().getData(), Role[].class));
    } else {
      return new ArrayList<>();
    }
  }
}
