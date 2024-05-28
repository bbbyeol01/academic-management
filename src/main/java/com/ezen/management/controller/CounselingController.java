package com.ezen.management.controller;

import com.ezen.management.domain.Counseling;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.*;
import com.ezen.management.service.CounselingService;
import com.ezen.management.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Controller
@PreAuthorize("hasAnyRole('MASTER', 'ADMIN', 'TEACHER')") //추가 변경 사항 = login 밀어내기
@RequestMapping("/counseling")
public class CounselingController {

    @Autowired
    private final CounselingService counselingService;
    @Autowired
    private final StudentService studentService;


    public CounselingController(CounselingService counselingService, StudentService studentService) {
        this.counselingService = counselingService;
        this.studentService = studentService;
    }



    //상담 상세리스트
    @GetMapping("/counselingDetail")
    public void findByStudentIdx(Model model, @RequestParam("idx")Long studentIdx) {
        //왜 스트링을 보낼때는 안가고 void로 하니까 갈까?

        //학생 단일 정보도 보내기
        Student student = studentService.findById(studentIdx);
        model.addAttribute("student", student);
        log.info("student= " + student);


        //학생정보 List에 실어보내기
        List<Counseling> counselingList = counselingService.findByStudentIdx(studentIdx);

        //상담회차 기준으로 정렬해서 보내기
        Collections.sort(counselingList, Comparator.comparingInt(Counseling::getRound).reversed());

        model.addAttribute("counselingList", counselingList);
        log.info("counselingList= " + counselingList);

    }



    //추가하기
    @GetMapping("/insert")
    public void insert(Model model, CounselingDTO counselingDTO, @RequestParam("idx") Long studentIdx){

        log.info("안녕 여기 추가화면 가는 중");

        //학생 단일 정보 보내기
        Student student = studentService.findById(studentIdx);
        model.addAttribute("student", student);
        log.info("student= " + student);


    }
    @PostMapping("/insert")
    public String insert(CounselingDTO counselingDTO
                            ,BindingResult bindingResult
                            ,RedirectAttributes redirectAttributes){

        log.info("안녕 여기 추가화면 나오는 중");

        //시간 잘 나오나 확인용
        log.info("before counselingDTO= " + counselingDTO);

        //만약 시간이 null 인경우 현재 시간으로 변경하기
        if(counselingDTO.getCounselingDate() == null){
            counselingDTO.setCounselingDate(LocalDateTime.now());
        }

        //시간 변경후 확인용
        log.info("after counselngDTO= " + counselingDTO);


        Long idx = counselingService.insert(counselingDTO);
        log.info("idx= " + idx);

        redirectAttributes.addFlashAttribute("result", idx);
        redirectAttributes.addAttribute("idx", counselingDTO.getStudentIdx());

        return "redirect:/counseling/counselingDetail";
    }


    //수정하기
    @GetMapping("/update")
    public void update(Model model
                        ,Student Student
                        ,@RequestParam("idx") Long idx){

        log.info("안녕 여기 수정화면 가는 중");

        //상담 정보 보내기
        Counseling counseling = counselingService.findByidx(idx);
        model.addAttribute("counseling", counseling);
        log.info("counseling= " + counseling);

        //학생 단일 정보 보내기
        Student student = studentService.findById(counseling.getStudent().getIdx());
        model.addAttribute("student", student);
        log.info("student= " + student);

    }
    @PostMapping("/update")
    public String update(CounselingDTO counselingDTO
                         ,RedirectAttributes redirectAttributes){

        log.info("상담 수정화면 나오는 중");


        counselingService.update(counselingDTO);
        redirectAttributes.addFlashAttribute("result", "updated");
        redirectAttributes.addAttribute("idx", counselingDTO.getStudentIdx());

        log.info("counselingDTO= " + counselingDTO);

        return "redirect:/counseling/counselingDetail";
    }




    //삭제하기
    @PostMapping("/delete")
    public String delete(@RequestParam("idx") Long idx
                        ,RedirectAttributes redirectAttributes){

        log.info("상담삭제할겨" + idx);


        //상담idx 찾기
        Counseling counseling = counselingService.findByidx(idx);

        //삭제 호출
        counselingService.delete(idx);

        redirectAttributes.addFlashAttribute("result", "delete");
        redirectAttributes.addAttribute("idx", counseling.getStudent().getIdx());

        return "redirect:/counseling/counselingDetail";

    }





}
