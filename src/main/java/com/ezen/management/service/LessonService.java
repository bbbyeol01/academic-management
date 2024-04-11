package com.ezen.management.service;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import com.ezen.management.domain.SubjectTest;
import com.ezen.management.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    List<Lesson> findAllGreaterThan(LocalDate day);

    Lesson findById(Long lessonIdx);

    //진행중인 수업 & 검색 & 페이징
    public PageResponseDTO<Lesson> ongoingLesson(PageRequestDTO pageRequestDTO, String userId);

    List<Lesson> getOngoingLesson();

    //완료된 수업 & 검색 & 페이징
    public PageResponseDTO<Lesson> endLesson(PageRequestDTO pageRequestDTO, String userId);

    List<Student> studentList(Long idx);

    public PageResponseDTO<Student> searchStudent(PageRequestDTO pageRequestDTO, Long lessonIdx);

    List<SubjectTest> searchSubjectTest(Long studentIdx);

    Long subjectTestUpdate(SubjectTestList subjectTestList);
}
