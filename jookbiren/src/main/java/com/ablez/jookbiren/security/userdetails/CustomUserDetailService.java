package com.ablez.jookbiren.security.userdetails;

import com.ablez.jookbiren.userInfo.entity.UserInfo;
import com.ablez.jookbiren.userInfo.repository.UserInfoQuerydslRepository;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserInfoQuerydslRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findById(Long.parseLong(username))
                .orElseThrow(() -> {
                    log.error("error: {}", "없는 회원입니다.");
                    throw new NoSuchElementException("없는 회원입니다.");
                });
        return CustomUserDetails.of(user);
    }
}
