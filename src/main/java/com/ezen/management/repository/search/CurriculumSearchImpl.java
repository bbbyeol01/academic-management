package com.ezen.management.repository.search;

import com.ezen.management.domain.Category;
import com.ezen.management.domain.Curriculum;
import com.ezen.management.domain.QCurriculum;
import com.ezen.management.domain.QSubject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CurriculumSearchImpl extends QuerydslRepositorySupport implements CurriculumSearch {
    public CurriculumSearchImpl() {
        super(Curriculum.class);
    }

    @Override
    public Page<Curriculum> searchCurriculum(String[] types, String keyword, Pageable pageable) {

        QCurriculum curriculum = QCurriculum.curriculum;

        JPQLQuery<Curriculum> query = from(curriculum);

        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if(types != null && keyword != null){
            for(String type : types){
                switch (type) {
                    case "n":
                        booleanBuilder.or(curriculum.name.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(curriculum.category.name.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.or(curriculum.time.eq(Integer.parseInt(keyword)));
                        break;
                    case "d":
                        booleanBuilder.or(curriculum.day.eq(Integer.parseInt(keyword)));
                        break;
                }
            }
        }
        query.orderBy(curriculum.name.asc());
        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Curriculum> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
