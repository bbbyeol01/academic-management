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
public class SurveyServiceImpl implements SurveyService{

    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    @Override
    public int modify(SurveyDtoList surveyDtoList, int round) {

        // 리스트 조회(엔티티)
        List<Survey> surveys = surveyRepository.findAllByRound(round);

        // 조회된 각 설문에 대해 SurveyDtoList에서 해당하는 값을 찾아서 엔티티에 반영
        for (Survey survey : surveys) {
            // SurveyDtoList에서 SurveyDto를 찾아서 엔티티에 반영
            for (SurveyDTO surveyDTO : surveyDtoList) {
                // SurveyDto의 회차와 설문 번호가 현재 설문의 회차와 번호와 일치하면 값을 반영
                if (Objects.equals(surveyDTO.getNumber(), Integer.toString(survey.getNumber()))) {
                    // 각 필드에 대해 엔티티에 값을 설정
                    String content = escapeHtml(surveyDTO.getContent());
                    String type = escapeHtml(surveyDTO.getType());
                    String item1 = escapeHtml(surveyDTO.getItem1());
                    String item2 = escapeHtml(surveyDTO.getItem2());
                    String item3 = escapeHtml(surveyDTO.getItem3());
                    String item4 = escapeHtml(surveyDTO.getItem4());
                    String item5 = escapeHtml(surveyDTO.getItem5());
                    String item6 = escapeHtml(surveyDTO.getItem6());
                    String item7 = escapeHtml(surveyDTO.getItem7());

                    // HTML 이스케이프 처리된 값을 엔티티에 반영
                    survey.change(
                            content,
                            type,
                            item1,
                            item2,
                            item3,
                            item4,
                            item5,
                            item6,
                            item7
                    );
                }
            }
        }

        // 저장
        List<Survey> savedSurveys = surveyRepository.saveAll(surveys);

        return savedSurveys.get(0).getRound();
    }

    // HTML 이스케이프
    private String escapeHtml(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    @Override
    public List<SurveyDTO> readAllByRound(int round) {

        // SurveyRepository의 findAllByRound 메서드를 사용하여 round 값에 해당하는 모든 설문 정보를 가져옴
        List<Survey> surveys = surveyRepository.findAllByRound(round);
        
        // Survey를 SurveyDTO로 변환하여 리스트로 반환
        return surveys.stream().map(this::surveyEntityToDTO).collect(Collectors.toList());
    }

}
