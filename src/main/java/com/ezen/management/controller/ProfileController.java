package com.ezen.management.controller;

import com.ezen.management.domain.Member;
import com.ezen.management.dto.MemberDTO;
import com.ezen.management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/profile")
    public ResponseEntity<MemberDTO> testUser(Principal principal) {

        Optional<Member> member = memberRepository.findById(principal.getName());

        ModelMapper modelMapper = new ModelMapper();

        MemberDTO memberDTO = member.map(m -> {
            MemberDTO dto = modelMapper.map(m, MemberDTO.class);
            dto.setPwd("");
            return dto;
        }).orElse(null);

        return ResponseEntity.ok(memberDTO);
    }
}
