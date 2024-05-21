package com.ezen.management.repository;

import com.ezen.management.domain.Curriculum;
import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Member;
import com.ezen.management.repository.search.LessonSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> , LessonSearch {

    @Query("select l from Lesson l where l.curriculum = :curriculum and l.number = :number")
    Optional<Lesson> getByCurriculumAndNumber(Curriculum curriculum, int number);

    @Query("select l from Lesson l where l.member = :member")
    Optional<List<Lesson>> getLessonsByMember(Member member);

    List<Lesson> getLessonsByEndDayGreaterThan(LocalDate now);

    @Query("select l from Lesson l where l.startDay = :today")
    Optional<List<Lesson>> lessonDateStart(LocalDate today);

    @Query("select l from Lesson l where l.endDay = :today")
    Optional<List<Lesson>> lessonDateEnd(LocalDate today);

}
