package com.ezen.management.service;

import com.ezen.management.domain.*;
import com.ezen.management.dto.*;
import com.ezen.management.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TrainingServiceImpl implements TrainingService{

    private final CategoryRepository categoryRepository;
    private final SubjectRepository subjectRepository;
    private final CurriculumRepository curriculumRepository;
    private final LessonRepository lessonRepository;
    private final SubjectHoldRepository subjectHoldRepository;
    private final MemberRepository memberRepository;
    private final SubjectTestRepository subjectTestRepository;

    //----------------------------------------------------유형----------------------------------------------------
    //유형전체
    //유형 페이징 & 검색
    @Override
    public PageResponseDTO<Category> searchCategory(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable();

        String keyword = pageRequestDTO.getKeyword();

        Page<Category> categoryPage = categoryRepository.searchCategory(keyword, pageable);

        List<Category> dtoList = categoryPage.getContent();

        return PageResponseDTO.<Category>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)categoryPage.getTotalElements())
                .build();
    }

    //유형 전체
    @Override
    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }

    // 유형 추가
    @Override
    public void categoryInsert(CategoryDTO categoryDTO) {
        Category category = dtoToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    //유형 수정
    @Override
    public void categoryUpdate(CategoryDTO categoryDTO) {
        Optional<Category> result = categoryRepository.findById(categoryDTO.getIdx());
        Category category = result.orElseThrow();
        category.changeName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    //유형 삭제
    @Override
    public void categoryDelete(Long idx) {
        categoryRepository.deleteById(idx);
    }

    //유형 찾기
    @Override
    public Category getCategoryIdx(Long idx){
        log.info("idx : " + idx);
        Optional<Category> result = categoryRepository.findById(idx);

        log.info("Service Category : " + result);

        Category category = result.orElseThrow();

        log.info("Category : " + category);


        return category;
    }

    @Override
    public Category getCategoryByName(String name){
        return categoryRepository.getCategoryByName(name);
    }



    //----------------------------------------------------과목----------------------------------------------------
    //과목 전체 & 페이징
    @Override
    public PageResponseDTO<Subject> searchSubject(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Subject> subjectPage = subjectRepository.searchSubject(types, keyword, pageable);

        List<Subject> dtoList = subjectPage.getContent();

        return PageResponseDTO.<Subject>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)subjectPage.getTotalElements())
                .build();
    }

    //과목 전체
    @Override
    public List<Subject> subjectList(){
        return subjectRepository.findAll();
    }

    //과목 등록
    @Override
    public void subjectInsert(SubjectDTO subjectDTO) {
        Subject subject = subjectDtoToEntity(subjectDTO);
        subjectRepository.save(subject);
    }

    //과목 수정
    @Override
    public void subjectUpdate(SubjectDTO subjectDTO) {
        Optional<Subject> result = subjectRepository.findById(subjectDTO.getIdx());
        Subject subject = result.orElseThrow();
        subject.changeMethod(subjectDTO.getMethod());
        subjectRepository.save(subject);
    }

    //과목 삭제
    @Override
    public void subjectDelete(Long idx) {
        subjectRepository.deleteById(idx);
    }

    //----------------------------------------------------과정----------------------------------------------------
    //과정 전체
    //과정 페이징 & 검색
    @Override
    public PageResponseDTO<Curriculum> searchCurriculum(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Curriculum> curriculumPage = curriculumRepository.searchCurriculum(types, keyword, pageable);

        List<Curriculum> dtoList = curriculumPage.getContent();

        return PageResponseDTO.<Curriculum>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)curriculumPage.getTotalElements())
                .build();
    }

    //과정 전체
    @Override
    public List<Curriculum> curriculumList(){
        return curriculumRepository.findAll();
    }

    //과정 등록
    @Override
    public void curriculumInsert(CurriculumDTO curriculumDTO) {
        Curriculum curriculum = curriculumDtoTOEntity(curriculumDTO);
        curriculumRepository.save(curriculum);
    }

    //과정 수정
    @Override
    public void curriculumUpdate(CurriculumDTO curriculumDTO) {
        Optional<Curriculum> result = curriculumRepository.findById(curriculumDTO.getIdx());
        log.info("Controller : " + result);
        Curriculum curriculum = result.orElseThrow();

        curriculum.changeName(curriculumDTO.getName());
        curriculum.changeCategory(curriculumDTO.getCategory());
        curriculum.changeDay(curriculumDTO.getDay());
        curriculum.changeTime(curriculumDTO.getTime());

        curriculumRepository.save(curriculum);
    }

    //과정 삭제
    @Override
    public void curriculumDelete(Long idx) {
        curriculumRepository.deleteById(idx);
    }

    //과정 인덱스로 가져오기
    @Override
    public Curriculum getCurriculumByIdx(Long idx){
        Optional<Curriculum> result = curriculumRepository.findById(idx);
        return result.orElseThrow();
    }

    //----------------------------------------------------수업----------------------------------------------------

    //수업 전체
    @Override
    public PageResponseDTO<Lesson> searchLesson(PageRequestDTO pageRequestDTO, String userId) {
        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Optional<Member> memberResult = memberRepository.findById(userId);
        Member member = memberResult.orElseThrow();
        String role = member.getRoleSet().toString();

        log.info("서비스 : " + role +" , "+userId );

        if(role.equals("[ROLE_TEACHER]")){
            Page<Lesson> lessonPage = lessonRepository.searchLesson(types, keyword, pageable, userId);
            List<Lesson> dtoList = lessonPage.getContent();
            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();
        }else{
            Page<Lesson> lessonPage = lessonRepository.searchLesson(types, keyword, pageable, "None");
            List<Lesson> dtoList = lessonPage.getContent();
            return PageResponseDTO.<Lesson>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)lessonPage.getTotalElements())
                    .build();
        }
    }

    //수업 전체
    @Override
    public List<Lesson> lessonList(){
        return lessonRepository.findAll();
    }

    //수업 등록
    @Override
    public Long lessonInsert(LessonDTO lessonDTO) {

        Optional<Curriculum> result1 = curriculumRepository.findById(lessonDTO.getCurriculum_idx());
        Curriculum curriculum = result1.orElseThrow();
        lessonDTO.setCurriculum_name(curriculum.getName());
        lessonDTO.setCurriculum_day(curriculum.getDay());
        lessonDTO.setCurriculum_time(curriculum.getTime());

        Optional<Member> result2 = memberRepository.findById(lessonDTO.getMember_id());
        Member member= result2.orElseThrow();
        lessonDTO.setMember_name(member.getName());

        Lesson lesson = lessonDtoToEntity(lessonDTO, curriculum, member);

        Long subjectIdx = lessonRepository.save(lesson).getIdx();

        return subjectIdx;
    }

    //수업 보유과목 등록
    @Override
    public void subjectHoldInsert(SubjectHoldDTO subjectHoldDTO){
        log.info("서비스 보유과목 : " + subjectHoldDTO);
        SubjectHold subjectHold = subjectHoldDtoToEntity(subjectHoldDTO);
        subjectHoldRepository.save(subjectHold);
        log.info("서비스 잘 들어갔나 : " + subjectHold);
    }

    //수업 상세
    @Override
    public Lesson getLessonByIdx(Long idx){

        Optional<Lesson> lesson = lessonRepository.findById(idx);

        return lesson.orElseThrow();
    }

    //수업 수정
    @Override
    public void lessonUpdate(LessonDTO lessonDTO) {
        Optional<Lesson> lessonResult = lessonRepository.findById(lessonDTO.getIdx());
        Lesson lesson = lessonResult.orElseThrow();

        Optional<Curriculum> curriculumResult = curriculumRepository.findById(lessonDTO.getCurriculum_idx());
        Curriculum curriculum = curriculumResult.orElseThrow();

        Optional<Member> memberResult = memberRepository.findById(lessonDTO.getMember_id());
        Member member = memberResult.orElseThrow();

        lesson.changeTeacher(member);
        lesson.changeCurriculum(curriculum);
        lesson.changeClassroom(lessonDTO.getClassRoom());
        lesson.changeQuestionName(lessonDTO.getQuestionName());
        lesson.changeContent(lessonDTO.getContent());
        lesson.changeNumber(lessonDTO.getNumber());
        lesson.changeStartDay(lessonDTO.getStartDay());
        lesson.changeEndDay(lessonDTO.getEndDay());
        lesson.changeSurvey1(lessonDTO.getSurvey1());
        lesson.changeSurvey2(lessonDTO.getSurvey2());
        lesson.changeSurvey3(lessonDTO.getSurvey3());

        log.info("잘 바뀌었나 확인해보자 : " + lesson);

        lessonRepository.save(lesson);
    }

    //수업 삭제
    @Override
    public void lessonDelete(Long idx) { lessonRepository.deleteById(idx);}

    //과목 평가 수정
    @Override
    public void subjectTestUpdate(SubjectTestDTO subjectTestDTO){
        Optional<SubjectTest> result = subjectTestRepository.findById(subjectTestDTO.getIdx());
        SubjectTest subjectTest = result.orElseThrow();

        subjectTestRepository.save(subjectTest);
    }
}
