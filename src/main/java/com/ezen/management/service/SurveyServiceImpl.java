package com.ezen.management.service;

import com.ezen.management.domain.Survey;
import com.ezen.management.dto.SurveyDTO;
import com.ezen.management.dto.SurveyDtoList;
import com.ezen.management.repository.SurveyAnswerRepository;
import com.ezen.management.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
//@Transactional //일단 보류해놓자
public class SurveyServiceImpl implements SurveyService{

    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    @Override
    public int register(SurveyDtoList surveyDtoList) {

        // Entity 변환
        List<Survey> surveyList = surveyDtoListToEntityList(surveyDtoList);

        // DB 저장
        List<Survey> savedSurveys = surveyRepository.saveAll(surveyList);

        return savedSurveys.get(0).getRound();
    }

    @Override
    public List<SurveyDTO> surveyList() {

        List<Survey> surveyList = surveyRepository.findAll();
        List<SurveyDTO> surveyDTOList = surveyList.stream()
                .map(this::surveyEntityToDTO)
                .collect(Collectors.toList());

        return surveyDTOList;
    }

    @Override
    public int modify(SurveyDtoList surveyDtoList, int round) {

        //리스트 조회(엔티티)
        List<Survey> surveys = surveyRepository.findAllByRound(round);

        // 조회된 각 설문에 대해 SurveyDtoList에서 해당하는 값을 찾아서 엔티티에 반영합니다.
        for (Survey survey : surveys) {
            // SurveyDtoList에서 SurveyDto를 찾아서 엔티티에 반영합니다.
            for (SurveyDTO surveyDTO : surveyDtoList) {
                // SurveyDto의 회차와 설문 번호가 현재 설문의 회차와 번호와 일치하면 값을 반영합니다.
                if (Objects.equals(surveyDTO.getNumber(), Integer.toString(survey.getNumber()))){
                    // 각 필드에 대해 엔티티에 값을 설정합니다.
                    survey.change(
                            surveyDTO.getContent(),
                            surveyDTO.getType(),
                            surveyDTO.getItem1(),
                            surveyDTO.getItem2(),
                            surveyDTO.getItem3(),
                            surveyDTO.getItem4(),
                            surveyDTO.getItem5(),
                            surveyDTO.getItem6(),
                            surveyDTO.getItem7());
                };
            }
        }

        //저장
        List<Survey> savedSurveys = surveyRepository.saveAll(surveys);

        return savedSurveys.get(0).getRound();
    }

    @Override
    public List<SurveyDTO> readAllByRound(int round) {

        // SurveyRepository의 findAllByRound 메서드를 사용하여 round 값에 해당하는 모든 설문 정보를 가져옴
        List<Survey> surveys = surveyRepository.findAllByRound(round);
        
        // Survey를 SurveyDTO로 변환하여 리스트로 반환
        return surveys.stream().map(this::surveyEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public int deleteAllByRound(int round) {

        int result = surveyRepository.deleteAllByRound(round);

        return result;
    }


    //문제1
    @Override
    public List<SurveyDTO> findByRoundAndNumber(int round, int number) {

        List<Survey> surveyList = surveyRepository.findByRoundAndNumber(round,number);

        List<SurveyDTO> surveyDTOList = surveyList.stream()
                .map(this::surveyEntityToDTO)
                .collect(Collectors.toList());

        return surveyDTOList;
    }
}
