package com.ezen.management.repository;

import com.ezen.management.domain.Counseling;
import com.ezen.management.domain.Student;
import com.ezen.management.repository.search.CounselingSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface CounselingRepository extends JpaRepository<Counseling, Long>, CounselingSearch {


    //학생정보를 List로 받아와서 처리해야함 / 자동으로 query 생성하는 JPQL
    List<Counseling> findByStudentIdx(Long studentIdx);

    //한개의 정보조회
    Counseling findByIdx(Long idx);


    //list Join으로 가져와 보기
    @Query("select c from Counseling c join Student s on c.student.idx = s.idx where c.student = :student")
    Page<Counseling> getCounselingWithStudentName(Student student, Pageable pageable);




}
