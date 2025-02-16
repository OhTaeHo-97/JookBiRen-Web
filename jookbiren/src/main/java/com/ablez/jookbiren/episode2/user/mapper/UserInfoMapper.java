//package com.ablez.jookbiren.episode2.user.mapper;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserInfoMapper {
//    public List<UserInfoDto> makeUserInfoListEp1(List<UserInfoEp01> userInfos) {
//        return userInfos.stream().map(this::makeUserInfoDto).collect(Collectors.toList());
//    }
//
//    public List<UserInfoDto> makeUserInfoListEp2(List<UserInfoEp02> userInfos) {
//        return userInfos.stream().map(this::makeUserInfoDto).collect(Collectors.toList());
//    }
//
//    private UserInfoDto makeUserInfoDto(UserInfoEp01 userInfo) {
//        return UserInfoDto.builder()
//                .id(userInfo.getUserInfoId())
//                .phone(userInfo.getOrderInfo().getBuyerInfo().getPhone())
//                .name(userInfo.getOrderInfo().getBuyerInfo().getName())
//                .platform(userInfo.getOrderInfo().getPlatform().getPlatform())
//                .orderId(userInfo.getOrderInfo().getOrderNumber())
//                .nickname(findNickname(userInfo.getOrderInfo()))
//                .code(userInfo.getCode())
//                .build();
//    }
//
//    private UserInfoDto makeUserInfoDto(UserInfoEp02 userInfo) {
//        return UserInfoDto.builder()
//                .id(userInfo.getUserInfoId())
//                .phone(userInfo.getOrderInfo().getBuyerInfo().getPhone())
//                .name(userInfo.getOrderInfo().getBuyerInfo().getName())
//                .platform(userInfo.getOrderInfo().getPlatform().getPlatform())
//                .orderId(userInfo.getOrderInfo().getOrderNumber())
//                .nickname(findNickname(userInfo.getOrderInfo()))
//                .code(userInfo.getCode())
//                .build();
//    }
//
//    private String findNickname(OrderInfo orderInfo) {
//        if (orderInfo.getPlatform() == Platform.NAVER) {
//            return orderInfo.getBuyerInfo().getNaverNickname();
//        }
//        return orderInfo.getBuyerInfo().getTumblbugNickname();
//    }
//}
