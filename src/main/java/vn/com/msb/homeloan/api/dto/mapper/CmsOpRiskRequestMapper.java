package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSOpsRiskCheckCRequest;
import vn.com.msb.homeloan.api.dto.request.CMSOpsRiskCheckPRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckCRequest;
import vn.com.msb.homeloan.core.model.request.OpsRiskCheckP;

@Mapper
public interface CmsOpRiskRequestMapper {

  CmsOpRiskRequestMapper INSTANCE = Mappers.getMapper(CmsOpRiskRequestMapper.class);

  OpsRiskCheckP toModelP(CMSOpsRiskCheckPRequest.CheckPData request);

  OpsRiskCheckCRequest.OpsRiskCheckC toModelC(CMSOpsRiskCheckCRequest.CheckCData request);

  List<OpsRiskCheckP> toModelPs(List<CMSOpsRiskCheckPRequest.CheckPData> requests);

  List<OpsRiskCheckCRequest.OpsRiskCheckC> toModelCs(
      List<CMSOpsRiskCheckCRequest.CheckCData> requests);
}
