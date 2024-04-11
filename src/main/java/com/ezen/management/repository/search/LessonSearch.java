package com.ezen.management.repository.search;

import com.ezen.management.domain.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LessonSearch {
    Page<Lesson> searchLesson(String[] types, String keyword, Pageable pageable, String userId);

    Page<Lesson> searchLessonOngoing(String[] types, String keyword, Pageable pageable, String userId);

    Page<Lesson> searchLessonEnd(String[] types, String keyword, Pageable pageable, String userId);


}
