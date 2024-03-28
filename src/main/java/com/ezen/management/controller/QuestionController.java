package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Question;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.QuestionAnswerDTO;
import com.ezen.management.dto.StudentDTO;
import com.ezen.management.service.LessonService;
import com.ezen.management.service.QuestionAnswerService;
import com.ezen.management.service.QuestionService;
import com.ezen.management.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/student")
@RequiredArgsConstructor
public class QuestionController {

    private final StudentService studentService;
    private final LessonService lessonService;
    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;

    @GetMapping("")
    public String index(Model model){


//        List<Lesson> lessonList = lessonService.findAll();
//        현재 진행 중인 수업 리스트
        List<Lesson> lessonList = lessonService.findAllGreaterThan(LocalDate.now());

        model.addAttribute("lessonList", lessonList);

        log.info("!!!!!!!!!!!!!!!!!! student index !!!!!!!!!!!!!!!!!!");
        return "/student/index";
    }

    @PostMapping("/select")
    public String question(Model model, StudentDTO studentDTO){

//        레슨 인덱스와 받아온 이름으로 학생 조회
//        뷰에서는 학생 정보를 보여주고 사전평가/설문조사 중 하나를 클릭하면 거기로 student idx를 넘겨줌
        Student student = studentService.findByLessonIdxAndName(studentDTO.getLessonIdx(), studentDTO.getName());


//        학생이 존재하지 않으면 문제를 풀 수 없음
        if(student == null){
            return "redirect:/student";
        }


        model.addAttribute("lesson", student.getLesson());
        model.addAttribute("student", student);


        return "/student/select";
    }

    @PostMapping("/question")
    public String testPaper(Model model, StudentDTO studentDTO){

        log.info("studentDTO : {} ", studentDTO);

        Student student = studentService.findByLessonIdxAndName(studentDTO.getLessonIdx(), studentDTO.getName());
//        학생이 존재하지 않으면 문제를 풀 수 없음
        if(student == null){
            return "redirect:/student";
        }
//
////        학생이 사전조사를 마쳤으면
//        if(student.isPretest()){
//            return "/student/question/result";
//        }

//        레슨에 저장된 문제 카테고리(문제 이름)
        String questionName = student.getLesson().getQuestionName();
//        문제 이름에 해당하는 문제 리스트 가져오기
        List<Question> questions = questionService.findQuestionByName(questionName);

//        문제랑 학생 뷰로 보냄
        model.addAttribute("student", student);
        model.addAttribute("questions", questions);

        log.info("학생 : {}", student);
        log.info("수업 : {}", student.getLesson());
        log.info("문제 : {} ", questions);
//
//        questions.forEach(question -> {
//            log.info("문제 번호 {} 문제 {}", question.getNumber(), question.getContent());
//        });

        return "/student/question";
    }


    @PostMapping("/question/insert")
    public String insert(QuestionAnswerDTO questionAnswerDTO, HttpServletResponse response){

        log.info("questionAnswerDTO : {}", questionAnswerDTO);

//        답안지 삽입
//        채점
//        학생 테이블 pretest = true, score = 점수
        int result = questionAnswerService.grading(questionAnswerDTO);

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter w = null;

        try {
            w = response.getWriter();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return "redirect:/student";
        }

        if(result == 1){
            w.println("<script> alert('제출되었습니다.');");
        }else{
            w.println("<script> alert('Error!');");
        }

        w.println("location.href='/student' </script>");
        w.close();

        return null;

    }



}