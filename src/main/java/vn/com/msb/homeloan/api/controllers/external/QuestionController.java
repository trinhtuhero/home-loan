package vn.com.msb.homeloan.api.controllers.external;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.QuestionCategoryMapper;
import vn.com.msb.homeloan.core.model.QuestionCategory;
import vn.com.msb.homeloan.core.service.QuestionService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping
  public ResponseEntity<ApiResource> getQuestion(@RequestParam("product") String product,
      @RequestParam(value = "one_level", required = false) Boolean oneLevel) {
    List<QuestionCategory> question = questionService.getFrequentlyQuestion(product, oneLevel);
    return ResponseEntity.ok(
        new ApiResource(QuestionCategoryMapper.INSTANCE.toDtos(question), HttpStatus.OK.value()));
  }
}
