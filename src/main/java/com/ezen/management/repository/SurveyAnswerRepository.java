package com.ezen.management.repository;

import com.ezen.management.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    List<SurveyAnswer> findByLessonIdx(Long lessonIdx);

    List<SurveyAnswer> findByLessonIdxAndRound(Long lessonIdx, int round);

    //결과를 가져오는 쿼리
    @Query(value = "SELECT " +"result.an AS an, " +
            "SUM(CASE WHEN result.answer = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN result.answer = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN result.answer = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN result.answer = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN result.answer = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN result.answer = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN result.answer = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM ( " +
            "SELECT 'an1' AS an, an1 AS answer, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an2', an2, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an3', an3, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an4', an4, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an5', an5, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an6', an6, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an7', an7, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an8', an8, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an9', an9, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an10', an10, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an11', an11, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an12', an12, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an13', an13, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an14', an14, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an15', an15, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an16', an16, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an17', an17, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an18', an18, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an19', an19, round, lesson_Idx FROM survey_answer " +
            "UNION ALL SELECT 'an20', an20, round, lesson_Idx FROM survey_answer " +
            ") AS result " +
            "WHERE result.round = :round AND result.lesson_Idx = :lessonIdx " +
            "GROUP BY result.an " +
            "ORDER BY CAST(SUBSTRING_INDEX(result.an, 'an', -1) AS UNSIGNED)", nativeQuery = true)
    List<Object[]> calculateSumOfAnswers(@Param("round") int round, @Param("lessonIdx") Long lessonIdx);

}
