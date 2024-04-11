package com.ezen.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "curriculum")
public class Lesson extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Curriculum curriculum;

    //과정 설명(내용)
    private String content;

    //교사
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member member;

    //기수
    @Column(nullable = false)
    private int number;

    //인원
    @Builder.Default
    private int headCount = 0;

    @Column(nullable = false)
    private LocalDate startDay;

    @Column(nullable = false)
    private LocalDate endDay;

    @Column(nullable = false)
    private LocalDate survey1;

    @Column(nullable = false)
    private LocalDate survey2;

    @Column(nullable = false)
    private LocalDate survey3;

    private String classRoom;

    private String questionName;

    public void changeTeacher(Member member){
        this.member = member;
    }

    public void changeClassroom(String classRoom) {
        this.classRoom = classRoom;
    }

    public void changeQuestionName(String questionName){
        this.questionName = questionName;
    }

    public void headCountUp(){
        this.headCount++;
    }

    public void headCountDown(){
        if(headCount < 1){
            return;
        }

        this.headCount--;
    }

    public void changeCurriculum(Curriculum curriculum){this.curriculum = curriculum;}


    public void changeContent(String content){
        this.content = content;
    }

    public void changeNumber(int number){ this.number = number;}

    public void changeStartDay(LocalDate startDay){this.startDay = startDay;}

    public void changeEndDay(LocalDate endDay){this.endDay = endDay;}
    public void changeSurvey1(LocalDate survey1){this.survey1 = survey1;}
    public void changeSurvey2(LocalDate survey2){this.survey2 = survey2;}
    public void changeSurvey3(LocalDate survey3){this.survey3 = survey3;}


}