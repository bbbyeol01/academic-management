package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import com.ezen.management.domain.SurveyAnswer;
import com.ezen.management.dto.*;
import com.ezen.management.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
//@RequestMapping //나중에 어떻게할지 생각해보자
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    private final StudentService studentService;

    private final SurveyAnswerService surveyAnswerService;

    private final LessonService lessonService;

    /*=========================설문관리 CRUD=========================*/

    /**
     * OK
     */
    @GetMapping("member/survey/list")
    public String surveyList(Model model){

        List<SurveyDTO> surveyList = surveyService.surveyList();

        model.addAttribute("surveyList", surveyList);

        return "member/survey/list";
    }

    /**
     * 템플릿 오류 해결해야 함. 경로 문제 같은데...
     */
    @GetMapping("/member/survey/register")
    public String registerGet(Model model, @RequestParam("round")int round){

        return "/member/survey/register";
    }

    /**
     * OK
     */
    @PostMapping("/member/survey/register")
    public String registerPost(@ModelAttribute("surveyDtoList") SurveyDtoList surveyDtoList, @RequestParam("round")int round, @RequestParam("lessonIdx")String lessonIdx){

        log.info("확인 : " + lessonIdx);

        round = surveyService.register(surveyDtoList);

        return "redirect:/member/survey/read?round=" + round;
    }

    /**
     * OK
     */
    @GetMapping("/member/survey/read")
    public String read(Model model, @RequestParam("round")int round) {

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        // 조회된 Survey 목록을 모델에 추가
        model.addAttribute("surveys", surveyList);

        log.info("안찍???????" + surveyList);

        // 뷰 페이지로 이동합니다.
        return "/member/survey/read";
    }

    /**
     * OK
     */
    @GetMapping("/member/survey/remove")
    public String remove(@RequestParam("round")int round) {

        int result = surveyService.deleteAllByRound(round);

        return "redirect:/member/survey/list";
    }

    @GetMapping("/member/survey/modify")
    public String modifyGet(Model model, @RequestParam("round")int round){

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        // 조회된 Survey 목록을 모델에 추가
        model.addAttribute("surveys", surveyList);

        return "/member/survey/modify";
    }

    @PostMapping("/member/survey/modify")
    public String modifyPost(@ModelAttribute("surveyDtoList") SurveyDtoList surveyDtoList, @RequestParam("round")int round){

        round = surveyService.modify(surveyDtoList, round);

        return "redirect:/member/survey/read?round=" + round;
    }

    /*=========================설문작성=========================*/

    /**
     * surveyList가 없으면 뷰에서 어떻게 처리할지???
    */
    @PostMapping("/student/survey")
    public String survey(Model model, StudentDTO studentDTO){

        log.info("되나" + studentDTO);

        Student student = studentService.findById(studentDTO.getIdx());

        int round = student.getSurvey();

        log.info("라운드" + round);

        // 특정 회차(round)에 대한 Survey 목록을 조회
        List<SurveyDTO> surveyList = surveyService.readAllByRound(round);

        // 조회된 Survey 목록을 모델에 추가
        model.addAttribute("surveys", surveyList);

        log.info("찍어보자ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ" + surveyList);

        model.addAttribute("student", student);

        model.addAttribute("round", round);

        return "/student/survey";
    }
    
    /**
     * NULL일때 DB 저장되지 않도록
     */
    @PostMapping("/student/survey/insert")
    public String insert(SurveyAnswerDTO surveyAnswerDTO, StudentDTO studentDTO){

        log.info("확인용 : 여기는 컨트롤러" + surveyAnswerDTO);

        int result = surveyAnswerService.insert(surveyAnswerDTO, studentDTO);

        return "redirect:/student";
    }

    /*=========================설문결과=========================*/


    //1회차
    @GetMapping("/member/lesson/survey/result")
    public String result(Model model, @RequestParam("lessonIdx")Long lessonIdx, @RequestParam("round")int round){
        //수업에서 필요한 정보 : 커리큘럼, 기수, 시작일, 종료일, 교사
        //그리고.. survey와 surveyAnswer 필요

        //수업
        Lesson lesson = lessonService.findById(lessonIdx);

        //설문(질문과보기)
        List<SurveyDTO> surveyDTOList = surveyService.readAllByRound(round);

        log.info("확인용 : " + surveyDTOList);

        //설문(결과 : 객관식)
        List<SurveyResultDTO> surveyResultDTOList = surveyAnswerService.calculateSumOfAnswers(round, lessonIdx);
        log.info("컨트롤러" + surveyResultDTOList);

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
