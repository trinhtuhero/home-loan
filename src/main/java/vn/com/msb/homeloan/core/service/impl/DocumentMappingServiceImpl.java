package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;
import vn.com.msb.homeloan.core.entity.MvalueUploadFilesEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MvalueDocument;
import vn.com.msb.homeloan.core.model.mapper.MvalueDocumentMapper;
import vn.com.msb.homeloan.core.repository.DocumentMappingRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.MvalueUploadFilesRepository;
import vn.com.msb.homeloan.core.service.DocumentMappingService;
import vn.com.msb.homeloan.core.util.ObjectUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DocumentMappingServiceImpl implements DocumentMappingService {
  private final DocumentMappingRepository documentMappingRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final MvalueUploadFilesRepository mvalueUploadFilesRepository;

  @Override
  public List<MvalueDocument> getDocumentMapping(
      String loanId, String collateralType, String type, String collateralId) {
    loanApplicationRepository
        .findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    List<MvalueDocument> lsEntities =
        MvalueDocumentMapper.INSTANCE.toModels(
            documentMappingRepository.findByCollateralTypeAndType(collateralType, type));

    lsEntities.forEach(
        entity -> {
          List<MvalueUploadFilesEntity> ls;
          if (StringUtils.isEmpty(collateralId)) {
              ls =
                mvalueUploadFilesRepository
                  .findByLoanApplicationIdAndDocumentMvalueIdAndStatus(
                    loanId,
                    entity.getId() + "",
                    LoanUploadFileStatusEnum.UPLOADED.getCode());
          } else {
            ls =
                mvalueUploadFilesRepository
                    .findByLoanApplicationIdAndDocumentMvalueIdAndCollateralIdAndStatus(
                        loanId,
                        entity.getId() + "",
                        collateralId,
                        LoanUploadFileStatusEnum.UPLOADED.getCode());
          }
          if (ObjectUtil.isNotEmpty(ls))entity.setEntities(ls);
        });
    return lsEntities;
  }
}
