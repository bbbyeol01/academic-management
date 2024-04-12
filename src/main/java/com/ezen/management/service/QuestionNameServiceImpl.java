package com.ezen.management.service;

import com.ezen.management.AlreadyExistException;
import com.ezen.management.domain.QuestionName;
import com.ezen.management.repository.QuestionNameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionNameServiceImpl implements QuestionNameService {

    private final QuestionNameRepository questionNameRepository;
    @Override
    public List<QuestionName> findAll() {
        return questionNameRepository.findAll(Sort.by("name"));
    }

    @Override
    public void save(String name) {

        QuestionName byName = questionNameRepository.findByName(name);

        if(byName != null){
            throw new AlreadyExistException("이미 존재하는 문제 이름입니다.");
        }


        QuestionName questionName = QuestionName.builder()
                .name(name)
                .build();

//        questionName.changeName(name);
        questionNameRepository.save(questionName);
    }

    @Override
    public QuestionName findByName(String name) {
        return questionNameRepository.findByName(name);

    }

}
