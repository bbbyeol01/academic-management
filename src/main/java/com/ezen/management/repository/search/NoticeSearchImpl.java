package com.ezen.management.repository.search;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.domain.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoticeSearchImpl extends QuerydslRepositorySupport implements NoticeSearch {
    public NoticeSearchImpl() {
        super(Notice.class);
    }

    @Override
    public Page<Notice> searchNotice(String[] types, String keyword, Pageable pageable, NoticeCategory category) {

        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice);

        query.orderBy(notice.regDate.desc());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(types != null && keyword != null){

            for(String type : types){
                switch (type){
                    case "t" :
                        //        where id like ...
                        booleanBuilder.or(notice.title.contains(keyword));
                        break;
                    case "c" :
                        //        or name like ...
                        booleanBuilder.or(notice.content.contains(keyword));
                        break;
                }
            }// for
        }// if

        if(category != null){
            booleanBuilder.and(notice.category.eq(category));
        }


        query.where(booleanBuilder);

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
