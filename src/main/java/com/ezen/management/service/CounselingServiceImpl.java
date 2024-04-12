package com.ezen.management.service;


import com.ezen.management.domain.Counseling;
import com.ezen.management.domain.Student;
import com.ezen.management.dto.CounselingDTO;
import com.ezen.management.repository.CounselingRepository;
import com.ezen.management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CounselingServiceImpl implements CounselingService {

    @Autowired
    private final CounselingRepository counselingRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private  final StudentRepository studentRepository;


   

    //추가하기
    @Override
    public Long insert(CounselingDTO counselingDTO) {
        // CounselingDTO로부터 Counseling 엔티티로 매핑
        Counseling counseling = modelMapper.map(counselingDTO, Counseling.class);

        log.info("counseling= " + counseling);

        //student에도 저장해야 해요
        Optional<Student> studentResult = studentRepository.findById(counselingDTO.getStudentIdx());
        Student student = studentResult.orElseThrow();

        // Student 엔티티에 counseling 추가
        student.insertCounseling();
        studentRepository.save(student);

        // 저장하고 저장된 엔티티의 idx 반환
        Long idx = (long) counselingRepository.save(counseling).getIdx();
        log.info("idx= " + idx);

        return idx;
    }


    //수정하기
    @Override
    public void update(CounselingDTO counselingDTO) {

        Optional<Counseling> result = counselingRepository.findById(counselingDTO.getIdx());

        Counseling counseling = result.orElseThrow();

        //Entity에 추가함
        counseling.changeContent(counselingDTO.getRound()
                ,counselingDTO.getContent()
                ,counselingDTO.getMethod()
                ,counselingDTO.getWriter());
        
        counselingRepository.save(counseling);

    }

    
    //삭제하기
    @Override
    public void delete(Long idx) {


        //counseling 값 가져오기
        Optional<Counseling> result = counselingRepository.findById(idx);
        Counseling counseling = result.orElseThrow();

        //학생 정보 찾아오기
        Optional<Student> studentResult = studentRepository.findById(result.get().getStudent().getIdx());
        Student student = studentResult.orElseThrow();

        //학생Entity 값 변경
        student.deleteCounseling();
        studentRepository.save(student);

        counselingRepository.deleteById(idx);
    }


    
    //상담정보에서 학생idx로 조회
    @Override
    public Counseling findById(Long studentIdx) {

         Optional<Counseling> studentById = counselingRepository.findById(studentIdx);

         if(studentById.isPresent()){
            //실제 객체를 가져오기 위해 .get() 사용
             Counseling counseling = studentById.get();
             log.info("studentById= " , studentById);
             return counseling;

         }else {

             // 예외처리가 들어갈 구간
             return null;
         }
    }


    //단일 상담정보 조회
    @Override
    public Counseling findByidx(Long idx) {

        Counseling counseling = counselingRepository.findByIdx(idx);

        log.info("counseling= " + counseling);

        return counseling;
    }
    

    //목록으로 학생정보 가져오기
    @Override
    public List<Counseling> findByStudentIdx(Long studentIdx) {


        List<Counseling> counselingList = counselingRepository.findByStudentIdx(studentIdx);
        log.info("counselingList= " + counselingList);


        return counselingList;
    }



}
