package com.ezen.management.repository.search;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.QLesson;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class LessonSearchImpl extends QuerydslRepositorySupport implements LessonSearch {


    public LessonSearchImpl() {
        super(Lesson.class);
    }

    @Override
    public Page<Lesson> searchLesson(String[] types, String keyword, Pageable pageable) {

        QLesson lesson = QLesson.lesson;

        JPQLQuery<Lesson> query = from(lesson);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (types != null && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "c":
                        booleanBuilder.or(lesson.curriculum.category.name.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.and(lesson.curriculum.name.contains(keyword));
                        break;
                    case "n":
                        booleanBuilder.or(lesson.number.eq(Integer.parseInt(keyword)));
                        break;
                    case "m":
                        booleanBuilder.or(lesson.member.name.contains(keyword));
                        break;
                    case "h":
                        booleanBuilder.or(lesson.headCount.eq(Integer.parseInt(keyword)));
                        break;
                    case "s":
                        booleanBuilder.or(lesson.startDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "e":
                        booleanBuilder.or(lesson.endDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "r":
                        booleanBuilder.or(lesson.classRoom.contains(keyword));

                }
            }
        }

        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Lesson> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Lesson> searchLessonOngoing(String[] types, String keyword, Pageable pageable) {

        QLesson lesson = QLesson.lesson;

        JPQLQuery<Lesson> query = from(lesson);
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        LocalDate now = currentTimestamp.toLocalDateTime().toLocalDate();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(lesson.endDay.gt(now));
        booleanBuilder.and(lesson.startDay.loe(now));

        if (types != null && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "c":
                        booleanBuilder.and(lesson.curriculum.category.name.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.and(lesson.curriculum.name.contains(keyword));
                        break;
                    case "n":
                        booleanBuilder.and(lesson.number.eq(Integer.parseInt(keyword)));
                        break;
                    case "m":
                        booleanBuilder.and(lesson.member.name.contains(keyword));
                        break;
                    case "h":
                        booleanBuilder.and(lesson.headCount.eq(Integer.parseInt(keyword)));
                        break;
                    case "s":
                        booleanBuilder.and(lesson.startDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "e":
                        booleanBuilder.and(lesson.endDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "r":
                        booleanBuilder.and(lesson.classRoom.contains(keyword));
                        break;
                }
            }
        }

        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Lesson> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Lesson> searchLessonEnd(String[] types, String keyword, Pageable pageable) {

        QLesson lesson = QLesson.lesson;

        JPQLQuery<Lesson> query = from(lesson);
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        LocalDate now = currentTimestamp.toLocalDateTime().toLocalDate();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(lesson.endDay.loe(now));
//        booleanBuilder.and(lesson.startDay.goe(now));

        if (types != null && keyword != null) {
            for (String type : types) {
                switch (type) {
                    case "c":
                        booleanBuilder.and(lesson.curriculum.category.name.contains(keyword));
                        break;
                    case "t":
                        booleanBuilder.and(lesson.curriculum.name.contains(keyword));
                        break;
                    case "n":
                        booleanBuilder.and(lesson.number.eq(Integer.parseInt(keyword)));
                        break;
                    case "m":
                        booleanBuilder.and(lesson.member.name.contains(keyword));
                        break;
                    case "h":
                        booleanBuilder.and(lesson.headCount.eq(Integer.parseInt(keyword)));
                        break;
                    case "s":
                        booleanBuilder.and(lesson.startDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "e":
                        booleanBuilder.and(lesson.endDay.eq(LocalDate.parse(keyword)));
                        break;
                    case "r":
                        booleanBuilder.and(lesson.classRoom.contains(keyword));
                        break;
                }
            }
        }

        query.where(booleanBuilder);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Lesson> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
