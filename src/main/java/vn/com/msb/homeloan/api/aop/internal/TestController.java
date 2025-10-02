package vn.com.msb.homeloan.api.aop.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.model.ProductCodeConfig;
import vn.com.msb.homeloan.core.service.ProductCodeConfigService;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/test")
@RequiredArgsConstructor
public class TestController {

    private final ProductCodeConfigService productCodeConfigService;

    @GetMapping
    public ResponseEntity<ApiResource> test() {
        HashMap<String, Object> map = productCodeConfigService.getMapKeys("ff3b9d43-9843-4d8d-8d06-22c2a997b2df", "9bcf9abd-bf52-449a-a8fe-4c9056e5cc5f");
        List<ProductCodeConfig> list = productCodeConfigService.getProductCodeConfigs(map);
        return null;
    }
}
