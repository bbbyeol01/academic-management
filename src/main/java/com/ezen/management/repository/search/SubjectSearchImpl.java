package com.ezen.management.repository.search;

import com.ezen.management.domain.QSubject;
import com.ezen.management.domain.Subject;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SubjectSearchImpl extends QuerydslRepositorySupport implements SubjectSearch {

    // Subject 를 빈 클래스로 지정해준다.
    public SubjectSearchImpl() {
        super(Subject.class);
    }

    @Override
    public Page<Subject> searchSubject(String[] types, String keyword, Pageable pageable) {

        QSubject subject = QSubject.subject;

        JPQLQuery<Subject> query = from(subject);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(types != null && keyword != null){
            for (String type : types){
                switch (type){
                    case "n" :
                        booleanBuilder.or(subject.name.contains(keyword));
                        break;
                    case "m" :
                        booleanBuilder.or(subject.method.contains(keyword));
                        break;
                }
            }
        }

        query.orderBy(subject.name.asc());
        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Subject> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

}
