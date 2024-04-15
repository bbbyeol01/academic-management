package com.ezen.management.service;


import com.ezen.management.domain.Counseling;
import com.ezen.management.dto.CounselingDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.CounselingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class CounselingServiceTests {

    @Autowired
    private CounselingService counselingService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private  CounselingRepository counselingRepository;



    //학생이 추가되면 자동으로 추가되어야 함 + 임시적으로 담당교사가 담당자로 들어가게. + student에서 증가하게(댓글 흠)
    @Test
    public void testInsert(){
        log.info("counselingService.getClass().getName()= " + counselingService.getClass().getName());

        //상담시간
        LocalDateTime start = LocalDateTime.now();


        CounselingDTO counselingDTO = CounselingDTO.builder()
                .studentIdx(1L)
                .counselingDate(start)
                .content("아주 훌륭해요")
                .method(1)
                .writer("고흥빵")
                .build();

        Long idx = counselingService.insert(counselingDTO);

        log.info("idx= " + idx);


    }


    @Test
    public void testUpdate(){

        CounselingDTO counselingDTO = CounselingDTO.builder()
                .idx(2L)
                .round(1)
                .content("잘해내고 있다는 용기의 드래곤을 줌")
                .method(0)
                .writer("감자도리")
                .build();

        counselingService.update(counselingDTO);
        log.info("counselingDTO= " + counselingDTO);

    }




    //왜 안됨
    @Test
    @Transactional
    public void testDelete(){

        //삭제위한 idx값
        Counseling counseling = counselingService.findByidx(2L);

        //삭제 전 상담이 있는지 여부 확인
        Counseling counselingServiceByStudentIdx = (Counseling) counselingService.findByStudentIdx(counseling.getStudent().getIdx());

        if(counselingServiceByStudentIdx == null){
            log.info("nullnunununununununlllllllllll");
        }


        //삭제위한 메서드 호출
        //counselingService.delete(counseling.getIdx());

        log.info("counseling= " +counseling);



    }



}
