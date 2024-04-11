package com.ezen.management.controller;

import com.ezen.management.domain.*;
import com.ezen.management.dto.*;
import com.ezen.management.repository.CurriculumRepository;
import com.ezen.management.repository.MemberRepository;
import com.ezen.management.repository.SubjectHoldRepository;
import com.ezen.management.repository.SubjectTestRepository;
import com.ezen.management.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final TrainingService trainingService;
    private final QuestionNameService questionNameService;
    private final StudentService studentService;

    private final SubjectHoldRepository subjectHoldRepository;

    //전체 수업 목록
    @GetMapping("")
    public String lesson(Model model, PageRequestDTO pageRequestDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails)principal;
        String userId = ((UserDetails) principal).getUsername();

        PageResponseDTO<Lesson> responseDTO = trainingService.searchLesson(pageRequestDTO, userId);
        model.addAttribute("responseDTO", responseDTO);

        List<QuestionName> questionName = questionNameService.findAll();
        model.addAttribute("questionName", questionName);

        return "/lesson/index";
    }

    //진행중인 수업 목록
    @GetMapping("/ongoing")
    public String ongoing(Model model, PageRequestDTO pageRequestDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetails) principal).getUsername();


        PageResponseDTO<Lesson> responseDTO = lessonService.ongoingLesson(pageRequestDTO, userId);
        model.addAttribute("responseDTO", responseDTO);

        List<QuestionName> questionName = questionNameService.findAll();
        model.addAttribute("questionName", questionName);

        return "/lesson/ongoing";
    }

    //완료된 수업 목록
    @GetMapping("/end")
    public String end(Model model, PageRequestDTO pageRequestDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = ((UserDetails) principal).getUsername();

        PageResponseDTO<Lesson> responseDTO = lessonService.endLesson(pageRequestDTO, userId);
        model.addAttribute("responseDTO", responseDTO);

        List<QuestionName> questionName = questionNameService.findAll();
        model.addAttribute("questionName", questionName);

        return "/lesson/end";
    }

    //수업상세
    //수업유형, 수업과정, 수업세부정보 -> 클릭으로 해당 수업의 학생들까지 ㄱㄱ
    @GetMapping(value = "/detail")
    public String lessonDetail(Model model, @RequestParam("idx") Long idx){

        //보유과목
        model.addAttribute("subjectHold", subjectHoldRepository.getSubjectHoldByLesson_idx(idx));

        //인덱스에 해당하는 수업
        model.addAttribute("lesson", trainingService.getLessonByIdx(idx));

        return "/lesson/detail";
    }

    //학생 목록
    @GetMapping(value = "/studentList")
    public String studentList(Model model, @RequestParam("idx") Long idx, PageRequestDTO pageRequestDTO){

        Lesson lesson = trainingService.getLessonByIdx(idx);
        model.addAttribute("lesson", lesson);
        model.addAttribute("responseDTO", studentService.searchStudent(idx, pageRequestDTO));

        log.info("아아아아아" + pageRequestDTO.getPage());

        return "/lesson/studentList";
    }

    //학생 상세
    @GetMapping(value = "/studentDetail")
    public String studentDetail(Model model, @RequestParam("idx") Long idx){
        //과목평가 테스트 가져오기 (학생 인덱스)
        List<SubjectTest> subjectTest = lessonService.searchSubjectTest(idx);
        model.addAttribute("subjectTest", subjectTest);

        model.addAttribute("student", studentService.findById(idx));
        return "/lesson/studentDetail";
    }


    //진행중인 수업 목록(axios)
    @GetMapping("/getOngoing")
    @ResponseBody
    public List<Lesson> getOngoing() {

        List<Lesson> ongoingLesson = lessonService.getOngoingLesson();
        log.info("ongoing lesson : {}", ongoingLesson);

        return lessonService.getOngoingLesson();
    }

    //과목 평가 변경
    @PostMapping(value = "/subjectTest/update")
    public String subjectTestUpdate(@ModelAttribute("subjectTestList") SubjectTestList subjectTestList){

        log.info("컨트롤러 : " + subjectTestList );

        Long idx = lessonService.subjectTestUpdate(subjectTestList);
        log.info("컨트롤러 학생 인덱스 : " + idx);

        return "redirect:/lesson/studentDetail?idx=" + idx;
    }
}
