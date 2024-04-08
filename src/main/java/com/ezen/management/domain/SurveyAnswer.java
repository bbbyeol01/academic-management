package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "lesson")
public class SurveyAnswer {//    설문 답변

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    private int round;

    //    객관식 답변
    private int an1;
    private int an2;
    private int an3;
    private int an4;
    private int an5;
    private int an6;
    private int an7;
    private int an8;
    private int an9;
    private int an10;
    private int an11;
    private int an12;
    private int an13;
    private int an14;
    private int an15;

    //    주관식 답변
    private String com1;
    private String com2;
    private String com3;
    private String com4;
    private String com5;
    private String com6;
    private String com7;
    private String com8;
    private String com9;
    private String com10;
    private String com11;
    private String com12;
    private String com13;
    private String com14;
    private String com15;

}

