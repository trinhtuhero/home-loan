package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSMvalueDocumentResponse;
import vn.com.msb.homeloan.core.model.MvalueDocument;

import java.util.List;

@Mapper
public interface CMSMvalueDocumentResponseMapper {

  CMSMvalueDocumentResponseMapper INSTANCE = Mappers.getMapper(CMSMvalueDocumentResponseMapper.class);

  List<CMSMvalueDocumentResponse> toCmsResponses(List<MvalueDocument> responses);
}
