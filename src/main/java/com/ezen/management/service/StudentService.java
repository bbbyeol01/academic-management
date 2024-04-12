package com.ezen.management.service;

import com.ezen.management.domain.Student;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student findByLessonIdxAndName(Long lessonIdx, String name);

    Student findById(Long studentIdx);

    List<Student> findAll();

    PageResponseDTO<Student> searchStudent(Long lessonIdx, PageRequestDTO pageRequestDTO);

    void insert(StudentDTO studentDTO);

    void modify(StudentDTO studentDTO) throws IOException;

    void delete(StudentDTO studentDTO) throws IOException;

}
