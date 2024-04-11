package com.ezen.management.controller;

import com.ezen.management.domain.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "/index";
    }


    @GetMapping("/member/login")
    public void loginGET(String error, String logout){
        log.info("Member login get......");
    }


    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN', 'TEACHER')")
    @GetMapping("/member")
    public String memberIndex(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String username = ((UserDetails) principal).getUsername();
        Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
        String password = ((UserDetails) principal).getPassword();

        log.info(username);
        log.info(password);

        authorities.forEach(auth -> {
            log.info("auth : {}", auth);
            log.info("getAuthority : {}", auth.toString().equals("ROLE_MASTER"));
            log.info("MemberRole.MASTER.toString() {} ", MemberRole.MASTER.toString());
            log.info("is that MASTER? {}", auth.toString().equals(MemberRole.MASTER.toString()));
        });

        return "/member/index";
    }


//    로그인 리다이렉트
//    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN', 'TEACHER')")
    @GetMapping("/redirect")
    public String redirect(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            String authorityName = authority.getAuthority();

            log.info("authorityName : {}",  authorityName);


            return switch (authorityName) {
                case "ROLE_MASTER", "ROLE_ADMIN" -> "redirect:/member";
                case "ROLE_TEACHER" -> "redirect:/lesson";
                default -> "redirect:/member/login?error";
            };
        }

        return "redirect:/member";

    }

//    @GetMapping("/logout")
//    public String logout(){
//
//        return "redirect:/";
//    }







}
