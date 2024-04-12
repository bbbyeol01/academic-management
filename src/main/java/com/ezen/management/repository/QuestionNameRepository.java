package com.ezen.management.repository;

import com.ezen.management.domain.QuestionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionNameRepository extends JpaRepository<QuestionName, String> {

    QuestionName findByName(String name);
}
