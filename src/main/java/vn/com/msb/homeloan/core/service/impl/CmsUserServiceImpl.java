package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.core.constant.CmsUserStatusEnum;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.CmsUserEntity;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;
import vn.com.msb.homeloan.core.model.mapper.CmsUserMapper;
import vn.com.msb.homeloan.core.model.response.cms.role.Role;
import vn.com.msb.homeloan.core.repository.CmsUserRepository;
import vn.com.msb.homeloan.core.repository.OrganizationRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.CommonCMSService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.Graph;
import vn.com.msb.homeloan.core.util.ImportExcelUtil;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.RoleConfig;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CmsUserServiceImpl implements CmsUserService {

  private final CmsUserRepository cmsUserRepository;
  private final OrganizationRepository organizationRepository;
  private final RoleConfig roleConfig;
  private final CommonCMSService commonCMSService;
  private final EnvironmentProperties environmentProperties;

  @Override
  public CmsUser findByEmail(String email) {
    if (email == null) {
      throw new ApplicationException(ErrorEnum.CMS_USER_EMAIL_FOUND);
    }
    CmsUserEntity entity = cmsUserRepository.findByEmail(email)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.CMS_USER_EMAIL_FOUND));
    return CmsUserMapper.INSTANCE.toModel(entity);
  }

  @Override
  public CmsUserSearch cmsUserSearch(CmsUserSearchParam cmsUserSearchParam) {
    return cmsUserRepository.cmsUserSearch(cmsUserSearchParam);
  }

  @Override
  public byte[] importCmsUsers(MultipartFile multipartFile) throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
    XSSFSheet worksheet = workbook.getSheet(Constants.ImportCmsUser.SHEET_NAME);

    // danh sách import users
    List<CmsUserEntity> cmsUsersImport = new ArrayList<>();
    // danh sách phòng giao dịch
    Set<String> brandCodesInSheet = new HashSet<>();
    // danh sách emails của import users
    Set<String> emailsInSheet = new HashSet<>();

    // danh sách emails của leaders
    Set<String> emailLeadersInSheet = new HashSet<>();
    Set<CmsUserEntity> leaders = new HashSet<>();
    Set<String> emailLeader = new HashSet<>();

    // dùng để check duplicate
    Map<String, Integer> mapImplId = new HashMap<>();
    Map<String, Integer> mapEmail = new HashMap<>();

    List<String> headers = Arrays.asList(Constants.ImportCmsUser.STT,
        Constants.ImportCmsUser.EMPL_ID,
        Constants.ImportCmsUser.FULL_NAME,
        Constants.ImportCmsUser.EMAIL,
        Constants.ImportCmsUser.PHONE_NUMBER,
        Constants.ImportCmsUser.BRANCH_CODE,
        Constants.ImportCmsUser.LEADER,
        Constants.ImportCmsUser.ROLE);
    // danh sách roles của rm
    //Bỏ check role
    //List<String> roles = Arrays.asList(roleConfig.getAdmin(), roleConfig.getBm(), roleConfig.getRm());

    // check import lỗi hay không
    boolean checkImport = true;

    // tạo cell lỗi
    XSSFFont font = workbook.createFont();
    CellStyle style = workbook.createCellStyle();
    font.setFontHeight(11);
    font.setBold(true);
    font.setFontName("Times New Roman");
    style.setFont(font);
    worksheet.setColumnWidth(8, 10000);
    style.setFillForegroundColor(IndexedColors.RED.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    Cell cellHeaderError = worksheet.getRow(0).createCell(8);
    cellHeaderError.setCellStyle(style);
    cellHeaderError.setCellValue("Lỗi");

    for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
      XSSFRow row = worksheet.getRow(i);
      StringBuilder error = new StringBuilder();
      CmsUserEntity cmsUserEntity = new CmsUserEntity();
      if (i == 0) {
        //check validate header
        List<String> headersInFile = new ArrayList<>();
        headersInFile.add(ImportExcelUtil.getCellValue(row, 0));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 1));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 2));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 3));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 4));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 5));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 6));
        headersInFile.add(ImportExcelUtil.getCellValue(row, 7));
        if (!headers.equals(headersInFile)) {
          throw new ApplicationException(ErrorEnum.IMPORT_TEMPLATE_FILE_INVALID);
        }
      } else {
        String emplId = ImportExcelUtil.getCellValue(row, 1);
        cmsUserEntity.setEmplId(emplId);
        Cell cellError = row.createCell(8);
        cellError.setCellValue("");
        if (StringUtils.isEmpty(emplId)) {
          error = new StringBuilder(Constants.ImportCmsUser.ERROR_EMPL_ID_NOT_EMPTY + "\n");
        } else if (emplId.length() > Constants.ImportCmsUser.MAX_LENGTH_EMPL_ID) {
          error = new StringBuilder(
              String.format(Constants.ImportCmsUser.ERROR_EMPL_ID_EXCEED_MAX_LENGTH,
                  Constants.ImportCmsUser.MAX_LENGTH_EMPL_ID));
        }

        String fullName = ImportExcelUtil.getCellValue(row, 2);
        cmsUserEntity.setFullName(fullName);
        if (StringUtils.isEmpty(fullName)) {
          error.append(Constants.ImportCmsUser.ERROR_FULL_NAME_NOT_EMPTY + "\n");
        } else if (fullName.length() > Constants.ImportCmsUser.MAX_LENGTH_FULL_NAME) {
          error.append(String.format(Constants.ImportCmsUser.ERROR_FULL_NAME_EXCEED_MAX_LENGTH,
              Constants.ImportCmsUser.MAX_LENGTH_FULL_NAME) + "\n");
        }

        String email = ImportExcelUtil.getCellValue(row, 3);
        emailsInSheet.add(email);
        cmsUserEntity.setEmail(email.toLowerCase());
        if (StringUtils.isEmpty(email)) {
          error.append(Constants.ImportCmsUser.ERROR_EMAIL_NOT_EMPTY + "\n");
        } else if (email.length() > Constants.ImportCmsUser.MAX_LENGTH_EMAIL) {
          error.append(String.format(Constants.ImportCmsUser.ERROR_EMAIL_EXCEED_MAX_LENGTH,
              Constants.ImportCmsUser.MAX_LENGTH_EMAIL) + "\n");
        } else if (!EmailValidator.getInstance().isValid(email)) {
          error.append(Constants.ImportCmsUser.ERROR_EMAIL_INVALID + "\n");
        }

        String phone = ImportExcelUtil.getCellValue(row, 4);
        cmsUserEntity.setPhone(phone);
        if (StringUtils.isEmpty(phone)) {
          error.append(Constants.ImportCmsUser.ERROR_PHONE_NOT_EMPTY + "\n");
        } else if (!StringUtils.isPhone(phone)) {
          error.append(Constants.ImportCmsUser.ERROR_PHONE_INVALID + "\n");
        }

        String branchCode = ImportExcelUtil.getCellValue(row, 5);
        cmsUserEntity.setBranchCode(branchCode);
        brandCodesInSheet.add(branchCode);
        if (StringUtils.isEmpty(branchCode)) {
          error.append(Constants.ImportCmsUser.ERROR_BRANCH_CODE_NOT_EMPTY + "\n");
        }

        String role = ImportExcelUtil.getCellValue(row, 7);
        cmsUserEntity.setRole(role);
        if (StringUtils.isEmpty(role)) {
          error.append(Constants.ImportCmsUser.ERROR_ROLE_NOT_EMPTY + "\n");
        }
//                else {
//                    List<String> roleInputLst = new ArrayList<>(Arrays.asList(role.split(";")));
//                    if (roleInputLst.retainAll(roles)) {
//                        error.append(String.format(Constants.ImportCmsUser.ERROR_ROLE_INVALID, role) + "\n");
//                    }
//                    if (role.contains(roleConfig.getBm())) {
//                        leaders.add(cmsUserEntity);
//                        emailLeader.add(email);
//                    }
//                }

        String leaderEmail = ImportExcelUtil.getCellValue(row, 6);
        cmsUserEntity.setLeaderEmail(leaderEmail.toLowerCase());
        emailLeadersInSheet.add(leaderEmail);
        if (!(role.contains(roleConfig.getAdmin()) || role.contains(roleConfig.getBm()))) {
          if (StringUtils.isEmpty(leaderEmail)) {
            error.append(Constants.ImportCmsUser.ERROR_LEADER_EMAIL_NOT_EMPTY + "\n");
          } else if (leaderEmail.length() > Constants.ImportCmsUser.MAX_LENGTH_EMAIL) {
            error.append(String.format(Constants.ImportCmsUser.ERROR_LEADER_EMAIL_EXCEED_MAX_LENGTH,
                Constants.ImportCmsUser.MAX_LENGTH_EMAIL) + "\n");
          } else if (!EmailValidator.getInstance().isValid(leaderEmail)) {
            error.append(Constants.ImportCmsUser.ERROR_LEADER_EMAIL_INVALID + "\n");
          }
        }
        if (mapImplId.get(emplId) != null) {
          error.append(String.format(Constants.ImportCmsUser.ERROR_EMPLID_DUPLICATE,
              mapImplId.get(emplId) + 1) + "\n");
        } else {
          mapImplId.put(emplId, i);
        }

        if (mapEmail.get(email) != null) {
          error.append(
              String.format(Constants.ImportCmsUser.ERROR_EMAIL_DUPLICATE, mapEmail.get(email) + 1)
                  + "\n");
        } else {
          mapEmail.put(email, i);
        }

        if (!StringUtils.isEmpty(error.toString())) {
          cellError.setCellValue(error.toString());
          checkImport = false;
        }

        cmsUsersImport.add(cmsUserEntity);
      }
    }
    if (!checkImport) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      workbook.write(bos);
      return bos.toByteArray();
    }

    Set<CmsUserEntity> usersInDb = cmsUserRepository.findByEmails(emailsInSheet);
    Map<String, CmsUserEntity> mapFromEmailToUserInDb = usersInDb.stream()
        .collect(Collectors.toMap(CmsUserEntity::getEmail, Function.identity()));

    List<OrganizationEntity> organizations = organizationRepository.findByBrandCode(
        brandCodesInSheet);
    Map<String, OrganizationEntity> mapFromCodeToOrg = organizations.stream()
        .collect(Collectors.toMap(OrganizationEntity::getCode, Function.identity()));

    emailLeadersInSheet.removeAll(emailLeader);
    leaders.addAll(cmsUserRepository.findByEmails(emailLeadersInSheet));
    Map<String, CmsUserEntity> mapFromEmailToLeader = leaders.stream()
        .collect(Collectors.toMap(CmsUserEntity::getEmail, Function.identity()));

    int index = 0;
    for (CmsUserEntity userImport : cmsUsersImport) {
      index++;
      Cell cellError = worksheet.getRow(index).createCell(8);
      cellError.setCellValue("");
      StringBuilder error = new StringBuilder();
      userImport.setStatus(CmsUserStatusEnum.ACTIVE);

      // email đã được sử dụng bởi user khác
      CmsUserEntity userInDb = mapFromEmailToUserInDb.get(userImport.getEmail());
      if (userInDb != null && !userImport.getEmplId().equals(userInDb.getEmplId())) {
        error.append(
            String.format(Constants.ImportCmsUser.ERROR_EMAIL_EXIST, userImport.getEmail()) + "\n");
      }

      // gan gia tri leader empl id cho user
      CmsUserEntity leaderEntity = mapFromEmailToLeader.get(userImport.getLeaderEmail());
      if (leaderEntity != null) {
        userImport.setLeaderEmplId(leaderEntity.getEmplId());
      } else if (!(userImport.getRole().contains(roleConfig.getBm()) || userImport.getRole()
          .contains(roleConfig.getAdmin()))) {
        error.append(String.format(Constants.ImportCmsUser.ERROR_LEADER_EMAIL_NOT_EXIT,
            userImport.getLeaderEmail()) + "\n");
      }

      if (mapFromCodeToOrg.get(userImport.getBranchCode()) == null) {
        error.append(String.format(Constants.ImportCmsUser.ERROR_BRANCH_CODE_NOT_FOUND,
            userImport.getBranchCode()) + "\n");
      }

      if (!StringUtils.isEmpty(error.toString())) {
        cellError.setCellValue(error.toString());
        checkImport = false;
      }
    }

    if (checkImport) {
      cmsUserRepository.saveAll(cmsUsersImport);
      return new byte[0];
    } else {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      workbook.write(bos);
      return bos.toByteArray();
    }
  }

  @Override
  public CmsUser createCmsUser(CmsUser cmsUser) {
    CmsUserEntity cmsUserEntity = CmsUserMapper.INSTANCE.toEntity(cmsUser);
    String branchName = null;
    Optional<CmsUserEntity> opCmsUserEntity = cmsUserRepository.findByEmail(
        cmsUserEntity.getEmail());
    if (opCmsUserEntity.isPresent() && !opCmsUserEntity.get().getEmplId()
        .equals(cmsUserEntity.getEmplId())) {
      throw new ApplicationException(ErrorEnum.CMS_USER_EMAIL_EXIST);
    }
    opCmsUserEntity = cmsUserRepository.findByEmail(cmsUserEntity.getLeaderEmail());
    if (opCmsUserEntity.isPresent()) {
      cmsUserEntity.setLeaderEmplId(opCmsUserEntity.get().getEmplId());
    }
    OrganizationEntity organizationEntity = organizationRepository.findByCode(
        cmsUserEntity.getBranchCode());
    if (organizationEntity == null) {
      throw new ApplicationException(ErrorEnum.BRANCH_CODE_NOT_FOUND);
    } else {
      branchName = organizationEntity.getName();
    }
    List<String> roles = Arrays.asList(roleConfig.getAdmin(), roleConfig.getBm(),
        roleConfig.getRm());
    String[] lstRole = cmsUser.getRole().split(";");
    List<String> strListRole = new ArrayList<String>(Arrays.asList(lstRole));
    strListRole.removeIf(n -> roles.contains(n));
    if (strListRole != null && strListRole.size() > 0) {
      throw new ApplicationException(ErrorEnum.ROLE_CMS_USER_NOT_FOUND,
          String.valueOf(strListRole));
    }
    cmsUserEntity.setStatus(CmsUserStatusEnum.ACTIVE);
    String userId = AuthorizationUtil.getUserId();
    cmsUserEntity.setCreatedBy(userId);
    cmsUserEntity.setUpdatedBy(userId);
    CmsUser cmsUserResult = CmsUserMapper.INSTANCE.toModel(cmsUserRepository.save(cmsUserEntity));
    cmsUserResult.setBranchName(branchName);
    return cmsUserResult;
  }

  @Override
  public List<CmsUser> getAllCmsUser() {
    List<CmsUserEntity> cmsUserEntities = cmsUserRepository.findAll();
    return CmsUserMapper.INSTANCE.toModels(cmsUserEntities);
  }

  @Override
  public List<String> getAllChildrenUser() {
    Optional<CmsUserEntity> userLogin = cmsUserRepository.findByEmail(AuthorizationUtil.getEmail());
    if (!userLogin.isPresent()) {
      return Collections.singletonList("0");
    }

    List<CmsUserEntity> cmsUserEntities = cmsUserRepository.findByBranchCode(
        userLogin.get().getBranchCode());

    Map<String, Integer> mapEmplId = new HashMap<>();
    Map<Integer, String> mapIndex = new HashMap<>();
    Map<String, String> mapUserEmail = new HashMap<>();

    for (int i = 0; i < cmsUserEntities.size(); i++) {
      mapEmplId.put(cmsUserEntities.get(i).getEmplId(), i + 1);
      mapIndex.put(i + 1, cmsUserEntities.get(i).getEmplId());
      mapUserEmail.put(cmsUserEntities.get(i).getEmail(), cmsUserEntities.get(i).getEmplId());
    }

    Graph graph = new Graph(cmsUserEntities.size() + 1);
    cmsUserEntities.forEach(x -> {
      String parent = mapUserEmail.get(x.getLeaderEmail());
      parent = StringUtils.isEmpty(parent) ? x.getEmplId() : parent;
      graph.addEdge(mapEmplId.get(parent), mapEmplId.get(x.getEmplId()));
    });

    graph.DFS(mapEmplId.get(userLogin.get().getEmplId()));

    List<Integer> ways = graph.getWays();

    List<String> children = ways.stream()
        .filter(x -> !mapEmplId.get(userLogin.get().getEmplId()).equals(x))
        .map(mapIndex::get)
        .collect(Collectors.toList());

    //User login always at the top of the list
    children.add(0, userLogin.get().getEmplId());

    return children;
  }

  @Override
  public List<String> getAllChildrenUser(HttpServletRequest httpRequest) throws IOException {
    List<Role> roles = commonCMSService.getAuthorInfo(httpRequest.getHeader("Authorization"));
    List<String> picRms = Collections.EMPTY_LIST;
    if (!AuthorizationUtil.containRole(roles, environmentProperties.getCj5Admin())) {
      picRms = getAllChildrenUser();
    }
    return picRms;
  }
}
