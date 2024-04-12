package com.ezen.management.repository;

import com.ezen.management.domain.Counseling;
import com.ezen.management.domain.Curriculum;
import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class CounselingRepositoryTests {

    @Autowired
    private CounselingRepository counselingRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    @Transactional
    @Rollback(value = false) //fetch 전 까지는 DB에 안들어감. test + Transactional 하면 rollback이 true여서 그걸 false로 바꿔줘야 함
    public void 상담추가(){

//        과정 고유 이름으로 과정 찾아옴
        Optional<Curriculum> findCurriculumById = curriculumRepository.findById(1L);
        Curriculum curriculum = findCurriculumById.orElseThrow();

        log.info("curriculum...... " + curriculum);

//        과정, 회차로 수업 찾아옴
        Optional<Lesson> byCurriculumAndNumber = lessonRepository.findById(1L);
        Lesson lesson = byCurriculumAndNumber.orElseThrow();

        log.info("lesson...... " + lesson);

//        수업, 학생 이름으로 학생 찾아옴
        Optional<Student> studentResult = studentRepository.getByLessonAndName(lesson, "지혜");
        Student student = studentResult.orElseThrow();

        log.info("student...... " + student);

//        학생 정보의 상담 컬럼 +1
        student.insertCounseling();
        studentRepository.save(student);

//        상담 DB 저장
        Counseling counseling = Counseling.builder()
                .content("인생이란 뭘꽈")
                .method(0)
                .writer("김도넛")
                .student(student)
                .counselingDate(LocalDateTime.now())
                .round(1)
                .build();

        counselingRepository.save(counseling);


    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 상담학생조인(){
        Optional<Student> byId = studentRepository.findById(1L);
        Student student = byId.orElseThrow();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Counseling> counselingWithStudentName = counselingRepository.getCounselingWithStudentName(student, pageable);

//        log.info("result : {}", counselingWithStudentName);

        //객체를 까본다? 확인해볼 수 있는 방법 => counseling이 studentIdx를 외래키로 가지고 있기 때문에 굳이 조인을 하지 않아도 가지고 올 수 있다. = counseling.getStudent.name
        List<Counseling> content = counselingWithStudentName.getContent();
        content.forEach(counseling -> {
            log.info("counseling : {}", counseling);
        });
    }








    //search + paging 확인구간

    @Test
    public void testSearchAll2(){
        String[] types = {"t", "c", "w","n"};

        String keyword = "2024-04-02";

        Pageable pageable = PageRequest.of(0,10, Sort.by("idx").descending());

        Page<Counseling> result = counselingRepository.searchAll(types, keyword, pageable);

        //total pages
        log.info("result.getTotalPages= " + result.getTotalPages());

        //pag size
        log.info("result.getSize= " + result.getSize());

        //pageNumber
        log.info("result.getNumber= " + result.getNumber());

        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(counseling -> log.info("counseling= " + counseling));


    }


    @Test
    public void testSearchAll(){

        String[] types = {"t","c","w"};

        String keyword = "2024-04-02";

        Pageable pageable = PageRequest.of(0,10, Sort.by("idx").descending());

        Page<Counseling> result = counselingRepository.searchAll(types, keyword, pageable);

        log.info("result= " + result);

    }



    @Test
    public void testPaging(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("idx").descending());
        Page<Counseling> result = counselingRepository.findAll(pageable);

        log.info("total count= " + result.getTotalElements());
        log.info("total pages= " + result.getTotalPages());
        log.info("page number= " + result.getSize());

        List<Counseling> todoList = result.getContent();

        todoList.forEach(counseling -> log.info("counseling= " + counseling));

    }



}
