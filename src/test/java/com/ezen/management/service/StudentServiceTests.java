package com.ezen.management.service;

import com.ezen.management.domain.Student;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class StudentServiceTests {

    @Autowired
    StudentService studentService;

    @Test
    public void 학생선택() throws Exception {
        //given

        //when
        Student byId = studentService.findById(1L);
        log.info("student ... {}", byId);

        //then
        Assertions.assertThat(byId).isNotNull();

    }

    @Test
    public void 검색() throws Exception {
        //given
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .keyword("기업")
                .type("nl")
                .build();

        PageResponseDTO<Student> studentPageResponseDTO = studentService.searchStudent(null, pageRequestDTO);
        List<Student> dtoList = studentPageResponseDTO.getDtoList();

        dtoList.forEach(dto -> {
            log.info("student : {} ", dto);
        });

        //when

        //then

    }
}
