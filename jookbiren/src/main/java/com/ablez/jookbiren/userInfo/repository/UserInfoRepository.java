package com.ablez.jookbiren.userInfo.repository;

import com.ablez.jookbiren.userInfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
