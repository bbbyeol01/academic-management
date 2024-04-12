package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "lesson")
public class Student extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    private String name;
    private String birthday;
    private String email;
    private String phone;

    private String uuid;

    @Builder.Default
    private String fileName = "default_profile.jpg";

    //    상담 등록 시 +1
    @Builder.Default
    private int counseling = 0;

    //    사전 평가 여부
//    사전 평가 등록 시 +1
    @Builder.Default
    private boolean pretest = false;

    //    사전 평가 점수
    @Builder.Default
    private int score = 0;


    @Builder.Default
    private int survey = 0;

    @Builder.Default
//    0 진행중 / 1 수료 / 2 하차
    private int done = 0;



    private String etc;

    //상담 추가시
    public void insertCounseling(){
        this.counseling++;
    }

    //상담 삭제시
    public void deleteCounseling(){
        this.counseling--;
    }

    public void grading(int score){
        this.pretest = true;
        this.score = score;
    }

    public void insertSurvey(){
        this.survey++;
    }

    public void changeFileName(String uuid, String fileName){
        this.uuid = uuid;
        this.fileName = fileName;
    }

    public void changeEtc(String etc){
        this.etc = etc;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeBirthday(String birthday){
        this.birthday = birthday;
    }

    public void changeEmail(String email){
        this.email = email;
    }

    public void changePhone(String phone){
        this.phone = phone;
    }


}