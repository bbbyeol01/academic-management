package com.ezen.management.service;

import com.ezen.management.domain.*;
import com.ezen.management.dto.*;
import com.ezen.management.repository.LessonRepository;
import com.ezen.management.repository.MemberRepository;
import com.ezen.management.repository.StudentRepository;
import com.ezen.management.repository.SubjectTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final SubjectTestRepository subjectTestRepository;
    private final MemberRepository memberRepository;

    public List<Lesson> findAll(){
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> findAllGreaterThan(LocalDate day) {
        return lessonRepository.getLessonsByEndDayGreaterThan(day);

    }

    @Override
    public Lesson findById(Long lessonIdx) {
        Optional<Lesson> byId = lessonRepository.findById(lessonIdx);

        return byId.orElse(null);
    }

    //진행중인 수업 & 검색 & 페이징
    @Override
    public PageResponseDTO<Lesson> ongoingLesson(PageRequestDTO pageRequestDTO, String userId) {
        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Optional<Member> memberResult = memberRepository.findById(userId);
        Member member = memberResult.orElseThrow();
        String role = member.getRoleSet().toString();

        if(role.equals("[ROLE_TEACHER]")){
            Page<Lesson> lessonPage = lessonRepository.searchLessonOngoing(types,keyword,pageable, userId);

            List<Lesson> dtoList = lessonPage.getContent();

            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();
        }else{
            Page<Lesson> lessonPage = lessonRepository.searchLessonOngoing(types,keyword,pageable, "None");

            List<Lesson> dtoList = lessonPage.getContent();

            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();
        }



    }

    @Override
    public List<Lesson> getOngoingLesson() {
        return lessonRepository.getLessonsByEndDayGreaterThan(LocalDate.now());
    }

    //완료된 수업 & 검색 & 페이징
    @Override
    public PageResponseDTO<Lesson> endLesson(PageRequestDTO pageRequestDTO, String userId) {
        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Optional<Member> memberResult = memberRepository.findById(userId);
        Member member = memberResult.orElseThrow();
        String role = member.getRoleSet().toString();

        if(role.equals("[ROLE_TEACHER]")) {
            Page<Lesson> lessonPage = lessonRepository.searchLessonEnd(types, keyword, pageable, userId);
            List<Lesson> dtoList = lessonPage.getContent();
            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();
        }else{
            Page<Lesson> lessonPage = lessonRepository.searchLessonEnd(types, keyword, pageable, "None");
            List<Lesson> dtoList = lessonPage.getContent();
            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();

        }
    }

    //학생 목록
    @Override
    public PageResponseDTO<Student> searchStudent(PageRequestDTO pageRequestDTO, Long lessonIdx){

        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Student> studentPage = studentRepository.searchStudent(lessonIdx, types, keyword, pageable);
        List<Student> dtoList = studentPage.getContent();

        return PageResponseDTO.<Student>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)studentPage.getTotalElements())
                .build();
    }

    @Override
    public List<Student> studentList(Long idx){
        return studentRepository.getByLessonIdx(idx);
    }

    //학생 인덱스로 과목평가 가져오기
    @Override
    public List<SubjectTest> searchSubjectTest(Long studentIdx){
        return subjectTestRepository.searchSubjectTest(studentIdx);
    }

    @Override
    public Long subjectTestUpdate(SubjectTestList subjectTestList){

        List<SubjectTest> subjectTest =  subjectTestListTOSubjectTest(subjectTestList);
        subjectTestRepository.saveAll(subjectTest);

        return subjectTest.get(0).getStudent().getIdx();
    }

    public SubjectTest SubjectTesListDTOtoSubjectTestEntity(SubjectTestListDTO subjectTestListDTO){

        log.info("서비스 : " + subjectTestListDTO);

        Student student = studentRepository.findById(subjectTestListDTO.getStudentIdx()).orElseThrow();

        SubjectTest subjectTest = SubjectTest.builder()
                .idx(subjectTestListDTO.getIdx())
                .subject(subjectTestListDTO.getSubject())
                .state(subjectTestListDTO.getState())
                .student(student)
                .build();

        return subjectTest;
    }

    public List<SubjectTest> subjectTestListTOSubjectTest(SubjectTestList subjectTestList){
        List<SubjectTest> list = new ArrayList<>();

        for(SubjectTestListDTO subjectTestListDTO : subjectTestList){
            SubjectTest subjectTest = SubjectTesListDTOtoSubjectTestEntity(subjectTestListDTO);
            list.add(subjectTest);
        }

        return list;
    }

}
