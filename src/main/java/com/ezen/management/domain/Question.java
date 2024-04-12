package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

//    주제
//    동일한 주제가 20개 -> 한 수업의 문제가 됨
    private String name;

//    문제
    private String content;
//    보기
    private String example;
//    사진
    private String uuid;
    private String fileName;

    private int number;

    private int answer;

    private String item1;
    private String item2;
    private String item3;
    private String item4;


    public void changeContent(String content){
        this.content = content;
    }

    public void changeExample(String example){
        this.example = example;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }


    public void changeItem(String item1, String item2, String item3, String item4){
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    public void changeAnswer(int answer){
        this.answer = answer;
    }
}
