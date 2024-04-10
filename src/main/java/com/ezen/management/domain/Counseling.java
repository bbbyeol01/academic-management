package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Counseling extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDateTime counselingDate;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private String writer;

    //상담회차
    private int round;

    @Column(nullable = false)
//    0은 대면, 1 전화, 2 온라인
    private int method;


    public void changeContent(int round, String content, int method, String writer){
        this.round = round;
        this.content = content;
        this.method = method;
        this.writer = writer;
    }



}
