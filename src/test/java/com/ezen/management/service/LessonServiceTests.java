package com.ezen.management.service;

import com.ezen.management.domain.Lesson;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class LessonServiceTests {


    @Autowired
    LessonService lessonService;

//    @Test
//    @Transactional
//    public void 선생님으로불러오기() throws Exception {
//        //given
//        Pageable pageable = PageRequest.of(0, 10);
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<Lesson> lessonPageResponseDTO = lessonService.ongoingLesson(pageRequestDTO, "teacher");
//
//        List<Lesson> dtoList = lessonPageResponseDTO.getDtoList();
//        dtoList.forEach(dto -> {
//            log.info("dto member : {}", dto.getMember());
//        });
//
//        //when
//
//        //then
//
//    }


}
