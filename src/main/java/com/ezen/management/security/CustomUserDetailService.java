package com.ezen.management.security;

import com.ezen.management.domain.Member;
import com.ezen.management.dto.MemberSecurityDTO;
import com.ezen.management.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.getByIdWithRoles(username);


        if(result.isEmpty()){
            throw new UsernameNotFoundException("Username Not Found......");
        }

        Member member = result.get();

        return new MemberSecurityDTO(
                member.getId(),
                member.getPwd(),
                member.getName(),
                member.getUuid(),
                member.getFileName(),
                true,
                true,
                true,
                true,
                member.getRoleSet()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));
    }
}
