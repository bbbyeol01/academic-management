package com.ezen.management.controller;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Member;
import com.ezen.management.domain.MemberRole;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.*;
import com.ezen.management.service.LessonService;
import com.ezen.management.service.MemberService;
import com.ezen.management.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final StudentService studentService;
    private final LessonService lessonService;

    @Value("${com.ezen.management.upload.path}")
    private String uploadPath;

//    행정 관리 홈(행정 리스트)
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/admin")   //  /member/admin
    public String adminIndex(Model model, PageRequestDTO pageRequestDTO){

        Set<MemberRole> memberRoleSet = new HashSet<>();
        memberRoleSet.add(MemberRole.ADMIN);

        PageResponseDTO<Member> pageResponseDTO = memberService.findBySpecificRoles(memberRoleSet, pageRequestDTO);

        model.addAttribute("admin", MemberRole.ADMIN);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/member/admin/index";
    }


//    행정 추가
    @PreAuthorize("hasRole('MASTER')")
    @PostMapping("/admin/insert")   //  /member/admin/insert POST
    public String adminInsertPOST(MemberDTO memberDTO){
        log.info("----------------- ADMIN insert POST -----------------");

        try{
            memberService.adminInsert(memberDTO);
            return "redirect:/member/admin?code=insert-success";
        }catch (Exception e){
            return "redirect:/member/admin?code=insert-fail";
        }
    }


//    행정 정보
    @GetMapping("/admin/getAdmin")
    @ResponseBody
    public Member getAdmin(String adminId){

        Optional<Member> byId = memberService.findById(adminId);
        return byId.get();

    }


//    행정 수정
    @PreAuthorize("hasRole('MASTER')")
    @PostMapping("/admin/update")
    public String adminUpdatePost(MemberDTO memberDTO, HttpServletResponse response){





        try{
            memberService.update(memberDTO);
            return "redirect:/member/admin?code=modify-success";
        }catch (Exception e){
            return "redirect:/member/admin?code=modify-fail";
        }




    }


    //    행정 삭제
    @PreAuthorize("hasRole('MASTER')")
    @PostMapping("/admin/delete")
    @ResponseBody
    public void adminDelete(String id, HttpServletResponse response){
        memberService.delete(id);
    }



//    교사 관리 홈(교사 리스트)
    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @GetMapping("/teacher")
    public String teacherIndex(Model model, PageRequestDTO pageRequestDTO){

        Set<MemberRole> memberRoleSet = new HashSet<>();
        memberRoleSet.add(MemberRole.TEACHER);

        PageResponseDTO<Member> pageResponseDTO = memberService.findBySpecificRoles(memberRoleSet, pageRequestDTO);

        log.info("pageResponseDTO" + pageResponseDTO);
        model.addAttribute("teacher", MemberRole.TEACHER);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/member/teacher/index";
    }



//    교사 추가
    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @PostMapping("/teacher/insert")   //  /member/admin/insert POST
    public String teacherInsertPOST(MemberDTO memberDTO){

        try{
            memberService.teacherInsert(memberDTO);
            return "redirect:/member/teacher?code=insert-success";
        }catch (Exception e){
            return "redirect:/member/teacher?code=insert-fail";
        }

    }



//    교사 찾기
    @GetMapping("/teacher/getTeacher")
    @ResponseBody
    public Member getTeacher(String id){

        return memberService.findById(id).get();
    }


//    교사 수정
    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @PostMapping("/teacher/update")
    public String teacherUpdatePOST(MemberDTO memberDTO, HttpServletResponse response){

        try{
            memberService.update(memberDTO);
            return "redirect:/member/teacher?code=modify-success";
        }catch (Exception e){
            return "redirect:/member/teacher?code=modify-fail";
        }

    }




//    교사 삭제
    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @PostMapping("/teacher/delete")
    public String teacherDelete(@RequestParam String id){

        try{
            memberService.delete(id);
            return "redirect:/member/teacher?code=delete-success";
        }catch (Exception e){
            return "redirect:/member/teacher?code=delete-fail";

        }

    }


//    학생 목록
    @GetMapping("/student")
    public String student(Long lessonIdx, PageRequestDTO pageRequestDTO, Model model){

//        model.addAttribute("students", students);

        PageResponseDTO<Student> pageResponseDTO = studentService.searchStudent(lessonIdx, pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/member/student/index";
    }


//    학생 추가
    @PostMapping("/student/insert")
    public String studentInsert(StudentDTO studentDTO, MultipartFile file){

        log.info("upload File is null? : {}", file == null);
        log.info("upload FIle name : {} ", file.getName());

        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();

        studentDTO.setUuid(uuid);
        studentDTO.setFileName(originalName);

        Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
//           이미지 저장
            file.transferTo(savePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }


        try {
            studentService.insertStudent(studentDTO);
            return "redirect:/member/student?code=insert-success";
        }catch (Exception e){
            return "redirect:/member/student?code=insert-fail";
        }

    }


//    학생 수정
    @PostMapping("/student/modify")
    public String studentModify(StudentDTO studentDTO, MultipartFile file){

        String uuid = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();

        studentDTO.setUuid(uuid);
        studentDTO.setFileName(originalName);

        Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
//           이미지 저장
            file.transferTo(savePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        try {
            studentService.modifyStudent(studentDTO);
            return "redirect:/member/student?code=modify-success";
        }catch (Exception e){
            return "redirect:/member/student?code=modify-fail";
        }

    }


//    학생 정보
    @GetMapping("/student/getStudent")
    @ResponseBody
    public StudentDTO getStudent(Long studentIdx){

        Student student = studentService.findById(studentIdx);

//        뷰에 노출되면 안 되는 값이 있다면 DTO로 전달할 것
        return StudentDTO.builder()
                .lessonIdx(student.getLesson().getIdx())
                .idx(student.getIdx())
                .lessonName(student.getLesson().getCurriculum().getName())
                .lessonNumber(student.getLesson().getNumber())
                .name(student.getName())
                .birthday(student.getBirthday())
                .phone(student.getPhone())
                .email(student.getEmail())
                .etc(student.getEtc())
                .uuid(student.getUuid())
                .fileName(student.getFileName())
                .build();

    }


    @PostMapping("/student/delete")
    public String deleteStudent(StudentDTO studentDTO){

        try{
            studentService.deleteStudent(studentDTO);
            return "redirect:/member/student?code=delete-success";
        }catch (Exception e){
            return "redirect:/member/student?code=delete-fail";
        }

    }




}
