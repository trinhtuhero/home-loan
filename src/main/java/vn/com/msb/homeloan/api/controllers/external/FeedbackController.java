package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.SuggestionCommentResponseMapper;
import vn.com.msb.homeloan.api.dto.request.FeedbackRequest;
import vn.com.msb.homeloan.api.dto.response.feedback.FeedbackInitFormResponse;
import vn.com.msb.homeloan.core.model.feedback.SuggestionComment;
import vn.com.msb.homeloan.core.service.FeedbackService;
import vn.com.msb.homeloan.core.service.SuggestionCommentService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/feedback")
@RequiredArgsConstructor
public class FeedbackController {

  private final SuggestionCommentService suggestionCommentService;

  private final FeedbackService feedbackService;

  @GetMapping("/init-form")
  public ResponseEntity<ApiResource> initForm(@RequestParam Integer rate) {
    List<SuggestionComment> suggestionComments = suggestionCommentService.findSuggestionComments(
        rate);
    FeedbackInitFormResponse feedbackInitFormResponse = new FeedbackInitFormResponse(rate,
        SuggestionCommentResponseMapper.INSTANCE.toResponses(suggestionComments));
    ApiResource apiResource = new ApiResource(feedbackInitFormResponse, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping
  public ResponseEntity<ApiResource> submitFeedback(@RequestBody @Valid FeedbackRequest request) {
    feedbackService.submitFeedback(request.getLoanApplicationId(), request.getRate(),
        request.getComments(), request.getAdditionalComment());
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
