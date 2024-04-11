package com.ezen.management.service;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Survey;
import com.ezen.management.domain.SurveyAnswer;
import com.ezen.management.dto.*;

import java.util.ArrayList;
import java.util.List;

public interface SurveyAnswerService {

    public int insert(SurveyAnswerDTO surveyAnswerDTO, StudentDTO studentDTO);

    List<SurveyAnswerDTO> findByLessonIdx(Long lessonIdx);

    List<SurveyResultDTO> calculateSumOfAnswers(int round, Long lessonIndex);

    List<SurveyAnswerDTO> findByLessonIdxAndRound(Long lessonIdx, int round);


    default SurveyAnswer surveyAnswerDtoToEntity(SurveyAnswerDTO surveyAnswerDTO){

        SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                .idx(surveyAnswerDTO.getIdx())
                .an1(surveyAnswerDTO.getAn1())
                .an2(surveyAnswerDTO.getAn2())
                .an3(surveyAnswerDTO.getAn3())
                .an4(surveyAnswerDTO.getAn4())
                .an5(surveyAnswerDTO.getAn5())
                .an6(surveyAnswerDTO.getAn6())
                .an7(surveyAnswerDTO.getAn7())
                .an8(surveyAnswerDTO.getAn8())
                .an9(surveyAnswerDTO.getAn9())
                .an10(surveyAnswerDTO.getAn10())
                .an11(surveyAnswerDTO.getAn11())
                .an12(surveyAnswerDTO.getAn12())
                .an13(surveyAnswerDTO.getAn13())
                .an14(surveyAnswerDTO.getAn14())
                .an15(surveyAnswerDTO.getAn15())
                .com1(surveyAnswerDTO.getCom1())
                .com2(surveyAnswerDTO.getCom2())
                .com3(surveyAnswerDTO.getCom3())
                .com4(surveyAnswerDTO.getCom4())
                .com5(surveyAnswerDTO.getCom5())
                .com6(surveyAnswerDTO.getCom6())
                .com7(surveyAnswerDTO.getCom7())
                .com8(surveyAnswerDTO.getCom8())
                .com9(surveyAnswerDTO.getCom9())
                .com10(surveyAnswerDTO.getCom10())
                .com11(surveyAnswerDTO.getCom11())
                .com12(surveyAnswerDTO.getCom12())
                .com13(surveyAnswerDTO.getCom13())
                .com14(surveyAnswerDTO.getCom14())
                .com15(surveyAnswerDTO.getCom15())
                .round(surveyAnswerDTO.getRound())
                .lesson(surveyAnswerDTO.getLesson())
                .build();

        return surveyAnswer;
    }

    default SurveyAnswerDTO surveyAnswerEntityToDTO(SurveyAnswer surveyAnswer){

        SurveyAnswerDTO surveyAnswerDTO = SurveyAnswerDTO.builder()
                .idx(surveyAnswer.getIdx())
                .an1(surveyAnswer.getAn1())
                .an2(surveyAnswer.getAn2())
                .an3(surveyAnswer.getAn3())
                .an4(surveyAnswer.getAn4())
                .an5(surveyAnswer.getAn5())
                .an6(surveyAnswer.getAn6())
                .an7(surveyAnswer.getAn7())
                .an8(surveyAnswer.getAn8())
                .an9(surveyAnswer.getAn9())
                .an10(surveyAnswer.getAn10())
                .an11(surveyAnswer.getAn11())
                .an12(surveyAnswer.getAn12())
                .an13(surveyAnswer.getAn13())
                .an14(surveyAnswer.getAn14())
                .an15(surveyAnswer.getAn15())
                .com1(surveyAnswer.getCom1())
                .com2(surveyAnswer.getCom2())
                .com3(surveyAnswer.getCom3())
                .com4(surveyAnswer.getCom4())
                .com5(surveyAnswer.getCom5())
                .com6(surveyAnswer.getCom6())
                .com7(surveyAnswer.getCom7())
                .com8(surveyAnswer.getCom8())
                .com9(surveyAnswer.getCom9())
                .com10(surveyAnswer.getCom10())
                .com11(surveyAnswer.getCom11())
                .com12(surveyAnswer.getCom12())
                .com13(surveyAnswer.getCom13())
                .com14(surveyAnswer.getCom14())
                .com15(surveyAnswer.getCom15())
                .round(surveyAnswer.getRound())
                .lesson(surveyAnswer.getLesson())
                .build();

        return surveyAnswerDTO;
    }
}
