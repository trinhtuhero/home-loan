package vn.com.msb.homeloan.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadExist {
    private String id;
    private String fileName;
    private String folder;
}
