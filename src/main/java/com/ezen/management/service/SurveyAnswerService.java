package com.ezen.management.service;

import com.ezen.management.domain.SurveyAnswer;
import com.ezen.management.dto.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

public interface SurveyAnswerService {

    public int insert(SurveyAnswerDTO surveyAnswerDTO, StudentDTO studentDTO);

    List<SurveyAnswerDTO> findByLessonIdx(Long lessonIdx);

    List<SurveyResultDTO> calculateSumOfAnswers(int round, Long lessonIndex);

    List<SurveyAnswerDTO> findByLessonIdxAndRound(Long lessonIdx, int round);


    default SurveyAnswer surveyAnswerDtoToEntity(SurveyAnswerDTO surveyAnswerDTO){

        // SurveyAnswerDTO에서 필드 값을 가져와서 HTML 이스케이프 처리
        surveyAnswerDTO = escapeHtmlInSurveyAnswerDTO(surveyAnswerDTO);

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
                .an16(surveyAnswerDTO.getAn16())
                .an17(surveyAnswerDTO.getAn17())
                .an18(surveyAnswerDTO.getAn18())
                .an19(surveyAnswerDTO.getAn19())
                .an20(surveyAnswerDTO.getAn20())
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
                .com16(surveyAnswerDTO.getCom16())
                .com17(surveyAnswerDTO.getCom17())
                .com18(surveyAnswerDTO.getCom18())
                .com19(surveyAnswerDTO.getCom19())
                .com20(surveyAnswerDTO.getCom20())
                .round(surveyAnswerDTO.getRound())
                .lesson(surveyAnswerDTO.getLesson())
                .build();

        return surveyAnswer;
    }

    private SurveyAnswerDTO escapeHtmlInSurveyAnswerDTO(SurveyAnswerDTO surveyAnswerDTO) {
        String escapedCom1 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom1());
        String escapedCom2 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom2());
        String escapedCom3 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom3());
        String escapedCom4 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom4());
        String escapedCom5 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom5());
        String escapedCom6 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom6());
        String escapedCom7 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom7());
        String escapedCom8 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom8());
        String escapedCom9 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom9());
        String escapedCom10 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom10());
        String escapedCom11 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom11());
        String escapedCom12 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom12());
        String escapedCom13 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom13());
        String escapedCom14 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom14());
        String escapedCom15 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom15());
        String escapedCom16 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom16());
        String escapedCom17 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom17());
        String escapedCom18 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom18());
        String escapedCom19 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom19());
        String escapedCom20 = StringEscapeUtils.escapeHtml4(surveyAnswerDTO.getCom20());
        surveyAnswerDTO.setCom1(escapedCom1);
        surveyAnswerDTO.setCom2(escapedCom2);
        surveyAnswerDTO.setCom3(escapedCom3);
        surveyAnswerDTO.setCom4(escapedCom4);
        surveyAnswerDTO.setCom5(escapedCom5);
        surveyAnswerDTO.setCom6(escapedCom6);
        surveyAnswerDTO.setCom7(escapedCom7);
        surveyAnswerDTO.setCom8(escapedCom8);
        surveyAnswerDTO.setCom9(escapedCom9);
        surveyAnswerDTO.setCom10(escapedCom10);
        surveyAnswerDTO.setCom11(escapedCom11);
        surveyAnswerDTO.setCom12(escapedCom12);
        surveyAnswerDTO.setCom13(escapedCom13);
        surveyAnswerDTO.setCom14(escapedCom14);
        surveyAnswerDTO.setCom15(escapedCom15);
        surveyAnswerDTO.setCom16(escapedCom16);
        surveyAnswerDTO.setCom17(escapedCom17);
        surveyAnswerDTO.setCom18(escapedCom18);
        surveyAnswerDTO.setCom19(escapedCom19);
        surveyAnswerDTO.setCom20(escapedCom20);
        return surveyAnswerDTO;
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
                .an16(surveyAnswer.getAn16())
                .an17(surveyAnswer.getAn17())
                .an18(surveyAnswer.getAn18())
                .an19(surveyAnswer.getAn19())
                .an20(surveyAnswer.getAn20())
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
                .com16(surveyAnswer.getCom16())
                .com17(surveyAnswer.getCom17())
                .com18(surveyAnswer.getCom18())
                .com19(surveyAnswer.getCom19())
                .com20(surveyAnswer.getCom20())
                .round(surveyAnswer.getRound())
                .lesson(surveyAnswer.getLesson())
                .build();

        return surveyAnswerDTO;
    }
}
