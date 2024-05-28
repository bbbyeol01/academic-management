package com.ezen.management.service;

import com.ezen.management.domain.Question;

import com.ezen.management.domain.QuestionName;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.QuestionDTO;
import com.ezen.management.repository.QuestionNameRepository;
import com.ezen.management.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    @Value("${com.ezen.management.upload.path}")
    private String uploadPath;


    private final QuestionRepository questionRepository;
    private final QuestionNameRepository questionNameRepository;

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
    public void update(QuestionDTO questionDTO) throws IOException {

        log.error("" + questionDTO);

        Question byId = findById(questionDTO.getIdx());
        byId.changeContent(questionDTO.getContent());
        byId.changeExample(questionDTO.getExample());
        byId.changeAnswer(questionDTO.getAnswer());
        byId.changeItem(questionDTO.getItem1(), questionDTO.getItem2(), questionDTO.getItem3(), questionDTO.getItem4());

        if(questionDTO.getUuid() != null){

            try{
                Resource resource = new FileSystemResource(uploadPath + File.separator + byId.getUuid() + byId.getExtension());
                resource.getFile().delete();
            }catch (IOException e){
                log.error(e.getMessage());
                throw new IOException("파일이 삭제되지 않았습니다.");
            }

            byId.changeFileName(questionDTO.getUuid(), questionDTO.getFileName(), questionDTO.getExtension());
        }

        Question save = questionRepository.save(byId);

    }

    @Override
    public void delete(Long questionIdx) throws IOException {
        Optional<Question> byId = questionRepository.findById(questionIdx);
        Question question = byId.orElseThrow();

        String name = question.getName();

        List<Question> questionsByName = questionRepository.getQuestionsByName(question.getName());

//        분류가 하나 남았으면 분류도 삭제
        if (questionsByName.size() <= 1){
            QuestionName byName = questionNameRepository.findByName(name);
            questionNameRepository.delete(byName);
        }

        if(question.getUuid() != null){
            Resource resource = new FileSystemResource(uploadPath + File.separator + question.getUuid() + '_' + question.getFileName());

            try{
                resource.getFile().delete();
            }catch (IOException e){
                log.error("파일이 삭제되지 않았습니다.");
                throw new IOException();
            }
        }

        questionRepository.delete(question);

    }

    @Override
    public void multiSave(List<QuestionDTO> questionDTOList) {

        questionDTOList.forEach(questionDTO -> {
            Question question = Question.builder()
                    .name(questionDTO.getName())
                    .item1(questionDTO.getItem1())
                    .item2(questionDTO.getItem2())
                    .item3(questionDTO.getItem3())
                    .item4(questionDTO.getItem4())
                    .content(questionDTO.getContent())
                    .number(questionDTO.getNumber())
                    .answer(questionDTO.getAnswer())
                    .uuid(questionDTO.getUuid())
                    .fileName(questionDTO.getFileName())
                    .extension(questionDTO.getExtension())
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
