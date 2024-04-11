package com.ezen.management.repository;

import com.ezen.management.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    List<SurveyAnswer> findByLessonIdx(Long lessonIdx);

    List<SurveyAnswer> findByLessonIdxAndRound(Long lessonIdx, int round);

    //결과를 가져오는 쿼리
    @Query(value = "SELECT * FROM (" +
            "(SELECT 'an1' AS an, " +
            "SUM(CASE WHEN an1 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an1 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an1 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an1 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an1 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an1 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an1 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an2' AS an, " +
            "SUM(CASE WHEN an2 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an2 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an2 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an2 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an2 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an2 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an2 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an3' AS an, " +
            "SUM(CASE WHEN an3 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an3 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an3 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an3 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an3 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an3 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an3 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an4' AS an, " +
            "SUM(CASE WHEN an4 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an4 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an4 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an4 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an4 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an4 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an4 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an5' AS an, " +
            "SUM(CASE WHEN an5 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an5 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an5 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an5 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an5 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an5 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an5 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an6' AS an, " +
            "SUM(CASE WHEN an6 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an6 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an6 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an6 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an6 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an6 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an6 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an7' AS an, " +
            "SUM(CASE WHEN an7 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an7 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an7 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an7 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an7 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an7 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an7 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an8' AS an, " +
            "SUM(CASE WHEN an8 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an8 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an8 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an8 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an8 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an8 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an8 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an9' AS an, " +
            "SUM(CASE WHEN an9 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an9 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an9 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an9 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an9 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an9 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an9 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an10' AS an, " +
            "SUM(CASE WHEN an10 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an10 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an10 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an10 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an10 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an10 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an10 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an11' AS an, " +
            "SUM(CASE WHEN an11 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an11 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an11 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an11 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an11 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an11 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an11 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an12' AS an, " +
            "SUM(CASE WHEN an12 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an12 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an12 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an12 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an12 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an12 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an12 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an13' AS an, " +
            "SUM(CASE WHEN an13 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an13 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an13 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an13 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an13 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an13 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an13 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an14' AS an, " +
            "SUM(CASE WHEN an14 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an14 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an14 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an14 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an14 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an14 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an14 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            "UNION ALL " +
            "(SELECT 'an15' AS an, " +
            "SUM(CASE WHEN an15 = 1 THEN 1 ELSE 0 END) AS item1, " +
            "SUM(CASE WHEN an15 = 2 THEN 1 ELSE 0 END) AS item2, " +
            "SUM(CASE WHEN an15 = 3 THEN 1 ELSE 0 END) AS item3, " +
            "SUM(CASE WHEN an15 = 4 THEN 1 ELSE 0 END) AS item4, " +
            "SUM(CASE WHEN an15 = 5 THEN 1 ELSE 0 END) AS item5, " +
            "SUM(CASE WHEN an15 = 6 THEN 1 ELSE 0 END) AS item6, " +
            "SUM(CASE WHEN an15 = 7 THEN 1 ELSE 0 END) AS item7 " +
            "FROM survey_answer WHERE round = :round AND lesson_idx = :lessonIdx) " +
            ") AS result", nativeQuery = true)
    List<Object[]> calculateSumOfAnswers(@Param("round") int round, @Param("lessonIdx") Long lessonIdx);

}
