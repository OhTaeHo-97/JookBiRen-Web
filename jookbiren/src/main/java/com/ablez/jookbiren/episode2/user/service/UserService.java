package com.ablez.jookbiren.episode2.user.service;

import static com.ablez.jookbiren.episode2.answer.utils.AnswerConstant.SUSPECT1;
import static com.ablez.jookbiren.episode2.answer.utils.AnswerConstant.SUSPECT2;
import static com.ablez.jookbiren.episode2.utils.JookBiRenConstant.STAR_QUIZ_COUNT;

import com.ablez.jookbiren.episode2.user.dto.UserDto.CodeDto;
import com.ablez.jookbiren.episode2.user.dto.UserDto.EndingDto;
import com.ablez.jookbiren.episode2.user.dto.UserDto.InfoDto;
import com.ablez.jookbiren.episode2.user.dto.UserDto.LoginDto;
import com.ablez.jookbiren.episode2.user.dto.UserDto.StatusDto;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.episode2.user.repository.UserQuerydslRepository;
import com.ablez.jookbiren.episode2.user.repository.UserRepository;
import com.ablez.jookbiren.exception.BusinessLogicException;
import com.ablez.jookbiren.exception.ExceptionCode;
import com.ablez.jookbiren.security.interceptor.JwtParseInterceptor;
import com.ablez.jookbiren.security.jwt.JwtDto.TokenDto;
import com.ablez.jookbiren.security.jwt.JwtTokenizer;
import com.ablez.jookbiren.userInfo.entity.UserInfo;
import com.ablez.jookbiren.userInfo.service.UserInfoService;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private static final int EXCEL_COLUMN_LENGTH = 10;

    private final UserRepository userJpaRepository;
    private final UserInfoService userInfoService;
    private final JwtTokenizer jwtTokenizer;
    private final UserQuerydslRepository userRepository;
//    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserEp02 findCurrentUser(String accessToken) {
        long userId = Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername());
        UserEp02 user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));

        accessToken = accessToken.substring(7);
        if (!user.getAccessToken().equals(accessToken)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_LOGIN_USER);
        }
        return user;
    }

    public LoginDto login(CodeDto codeInfo) {
        UserInfo userInfo = userInfoService.findByCodeEp2(codeInfo.getCode());
        UserEp02 user = userInfo.getUserEp02();

        user.updateFirstLoginTime();

//        String userId = String.valueOf(user.getUserId());
        String accessToken = jwtTokenizer.generateAccessToken(String.valueOf(userInfo.getUserInfoId()),
                String.valueOf(user.getUserId()));
//        RefreshToken refreshToken = saveRefreshToken(userId);

        user.setAccessToken(accessToken);

        return new LoginDto(new TokenDto(accessToken), new EndingDto(user.getAnswerTime() != null));
    }

    @Transactional(readOnly = true)
    public StatusDto canPickSuspect() {
        UserEp02 user = findByUserId(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()));

        int answerStatus = user.getAnswerStatusCode();
        return new StatusDto(answerStatus == (Math.pow(2, STAR_QUIZ_COUNT) - 1));
    }

    @Transactional(readOnly = true)
    public UserEp02 findByUserId(long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER_ID));
    }

    @Transactional(readOnly = true)
    public InfoDto getInfo() {
        UserEp02 user = findByUserId(Long.parseLong(JwtParseInterceptor.getAuthenticatedUsername()));

        LocalDateTime firstLoginTime = user.getFirstLoginTime();
        LocalDateTime answerTime = user.getAnswerTime();
        Duration duration = Duration.between(firstLoginTime, answerTime);

        return new InfoDto(user.getScore(), duration.toSeconds(), user.getAnswerCount(), user.getSolvedQuizCount(),
                SUSPECT1.get(user.getCriminal1()), SUSPECT2.get(user.getCriminal2()));
    }

//    public void generateBuyerAndOrderInfo(MultipartFile file) {
//        // 연락처 실명 플랫폼 주문번호 닉네임 주소 구매가격 구매날짜 생성할코드수
//        readExcel(file);
//        /// Todo: 이미 있는 구매자가 들어왔을 때 구매자 정보 업데이트
//    }

//    public void readExcel(MultipartFile file) {
//        String fileExtension = findFileExtension(file);
//        Workbook workbook = null;
//        try {
//            workbook = makeWorkbook(fileExtension, file);
//
//            // 엑셀파일에서 첫 번째 시트 불러오기
//            Sheet worksheet = workbook.getSheetAt(0);
//
//            // 각 행 읽어 처리
//            for (int rowIdx = 0; rowIdx < worksheet.getPhysicalNumberOfRows();
//                 rowIdx++) { // getPhysicalNumberOfRow : 행의 개수를 불러오는 메소드
//                Row row = worksheet.getRow(rowIdx);
//                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//
//                if (row != null) {
//                    List<String> infos = processExcelData(dateFormatter, row);
//
//                    // 1. 연락처를 통해 동일한 구매자가 있는지 확인
//                    Optional<BuyerInfo> optionalBuyerInfo = buyerInfoService.findByPhone(infos.get(0));
//                    // 2. 동일한 연락처의 구매자가 있다면 그 사람의 데이터만 업데이트
//                    if (optionalBuyerInfo.isPresent()) {
//                        // 구매자 정보 변경
//                        updateBuyerInfo(optionalBuyerInfo.get(), infos);
//                    } else {// 3. 동일한 연락처의 구매자가 없다면 새로 추가
//                        insertNewBuyer(infos);
//                    }
//                }
//            }
//
//            workbook.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void insertNewBuyer(List<String> infos) {
//        PostBuyerInfoDto buyerInfo = PostBuyerInfoDto.builder()
//                .phone(infos.get(0))
//                .name(infos.get(1))
//                .platform(infos.get(2))
//                .nickname(infos.get(4))
//                .address(infos.get(5))
//                .build();
//        PostOrderInfoDto orderInfo = PostOrderInfoDto.builder()
//                .orderNumber(infos.get(3))
//                .amount(Integer.parseInt(infos.get(6)))
//                .platform(infos.get(2))
//                .episode(Integer.parseInt(infos.get(9)))
//                .createdAt(LocalDateTime.parse(infos.get(7),
//                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")))
//                .build();
//        buyerInfoService.insertBuyerInfo(Integer.parseInt(infos.get(8)), buyerInfo, orderInfo);
//    }

//    private void updateBuyerInfo(BuyerInfo buyerInfo, List<String> infos) {
//        buyerInfo.setName(infos.get(1));
//        Platform platform = Platform.findPlatform(infos.get(2));
//        if (platform == Platform.NAVER) {
//            buyerInfo.setNaverNickname(infos.get(4));
//        } else if (platform == Platform.TUMBLBUG) {
//            buyerInfo.setTumblbugNickname(infos.get(4));
//        }
//        buyerInfo.setAddress(infos.get(5));
//
//        PostOrderInfoDto orderInfo = PostOrderInfoDto.builder()
//                .orderNumber(infos.get(3))
//                .amount(Integer.parseInt(infos.get(6)))
//                .platform(infos.get(2))
//                .episode(Integer.parseInt(infos.get(9)))
//                .createdAt(LocalDateTime.parse(infos.get(7),
//                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")))
//                .build();
//
//        buyerInfoService.insertBuyerInfoToExistedBuyer(Integer.parseInt(infos.get(8)), orderInfo, buyerInfo);
//    }
//
//    private String findFileExtension(MultipartFile file) {
//        return FilenameUtils.getExtension(file.getOriginalFilename());
//    }
//
//    private Workbook makeWorkbook(String fileExtension, MultipartFile file) throws IOException {
//        if (fileExtension.equals("xls")) {
//            return new HSSFWorkbook(file.getInputStream());
//        } else {
//            return new XSSFWorkbook(file.getInputStream());
//        }
//    }
//
//    private List<String> processExcelData(SimpleDateFormat dateFormatter, Row row) {
//        // 각 열의 데이터를 저장할 리스트
//        List<String> infos = new ArrayList<>();
//        // 각 열의 데이터를 읽기(연락처 실명 플랫폼 주문번호 닉네임 주소 구매가격 구매날짜 생성할코드수)
//        for (int colIdx = 0; colIdx < EXCEL_COLUMN_LENGTH; colIdx++) {
//            Cell cell = row.getCell(colIdx);
//            if (cell == null) {
//                continue;
//            } else {
////                value = processCellData(cell, dateFormatter);
//                infos.add(processCellData(cell, dateFormatter));
//            }
//
//            // 연락처 실명 플랫폼 주문번호 닉네임 주소 구매가격 구매날짜 생성할코드수
//            // BuyerInfo -> 연락처, 실명, 플랫폼, 닉네임, 주소
//            // OrderInfo -> 주문번호, 구매가격, 플랫폼, 구매날짜
//            // UserInfoEp02 -> 생성할 코드 수 -> UserEp02에도 추가
//        }
//
//        return infos;
//    }
//
//    private String processCellData(Cell cell, SimpleDateFormat dateFormatter) {
//        if (cell.getCellType() == CellType.FORMULA) {
//            return cell.getCellFormula();
//        }
//        if (cell.getCellType() == CellType.NUMERIC) {
//            return processNumericData(cell, dateFormatter);
//        }
//        if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.BLANK
//                || cell.getCellType() == CellType.ERROR) {
//            return cell.getStringCellValue() + "";
//        }
//        return cell.getStringCellValue();
//    }
//
//    private String processNumericData(Cell cell, SimpleDateFormat dateFormatter) {
//        if (HSSFDateUtil.isCellDateFormatted(cell)) {
//            return dateFormatter.format(cell.getDateCellValue());
//        } else {
//            double numericCellValue = cell.getNumericCellValue();
//            if (numericCellValue == Math.rint(numericCellValue)) {
//                return String.valueOf((int) numericCellValue);
//            } else {
//                return String.valueOf(numericCellValue);
//            }
//        }
//    }

    // 유저 관련 정보 엑셀로 표시
//    @Transactional(readOnly = true)
//    public List<UserBriefInfoDto> findAll() {
//        List<UserEp02> users = userRepository.findAll();
//        return users.stream().map(userMapper::makeUserBriefInfoDto).collect(Collectors.toList());
//    }

    // 유저 전체 표시
//    @Transactional(readOnly = true)
//    public MultipleResponseDto findAllUsers(int episode, int page, int size) {
//        return userInfoService.findAllUsers(episode, page, size);
//    }
}
