package com.ezen.management.controller;

import com.ezen.management.domain.Notice;
import com.ezen.management.dto.MemberDTO;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
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
    public String notice(Model model, PageRequestDTO pageRequestDTO){

//        model.addAttribute("pageResponseDTO", )

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
    public String writeGet(){
        return "member/notice/write";
    }

    @GetMapping("/read")
    public String read(@RequestParam int idx, Model model){

        Notice byId = noticeService.findById(idx);

        log.info("notice : {}", byId);

        Optional<Member> memberById = memberRepository.findById(byId.getWriter());
        Member member = memberById.get();

        log.info("getCategory() : {}", byId.getCategory());

        NoticeDTO noticeDTO = NoticeDTO.builder()
                        .title(byId.getTitle())
                        .content(byId.getContent())
                        .writer(member.getId())
                        .writerName(member.getName())
                        .categoryName(byId.getCategory().getName())
                        .modDate(byId.getModDate())
                        .build();

        log.info("noticeDTO : {}", noticeDTO);

        model.addAttribute("noticeDTO", noticeDTO);

        return "/member/notice/read";
    }

    @PutMapping("/write")
    public String modify(NoticeDTO noticeDTO) {

//        여기에 수정 로직

        return "member/notice/read";
    }




}
