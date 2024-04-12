package com.ezen.management.repository;


import com.ezen.management.domain.Survey;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class SurveyRepositoryTests {

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    public void testSaveSurveys() {
        for (int round = 1; round <= 3; round++) {
            for (int i = 1; i <= 20; i++) {
                if (i == 10) {
                    Survey survey = Survey.builder()
                            .number(i)
                            .round(round)
                            .content("기타 개선 및 고충 사항에 대하여 작성해주세요")
                            .type("주관식")
                            .build();
                    surveyRepository.save(survey);
                } else {
                    Survey survey = Survey.builder()
                            .number(i)
                            .round(round)
                            .content("현재까지 본 훈련과정에 대한 전반적 만족한다.")
                            .type("객관식")
                            .item1("매우불만족")
                            .item2("불만족")
                            .item3("약간불만족")
                            .item4("보통")
                            .item5("약간만족")
                            .item6("만족")
                            .item7("매우만족")
                            .build();
                    surveyRepository.save(survey);
                }
            }
        }
    }

}
