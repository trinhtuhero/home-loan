package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.CreditAppraisalRequest;
import vn.com.msb.homeloan.core.model.CreditAppraisal;

@Mapper(uses = {FieldSurveyItemRequestMapper.class})
public interface CreditAppraisalRequestMapper {

  CreditAppraisalRequestMapper INSTANCE = Mappers.getMapper(CreditAppraisalRequestMapper.class);

    /*default CreditAppraisal toModel(CreditAppraisalRequest request) {
        CreditAppraisal creditAppraisal = new CreditAppraisal();

        creditAppraisal.setUuid(request.getUuid());
        creditAppraisal.setLoanApplicationId(request.getLoanApplicationId());
        creditAppraisal.setCreditEvaluationResult(CreditEvaluationResultEnum.valueOf(request.getCreditEvaluationResult()));
        creditAppraisal.setBeforeOpenLimitCondition(request.getBeforeOpenLimitCondition());
        creditAppraisal.setBeforeDisbursementCondition(request.getBeforeDisbursementCondition());
        creditAppraisal.setAfterDisbursementCondition(request.getAfterDisbursementCondition());
        creditAppraisal.setBusinessReview(request.getBusinessReview());
        creditAppraisal.setCssProfileId(request.getCssProfileId());
        creditAppraisal.setBusinessArea(request.getBusinessArea());
        creditAppraisal.setBusinessName(request.getBusinessName());
        creditAppraisal.setSaleFullName(request.getSaleFullName());
        creditAppraisal.setSalePhone(request.getSalePhone());
        creditAppraisal.setManagerFullName(request.getManagerFullName());
        creditAppraisal.setManagerPhone(request.getManagerPhone());
        creditAppraisal.setSignatureLevel(SignatureLevelEnum.valueOf(request.getSignatureLevel()));

        List<FieldSurveyItem> fieldSurveyItems = FieldSurveyItemRequestMapper.INSTANCE.toModels(request.getFieldSurveyItems());
        creditAppraisal.setFieldSurveyItems(fieldSurveyItems);

        List<CreditworthinessItem> creditworthinessItems = CreditworthinessItemRequestMapper.INSTANCE.toModels(request.getCreditworthinessItemRequests());
        creditAppraisal.setCreditworthinessItemRequests(creditworthinessItems);

        List<ExceptionItem> exceptionItems = ExceptionItemRequestMapper.INSTANCE.toModels(request.getExceptionItems());
        creditAppraisal.setExceptionItems(exceptionItems);

        return creditAppraisal;
    }

    default List<CreditAppraisal> toModels(List<CreditAppraisalRequest> requests) {
        List<CreditAppraisal> list = new ArrayList<>();
        for (CreditAppraisalRequest creditAppraisalRequest : requests) {
            CreditAppraisal creditAppraisal = INSTANCE.toModel(creditAppraisalRequest);
            list.add(creditAppraisal);
        }
        return list;
    }*/

  CreditAppraisal toModel(CreditAppraisalRequest request);

  List<CreditAppraisal> toModels(List<CreditAppraisalRequest> requests);
}
