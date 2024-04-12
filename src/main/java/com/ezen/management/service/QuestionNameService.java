package com.ezen.management.service;

import com.ezen.management.domain.QuestionName;

import java.util.List;

public interface QuestionNameService {

    List<QuestionName> findAll();

    void save(String name);

    QuestionName findByName(String name);

}
