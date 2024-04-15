package com.ezen.management.dto;

import com.ezen.management.domain.Lesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnswerDTO {

    // 설문 답변
    private Long idx;

    private int round;

    // 객관식 답변
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
    private int an16;
    private int an17;
    private int an18;
    private int an19;
    private int an20;


    // 주관식 답변
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
    private String com16;
    private String com17;
    private String com18;
    private String com19;
    private String com20;

    private Lesson lesson;

    // 주관식 답변에 대한 getter 메서드 수정
    public String getCom1() {
        return (com1 == null) ? "" : com1;
    }

    public String getCom2() {
        return (com2 == null) ? "" : com2;
    }

    public String getCom3() {
        return (com3 == null) ? "" : com3;
    }

    public String getCom4() {
        return (com4 == null) ? "" : com4;
    }

    public String getCom5() {
        return (com5 == null) ? "" : com5;
    }


    public String getCom6() {
        return (com6 == null) ? "" : com6;
    }

    public String getCom7() {
        return (com7 == null) ? "" : com7;
    }

    public String getCom8() {
        return (com8 == null) ? "" : com8;
    }

    public String getCom9() {
        return (com9 == null) ? "" : com9;
    }

    public String getCom10() {
        return (com10 == null) ? "" : com10;
    }

    public String getCom11() {
        return (com11 == null) ? "" : com11;
    }

    public String getCom12() {
        return (com12 == null) ? "" : com12;
    }

    public String getCom13() {
        return (com13 == null) ? "" : com13;
    }

    public String getCom14() {
        return (com14 == null) ? "" : com14;
    }

    public String getCom15() {
        return (com15 == null) ? "" : com15;
    }

    public String getCom16() {
        return (com16 == null) ? "" : com16;
    }

    public String getCom17() {
        return (com17 == null) ? "" : com17;
    }

    public String getCom18() {
        return (com18 == null) ? "" : com18;
    }

    public String getCom19() {
        return (com19 == null) ? "" : com19;
    }

    public String getCom20() {
        return (com20 == null) ? "" : com20;
    }

}