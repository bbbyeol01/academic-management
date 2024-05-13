package com.ezen.management.repository;

import com.ezen.management.domain.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCategoryRepository extends JpaRepository<NoticeCategory, Integer> {
}
