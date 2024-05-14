package com.ezen.management.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ezen.management.domain.Member;

import java.time.Duration;
import java.time.LocalDateTime;
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

        return "/member/notice/index";
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
    public String read(@RequestParam int idx, Model model){

        NoticeDTO noticeDTO = noticeService.findById(idx);

        model.addAttribute("noticeDTO", noticeDTO);

        return "/member/notice/read";
    }

    @PutMapping("/write")
    public String modify(NoticeDTO noticeDTO) {

//        여기에 수정 로직

        return "member/notice/read";
    }




}
