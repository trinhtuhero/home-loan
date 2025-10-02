package vn.com.msb.homeloan.core.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteFileRequest {

  @JsonProperty(value = "requestid")
  String requestId = UUID.randomUUID().toString();

  @JsonProperty(value = "filename")
  String fileName;
}
