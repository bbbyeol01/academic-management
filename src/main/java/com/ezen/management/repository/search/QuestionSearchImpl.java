package com.ezen.management.repository.search;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.QQuestion;
import com.ezen.management.domain.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.Enumerated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QuestionSearchImpl extends QuerydslRepositorySupport implements QuestionSearch {

    public QuestionSearchImpl(){
        super(Question.class);
    }
    @Override
    public Page<Question> searchQuestion(String[] types, String keyword, Pageable pageable) {

        QQuestion question = QQuestion.question;

        JPQLQuery<Question> query = from(question);

        query.orderBy(question.name.asc());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(types != null && keyword != null){

            for(String type : types){
                switch (type){
                    case "n" :
                        //        where name like ...
                        booleanBuilder.or(question.name.contains(keyword));
                        break;
                    case "c" :
                        //        or content like ...
                        booleanBuilder.or(question.content.contains(keyword));
                        break;
                }
            }// for
        }// if

        query.where(booleanBuilder);

        this.getQuerydsl().applyPagination(pageable, query);

        List<Question> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);


    }
}
