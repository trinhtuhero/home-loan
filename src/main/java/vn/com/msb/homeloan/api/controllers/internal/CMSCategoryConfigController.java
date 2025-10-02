package vn.com.msb.homeloan.api.controllers.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSAmlResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSGetAmlInfoResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSAmlCheckRequest;
import vn.com.msb.homeloan.core.entity.CategoryConfigEntity;
import vn.com.msb.homeloan.core.model.Aml;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;
import vn.com.msb.homeloan.core.service.AmlService;
import vn.com.msb.homeloan.core.service.CategoryConfigService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/category")
@RequiredArgsConstructor
public class CMSCategoryConfigController {
    private final CategoryConfigService categoryConfigService;

    @GetMapping
    public ResponseEntity<ApiResource> getCategoryConfig(@RequestParam("type") String type) {
        List<CategoryConfigEntity> result = categoryConfigService.getCategoryConfig(type);
        ApiResource apiResource = new ApiResource(result, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResource);
    }
}
