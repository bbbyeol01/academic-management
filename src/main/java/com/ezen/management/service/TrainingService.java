package com.ezen.management.service;

import com.ezen.management.domain.*;
import com.ezen.management.dto.*;

import java.util.List;

public interface TrainingService {

    //----------------------------------------------------유형----------------------------------------------------
    //유형 전체
    //유형 페이징
    PageResponseDTO<Category> searchCategory(PageRequestDTO pageRequestDTO);

    //유형 전체
    public List<Category> categoryList();

    //유형 등록
    public void categoryInsert(CategoryDTO categoryDTO);

    //유형 수정
    public void categoryUpdate(CategoryDTO categoryDTO);

    //유형 삭제
    public void categoryDelete(Long idx);

    //유형 찾기
    public Category getCategoryIdx(Long idx);
    
    //유형 이름으로 유형찾기
    public Category getCategoryByName(String name);

    default Category dtoToEntity(CategoryDTO categoryDTO){

        Category category = Category.builder()
                .idx(categoryDTO.getIdx())
                .name(categoryDTO.getName())
                .build();

        return category;
    }

    //----------------------------------------------------과목----------------------------------------------------
    //과목 전체
    //과목 페이징
    public PageResponseDTO<Subject> searchSubject(PageRequestDTO pageRequestDTO);

    //과목 전체
    public List<Subject> subjectList();

    //과목 등록
    public void subjectInsert(SubjectDTO subjectDTO);

    //과목 수정
    public void subjectUpdate(SubjectDTO subjectDTO);

    //과목 삭제
    public void subjectDelete(Long idx);

    default Subject subjectDtoToEntity(SubjectDTO subjectDTO){

        Subject subject = Subject.builder()
                .name(subjectDTO.getName())
                .method(subjectDTO.getMethod())
                .build();

        return subject;
    }

    //----------------------------------------------------과정----------------------------------------------------
    //과정 전체 & 페이징 & 검색
    public PageResponseDTO<Curriculum> searchCurriculum(PageRequestDTO pageRequestDTO);

    //과정 전체
    public List<Curriculum> curriculumList();

    //과정 등록
    public void curriculumInsert(CurriculumDTO curriculumDTO);

    //과정 수정
    public void curriculumUpdate(CurriculumDTO curriculumDTO);

    //과정 삭제
    public void curriculumDelete(Long idx);

    //과정 인덱스로 가져오기
    public Curriculum getCurriculumByIdx(Long idx);

    default Curriculum curriculumDtoTOEntity(CurriculumDTO curriculumDTO){


        Curriculum curriculum = Curriculum.builder()
                .name(curriculumDTO.getName())
                .category(curriculumDTO.getCategory())
                .time(curriculumDTO.getTime())
                .day(curriculumDTO.getDay())
                .build();

        return curriculum;
    }

    //----------------------------------------------------수업----------------------------------------------------

    //수업전체 & 검색 & 페이징
    public PageResponseDTO<Lesson> searchLesson(PageRequestDTO pageRequestDTO, String userID);

    //수업전체
    public List<Lesson> lessonList();

    //수업 상세
    public Lesson getLessonByIdx(Long idx);

    //수업 등록
    public Long lessonInsert(LessonDTO lessonDTO);

    //수업 보유과목 등록
    public void subjectHoldInsert(SubjectHoldDTO subjectHoldDTO);

    //수업 수정
    public void lessonUpdate(LessonDTO lessonDTO);

    //수업 삭제
    public void lessonDelete(Long idx);

    default Lesson lessonDtoToEntity(LessonDTO lessonDTO, Curriculum curriculum, Member member){

        Lesson lesson = Lesson.builder()
                .idx(lessonDTO.getIdx())
                .curriculum(curriculum)
                .member(member)
                .number(lessonDTO.getNumber())

                .headCount(lessonDTO.getHeadCount())
                .startDay(lessonDTO.getStartDay())
                .endDay(lessonDTO.getEndDay())
                .survey1(lessonDTO.getSurvey1())
                .survey2(lessonDTO.getSurvey2())
                .survey3(lessonDTO.getSurvey3())
                .classRoom(lessonDTO.getClassRoom())
                .content(lessonDTO.getContent())
                .questionName(lessonDTO.getQuestionName())
                .build();

        return lesson;
    }

    default SubjectHold subjectHoldDtoToEntity(SubjectHoldDTO subjectHoldDTO){

        SubjectHold subjectHold = SubjectHold.builder()
                .idx(subjectHoldDTO.getIdx())
                .name(subjectHoldDTO.getName())
                .lesson_idx(subjectHoldDTO.getLesson_idx())
                .build();

        return subjectHold;
    }

    //과목평가 수정
    public void subjectTestUpdate(SubjectTestDTO subjectTestDTO);
}