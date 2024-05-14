package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.MemberRole;
import com.ezen.management.domain.Notice;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.NoticeRepository;
import com.ezen.management.service.LessonService;
import com.ezen.management.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final NoticeService noticeService;
    private final LessonService lessonService;

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
    public String memberIndex(Model model){
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


//        공지사항
        List<NoticeDTO> indexList = noticeService.getIndexList();

        model.addAttribute("noticeList", indexList);


//        진행중 수업
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<Lesson> responseDTO = lessonService.ongoingLesson(pageRequestDTO, username);
        model.addAttribute("LessonList", responseDTO.getDtoList());

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
