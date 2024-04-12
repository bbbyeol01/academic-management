package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Question;

import com.ezen.management.domain.QuestionAnswer;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.LessonDTO;
import com.ezen.management.dto.QuestionAnswerDTO;
import com.ezen.management.dto.StudentDTO;
import com.ezen.management.service.LessonService;
import com.ezen.management.service.QuestionAnswerService;
import com.ezen.management.service.QuestionService;
import com.ezen.management.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final LessonService lessonService;
    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;

    @GetMapping("")
    public String index(Model model) {

//        List<Lesson> lessonList = lessonService.findAll();
//        현재 진행 중인 수업 리스트
        List<Lesson> lessonList = lessonService.findAllGreaterThan(LocalDate.now());

        model.addAttribute("lessonList", lessonList);

        log.info("!!!!!!!!!!!!!!!!!! student index !!!!!!!!!!!!!!!!!!");
        return "/student/index";
    }

    @PostMapping("/select")
    public String select(Model model, StudentDTO studentDTO) {

        Student student = null;

//        레슨 인덱스와 받아온 이름으로 학생 조회
//        뷰에서는 학생 정보를 보여주고 사전평가/설문조사 중 하나를 클릭하면 거기로 student idx를 넘겨줌
        try {
            student = studentService.findByLessonIdxAndName(studentDTO.getLessonIdx(), studentDTO.getName());
        }catch (Exception e){
//        학생이 존재하지 않으면 돌아감
            return "redirect:/student?code=not-exist-student";

        }

        model.addAttribute("lesson", student.getLesson());
        model.addAttribute("student", student);

        return "/student/select";
    }

    @PostMapping("/question")
    public String testPaper(Model model, StudentDTO studentDTO) {

        log.info("studentDTO : {} ", studentDTO);

        Student student = studentService.findByLessonIdxAndName(studentDTO.getLessonIdx(), studentDTO.getName());

//        학생이 존재하지 않으면 문제를 풀 수 없음
        if (student == null) {
            return "redirect:/student";
        }

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
    public String insert(@Valid QuestionAnswerDTO questionAnswerDTO) {

        log.info("questionAnswerDTO : {}", questionAnswerDTO);

        try{
//          채점 (답안지 삽입 + 학생 컬럼 pretest = true, score = 점수)
            questionAnswerService.grading(questionAnswerDTO);
            return "redirect:/student?code=success";
        }catch (Exception e){
            return "redirect:/student?code=fail";
        }

    }


    @GetMapping("/getStudent")
    @ResponseBody
//    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN', 'TEACHER')")
    public StudentDTO getStudent(Long studentIdx) {

        log.info("student Idx : {}", studentIdx);

        Student student = studentService.findById(studentIdx);
        log.info("student! {}", student);
        log.info("lesson {} ", student.getLesson());

        LessonDTO lessonDTO = LessonDTO.builder()
                .curriculum_name(student.getLesson().getCurriculum().getName())
                .number(student.getLesson().getNumber())
                .build();

//        엔티티에 뷰에 보여주면 안 되는 값이 있으면 DTO로 변환해서 반환함
        StudentDTO studentDTO = StudentDTO.builder()
                .name(student.getName())
                .email(student.getEmail())
                .idx(student.getIdx())
                .birthday(student.getBirthday())
                .phone(student.getPhone())
                .uuid(student.getUuid())
                .fileName(student.getFileName())
                .counseling(student.getCounseling())
                .pretest(student.isPretest())
                .score(student.getScore())
                .lessonName(lessonDTO.getCurriculum_name())
                .lessonNumber(lessonDTO.getNumber())
                .survey(student.getSurvey())
                .done(student.getDone())
                .etc(student.getEtc())
                .build();

        log.info("studentDTO : {}", studentDTO);

        return studentDTO;

    }


}
