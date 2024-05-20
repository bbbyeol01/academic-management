package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.*;
import com.ezen.management.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    private final StudentService studentService;

    private final SurveyAnswerService surveyAnswerService;

    private final LessonService lessonService;

    /*=========================설문관리 RU=========================*/
    /*======================설문관리 CD는 제거======================*/

    /**
     * 로그 생성 수정해야함
     */
    @GetMapping("/member/survey/read")
    public String read(Model model, @RequestParam("round")int round) {

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        // 조회된 Survey 목록을 모델에 추가
        model.addAttribute("surveys", surveyList);
        model.addAttribute("round", round);

        // 뷰 페이지로 이동합니다.
        return "/member/survey/read";
    }

    /**
     * 로그 생성 수정해야함
     */
    @GetMapping("/member/survey/modify")
    public String modifyGet(Model model, @RequestParam("round")int round){

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        // 조회된 Survey 목록을 모델에 추가
        model.addAttribute("surveys", surveyList);

        return "/member/survey/modify";
    }

    /**
     * 로그 생성 수정해야함
     */
    @PostMapping("/member/survey/modify")
    public String modifyPost(@ModelAttribute("surveyDtoList") SurveyDtoList surveyDtoList, @RequestParam("round")int round){

        round = surveyService.modify(surveyDtoList, round);

        return "redirect:/member/survey/read?round=" + round;
    }

    /*=======================설문작성(학생)=======================*/

    /**
     * 로그 생성 수정해야함
     */
    @PostMapping("/student/survey")
    public String survey(Model model, StudentDTO studentDTO){

        Student student = studentService.findById(studentDTO.getIdx());

        int round = student.getSurvey() + 1;

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        model.addAttribute("surveys", surveyList);
        model.addAttribute("student", student);
        model.addAttribute("round", round);

        return "/student/survey";
    }

    /**
     * 로그 생성 수정해야함
     */
    @PostMapping("/student/survey/insert")
    public String insert(SurveyAnswerDTO surveyAnswerDTO, StudentDTO studentDTO){

        int result = surveyAnswerService.insert(surveyAnswerDTO, studentDTO);

        return "redirect:/student";
    }

    /*=========================설문결과=========================*/

    /**
     * 로그 생성 수정해야함
     */
    @GetMapping("/member/lesson/survey/result")
    public String result(Model model, @RequestParam("lessonIdx")Long lessonIdx, @RequestParam("round")int round){

        //수업
        Lesson lesson = lessonService.findById(lessonIdx);

        //설문(질문과보기)
        List<SurveyDTO> surveyDTOList = surveyService.readAllByRound(round);

        //설문(결과 : 객관식)
        List<SurveyResultDTO> surveyResultDTOList = surveyAnswerService.calculateSumOfAnswers(round, lessonIdx);

        //설문(주관식 때문에 필요)
        List<SurveyAnswerDTO> surveyAnswerDTOList = surveyAnswerService.findByLessonIdxAndRound(lessonIdx,round);

        model.addAttribute("round", round);
        model.addAttribute("lesson", lesson);
        model.addAttribute("surveyDTOList", surveyDTOList);
        model.addAttribute("surveyResultDTOList", surveyResultDTOList);
        model.addAttribute("surveyAnswerDTOList", surveyAnswerDTOList);

        return "/member/lesson/survey/result";
    }

}
