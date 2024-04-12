package com.ezen.management.dto;

import com.ezen.management.domain.QuestionAnswer;
import com.ezen.management.domain.Student;
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDTO {

//    private Student student;

    private Long studentIdx;

//    ex) 자바 풀스택
    private String name;

    @NotNull
    private String an1;
    @NotNull
    private String an2;
    @NotNull
    private String an3;
    @NotNull
    private String an4;
    @NotNull
    private String an5;
    @NotNull
    private String an6;
    @NotNull
    private String an7;
    @NotNull
    private String an8;
    @NotNull
    private String an9;
    @NotNull
    private String an10;
    @NotNull
    private String an11;
    @NotNull
    private String an12;
    @NotNull
    private String an13;
    @NotNull
    private String an14;
    @NotNull
    private String an15;
    @NotNull
    private String an16;
    @NotNull
    private String an17;
    @NotNull
    private String an18;
    @NotNull
    private String an19;
    @NotNull
    private String an20;

//    private List<String> testPaper = new ArrayList<>();

    public List<String> getTestPaper(){
        List<String> testPaper = new ArrayList<>();
        testPaper.add(an1);
        testPaper.add(an2);
        testPaper.add(an3);
        testPaper.add(an4);
        testPaper.add(an5);
        testPaper.add(an6);
        testPaper.add(an7);
        testPaper.add(an8);
        testPaper.add(an9);
        testPaper.add(an10);
        testPaper.add(an11);
        testPaper.add(an12);
        testPaper.add(an13);
        testPaper.add(an14);
        testPaper.add(an15);
        testPaper.add(an16);
        testPaper.add(an17);
        testPaper.add(an18);
        testPaper.add(an19);
        testPaper.add(an20);

        return testPaper;

    }
}
