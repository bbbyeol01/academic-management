package com.ezen.management.service;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.SurveyAnswer;
import com.ezen.management.dto.SurveyAnswerDTO;
import com.ezen.management.repository.LessonRepository;
import com.ezen.management.repository.StudentRepository;
import com.ezen.management.repository.SurveyAnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class SurveyAnswerServiceTests {


    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private SurveyAnswerServiceImpl surveyAnswerService;

    @Test
    void insertTest() {
// round를 1부터 3까지 반복
        for (int i = 1; i <= 3; i++) {
            // 10번 반복
            for (int j = 0; j < 10; j++) {
                // 테스트 데이터 설정
                Optional<Lesson> lessonOptional = lessonRepository.findById(1L);
                Lesson lesson = lessonOptional.get();
                SurveyAnswerDTO surveyAnswerDTO = new SurveyAnswerDTO();
                surveyAnswerDTO.setLesson(lesson);
                surveyAnswerDTO.setAn1(1);
                surveyAnswerDTO.setAn2(2);
                surveyAnswerDTO.setAn3(3);
                surveyAnswerDTO.setAn4(4);
                surveyAnswerDTO.setAn5(5);
                surveyAnswerDTO.setAn6(6);
                surveyAnswerDTO.setAn7(7);
                surveyAnswerDTO.setAn8(1);
                surveyAnswerDTO.setAn9(2);
                surveyAnswerDTO.setAn10(3);
                surveyAnswerDTO.setAn11(4);
                surveyAnswerDTO.setAn12(5);
                surveyAnswerDTO.setAn13(6);
                surveyAnswerDTO.setAn14(7);
                surveyAnswerDTO.setAn15(1);
                surveyAnswerDTO.setRound(i);

                // SurveyAnswerDTO를 SurveyAnswer 엔티티로 변환
                SurveyAnswer surveyAnswer = surveyAnswerService.surveyAnswerDtoToEntity(surveyAnswerDTO);

                // SurveyAnswer를 저장
                surveyAnswerRepository.save(surveyAnswer);
            }
        }
    }
}
