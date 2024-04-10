package com.ezen.management.dto;

import com.ezen.management.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String name;
    private String uuid;
    private String fileName;

    public MemberSecurityDTO(String username, String password, String name, String uuid, String fileName, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.uuid = uuid;
        this.name = name;
        this.fileName = fileName;

    }


}
