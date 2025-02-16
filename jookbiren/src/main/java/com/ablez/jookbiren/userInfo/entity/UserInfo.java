package com.ablez.jookbiren.userInfo.entity;

import com.ablez.jookbiren.episode1.user.entity.UserEp01;
import com.ablez.jookbiren.episode2.user.entity.UserEp02;
import com.ablez.jookbiren.security.entity.Authority;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;
    @Column(nullable = false)
    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Authority> authorities = new HashSet<>();

    @OneToOne(mappedBy = "userInfo", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private UserEp01 userEp01;
    @OneToOne(mappedBy = "userInfo", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private UserEp02 userEp02;

    public List<String> getRoles() {
        return authorities.stream().map(Authority::getRole).collect(Collectors.toList());
    }

    public void addRole(Authority authority) {
        this.authorities.add(authority);
    }
}
