package com.ezen.management.controller;

import com.ezen.management.domain.MemberRole;
import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.dto.MemberDTO;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.MemberRepository;
import com.ezen.management.repository.NoticeCategoryRepository;
import com.ezen.management.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import com.ezen.management.domain.Member;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberRepository memberRepository;
    private final NoticeCategoryRepository noticeCategoryRepository;


    @GetMapping("")
    public String notice(Model model, PageRequestDTO pageRequestDTO, @RequestParam(required = false) NoticeCategory category){

        PageResponseDTO<NoticeDTO> all = noticeService.findAll(pageRequestDTO, category);

        log.info("noticeDtoList : {}", all.getDtoList());

        model.addAttribute("pageResponseDTO", all);

        return "member/notice/index";
    }

    @Operation(summary = "upload notice", description = "공지사항 작성")
    @PostMapping("/write")
    @ResponseBody
    public Integer writePost(NoticeDTO noticeDTO){

        log.info("noticeDTO : {} ", noticeDTO);

        return noticeService.insert(noticeDTO);

    }


    @GetMapping("/write")
    public String writeGet(Model model){

        List<NoticeCategory> all = noticeCategoryRepository.findAll();
        model.addAttribute("noticeCategoryList", all);

        return "member/notice/write";
    }

    @GetMapping("/read")
    public String read(@RequestParam int idx, Model model) throws AccessDeniedException {

        NoticeDTO noticeDTO = noticeService.findById(idx);

        if(noticeDTO.isAdmin()){
            //        권한 확인 (MASTER, ADMIN)
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails)principal;

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals("ROLE_TEACHER")){
                    throw new AccessDeniedException("접근 권한이 없습니다.");
                }
            }
        }

        model.addAttribute("noticeDTO", noticeDTO);
        return "member/notice/read";
    }

    @PutMapping("/modify")
    @ResponseBody
    public Integer modify(NoticeDTO noticeDTO) {

        log.info("noticeDTO ; {}", noticeDTO);

        return noticeService.update(noticeDTO);
    }

    @GetMapping("/modify")
    public String modify(int idx, Model model){

        NoticeDTO byId = noticeService.findById(idx);

        List<NoticeCategory> all = noticeCategoryRepository.findAll();
        model.addAttribute("noticeCategoryList", all);

        model.addAttribute("noticeDTO", byId);

        return "member/notice/modify";
    }


    @DeleteMapping("/delete/{idx}")
    @ResponseBody
    public Integer delete(@PathVariable int idx){

        return noticeService.delete(idx);
    }


}
