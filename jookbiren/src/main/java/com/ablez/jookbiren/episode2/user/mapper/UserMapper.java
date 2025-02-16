//package com.ablez.jookbiren.episode2.user.mapper;
//
//import com.ablez.jookbiren.episode1.user.entity.UserEp01;
//import com.ablez.jookbiren.episode2.user.dto.UserDto.UserBriefInfoDto;
//import com.ablez.jookbiren.episode2.user.entity.UserEp02;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserMapper {
//    public UserBriefInfoDto makeUserBriefInfoDto(UserEp01 user) {
//        UserInfoEp01 userInfo = user.getUserInfo();
//        OrderInfo orderInfo = userInfo.getOrderInfo();
//        BuyerInfo buyerInfo = orderInfo.getBuyerInfo();
//        return UserBriefInfoDto.builder()
//                .name(buyerInfo.getName())
//                .code(userInfo.getCode())
//                .phone(buyerInfo.getPhone())
//                .platform(orderInfo.getPlatform().getPlatform())
//                .nickname(findNickname(orderInfo, buyerInfo))
//                .orderNumber(orderInfo.getOrderNumber())
//                .amount(orderInfo.getAmount())
//                .episode(1)
//                .address(buyerInfo.getAddress())
//                .createdAt(orderInfo.getCreatedAt())
//                .answeredTime(user.getAnswerTime())
//                .criminal1(SUSPECT_EP01.get(user.getCriminal()))
//                .criminal2("")
//                .firstLoginTime(user.getFirstLoginTime())
//                .solvedQuizCount(user.getSolvedQuizCount())
//                .score(user.getScore())
//                .build();
//    }
//
//    public UserBriefInfoDto makeUserBriefInfoDto(UserEp02 user) {
//        UserInfoEp02 userInfo = user.getUserInfo();
//        OrderInfo orderInfo = userInfo.getOrderInfo();
//        BuyerInfo buyerInfo = orderInfo.getBuyerInfo();
//        return UserBriefInfoDto.builder()
//                .name(buyerInfo.getName())
//                .code(userInfo.getCode())
//                .phone(buyerInfo.getPhone())
//                .platform(orderInfo.getPlatform().getPlatform())
//                .nickname(findNickname(orderInfo, buyerInfo))
//                .orderNumber(orderInfo.getOrderNumber())
//                .amount(orderInfo.getAmount())
//                .episode(2)
//                .address(buyerInfo.getAddress())
//                .createdAt(orderInfo.getCreatedAt())
//                .answeredTime(user.getAnswerTime())
//                .criminal1(SUSPECT1.get(user.getCriminal1()))
//                .criminal2(SUSPECT2.get(user.getCriminal2()))
//                .firstLoginTime(user.getFirstLoginTime())
//                .solvedQuizCount(user.getSolvedQuizCount())
//                .score(user.getScore())
//                .build();
//    }
//
//    private String findNickname(OrderInfo orderInfo, BuyerInfo buyerInfo) {
//        if (orderInfo.getPlatform() == Platform.NAVER) {
//            return buyerInfo.getNaverNickname();
//        }
//        return buyerInfo.getTumblbugNickname();
//    }
//}
