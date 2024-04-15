package com.ezen.management.service;

import com.ezen.management.domain.Question;
import com.ezen.management.domain.QuestionAnswer;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.QuestionAnswerDTO;

import com.ezen.management.dto.QuestionDTO;


import java.io.IOException;
import java.util.List;

public interface QuestionService {

    List<Question> findQuestionByName(String questionName);

    List<Question> findAll();

    Question findById(Long questionIdx);
    int insert(QuestionDTO questionDTO);

    void update(QuestionDTO questionDTO) throws IOException;

    void delete(Long questionIdx) throws IOException;

    void multiSave(List<QuestionDTO> questionDTOList);

    PageResponseDTO<Question> searchQuestion(PageRequestDTO pageRequestDTO);

}
