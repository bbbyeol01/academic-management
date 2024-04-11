package com.ezen.management.repository;

import com.ezen.management.domain.Curriculum;
import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class LessonRepositoryTests {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Test
    public void 수업추가(){

        Optional<Member> memberResult = memberRepository.getByIdWithRoles("teacher");
        Member member = memberResult.orElseThrow();

        Optional<Curriculum> curriculumResult = curriculumRepository.findById(1L);
        Curriculum curriculum = curriculumResult.orElseThrow();


        log.info("curriculum {}", curriculum);


        LocalDate start = LocalDate.of(2024, 9, 27);

        Lesson lesson = Lesson.builder()
                .curriculum(curriculum)
                .member(member)
                .number(1)
                .questionName("자바 풀스택")
                .startDay(start)
                .endDay(start.plusDays(curriculum.getDay()))
                .survey1(start.plusMonths(1))
                .survey2(start.plusMonths(2))
                .survey3(start.plusMonths(3))
                .build();

        lessonRepository.save(lesson);


    }

    @Test
    public void getByCurriculumNameAndNumber(){

        Optional<Curriculum> curriculumResult = curriculumRepository.findById(1L);

        Curriculum curriculum = curriculumResult.orElseThrow();
        int number = 1;

        Optional<Lesson> lessonResult = lessonRepository.getByCurriculumAndNumber(curriculum, number);
        Lesson lesson = lessonResult.orElseThrow();

        log.info("lesson...... " + lesson);
        log.info("lesson curriculum...... " + lesson.getCurriculum());
    }


//    강사 이름으로 수업 (수업 리스트) 찾기
    @Test
    public void getByMember(){

        Optional<Member> result = memberRepository.findById("teacher");
        Member teacher = result.orElseThrow();

        Optional<List<Lesson>> lessonsByMember = lessonRepository.getLessonsByMember(teacher);
        List<Lesson> lessons = lessonsByMember.orElseThrow();

        log.info("lessons......" + lessons);

    }

    @Test
    @Transactional
    public void 현재진행중인수업(){
//        Optional<Member> result = memberRepository.findById("teacher");
//        Member teacher = result.orElseThrow();

        LocalDate now = LocalDate.now();

//        Optional<List<Lesson>> lessonsByEndDayGreaterThan = lessonRepository.getLessonsByEndDayGreaterThan(now);
        List<Lesson> lessonsByEndDayGreaterThan = lessonRepository.getLessonsByEndDayGreaterThan(now);
//        List<Lesson> lessons = lessonsByEndDayGreaterThan.orElseThrow();


        log.info("lessons...... " + lessonsByEndDayGreaterThan);
    }

    @Test
    public void 전체목록(){
        List<Lesson> all = lessonRepository.findAll();

        log.info("all lessons......" + all);
    }


}
