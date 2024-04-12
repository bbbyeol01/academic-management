package com.ezen.management.repository.search;

import com.ezen.management.domain.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class StudentSearchImpl extends QuerydslRepositorySupport implements StudentSearch {

    public StudentSearchImpl() {
        super(Student.class);
    }

    @Override
    @Transactional
    public Page<Student> searchStudent(Long lessonIdx, String[] types, String keyword, Pageable pageable) {

        log.info("types : {}", Arrays.toString(types));

        QStudent student = QStudent.student;

        JPQLQuery<Student> query = from(student);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(lessonIdx != null){
            booleanBuilder.and(student.lesson.idx.eq(lessonIdx));
        }

        if (types != null && keyword != null) {

            log.info("true");

            for (String type : types) {
                switch (type) {
                    case "n" : // 학생 이름
//                        여기가 and문이 되면 && 로 수행하기 때문에 오류. || 연산이기 때문에 or. 헷갈리지 말 것.
                        booleanBuilder.or(student.name.contains(keyword));
                        break;
                    case "l" : // 수업 이름
                        booleanBuilder.or(student.lesson.curriculum.name.contains(keyword));
                        booleanBuilder.or(student.lesson.number.stringValue().contains(keyword));
                        break;
                }
            }
        }

        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Student> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
