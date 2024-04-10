package com.ezen.management.service;

import com.ezen.management.domain.Question;

import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.QuestionDTO;
import com.ezen.management.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<Question> findQuestionByName(String questionName) {
        return questionRepository.getQuestionsByName(questionName);
    }


    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Long questionIdx) {
        Optional<Question> byId = questionRepository.findById(questionIdx);
        return byId.get();
    }

    @Override
    public int insert(QuestionDTO questionDTO) {

        Question question = modelMapper.map(questionDTO, Question.class);
        questionRepository.save(question);

        return 1;
    }

    @Override
    public int update(QuestionDTO questionDTO) {

        Question byId = findById(questionDTO.getIdx());
        byId.changeContent(questionDTO.getContent());
        byId.changeExample(questionDTO.getExample());
        byId.changeAnswer(questionDTO.getAnswer());
//        파일 고치는 로직은 따로?
        byId.changeItem(questionDTO.getItem1(), questionDTO.getItem2(), questionDTO.getItem3(), questionDTO.getItem4());

        Question save = questionRepository.save(byId);

        return 1;
    }

    @Override
    public void delete(Long questionIdx) {
        Optional<Question> byId = questionRepository.findById(questionIdx);
        Question question = byId.orElseThrow();

        questionRepository.delete(question);

    }

    @Override
    public void multiSave(List<QuestionDTO> questionDTOList) {

        questionDTOList.forEach(questionDTO -> {
            Question question = Question.builder()
                    .name(questionDTO.getName())
                    .item1(questionDTO.getItem1())
                    .item2(questionDTO.getItem3())
                    .item3(questionDTO.getItem3())
                    .item4(questionDTO.getItem4())
                    .content(questionDTO.getContent())
                    .number(questionDTO.getNumber())
                    .answer(questionDTO.getAnswer())
                    .fileName(questionDTO.getFileName())
                    .example(questionDTO.getExample())
                    .build();

            questionRepository.save(question);

        });

    }

    @Override
    public PageResponseDTO<Question> searchQuestion(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Question> questionPage = questionRepository.searchQuestion(types, keyword, pageable);

        List<Question> dtoList = questionPage.getContent();

        return PageResponseDTO.<Question>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)questionPage.getTotalElements())
                .build();
    }
}
