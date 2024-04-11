package com.ezen.management.service;

import com.ezen.management.domain.Survey;
import com.ezen.management.dto.SurveyDTO;
import com.ezen.management.dto.SurveyDtoList;

import java.util.ArrayList;
import java.util.List;

public interface SurveyService {

    //설문 등록
    int register(SurveyDtoList surveyDtoList);

    //설문 1건 조회
    List<SurveyDTO> readAllByRound(int round);

    //설문 전체 조회
    List<SurveyDTO> surveyList();

    //설문 수정
    int modify(SurveyDtoList surveyDtoList, int round);

    //설문 삭제
    int deleteAllByRound(int round);

    //문제
    List<SurveyDTO> findByRoundAndNumber(int round, int number);


    default Survey surveyDtoToEntity(SurveyDTO surveyDTO){

        Survey survey = Survey.builder()
                .idx(surveyDTO.getIdx())
                .number(Integer.parseInt(surveyDTO.getNumber()))
                .round(surveyDTO.getRound())
                .content(surveyDTO.getContent())
                .type(surveyDTO.getType())
                .item1(surveyDTO.getItem1())
                .item2(surveyDTO.getItem2())
                .item3(surveyDTO.getItem3())
                .item4(surveyDTO.getItem4())
                .item5(surveyDTO.getItem5())
                .item6(surveyDTO.getItem6())
                .item7(surveyDTO.getItem7())
                .build();

        return survey;
    }

    default SurveyDTO surveyEntityToDTO(Survey survey){

        SurveyDTO surveyDTO = SurveyDTO.builder()
                .idx(survey.getIdx())
                .number(Integer.toString(survey.getNumber()))
                .round(survey.getRound())
                .content(survey.getContent())
                .type(survey.getType())
                .item1(survey.getItem1())
                .item2(survey.getItem2())
                .item3(survey.getItem3())
                .item4(survey.getItem4())
                .item5(survey.getItem5())
                .item6(survey.getItem6())
                .item7(survey.getItem7())
                .build();

        return surveyDTO;
    }

    default List<Survey> surveyDtoListToEntityList(SurveyDtoList surveyDtoList) {
        List<Survey> surveyList = new ArrayList<>();
        for (SurveyDTO surveyDTO : surveyDtoList) {
            Survey survey = surveyDtoToEntity(surveyDTO);
            surveyList.add(survey);
        }
        return surveyList;
    }
}
