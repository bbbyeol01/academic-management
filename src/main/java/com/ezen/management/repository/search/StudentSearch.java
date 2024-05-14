package com.ezen.management.repository.search;

import com.ezen.management.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentSearch {

    Page<Student> searchStudent(Long lessonIdx ,String[] types, String keyword, Pageable pageable);

    Page<Student> searchStudentName(Long lessonIdx, String[] types, String keyword, Pageable pageable);
}
