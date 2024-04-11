package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

//    설문 번호
    private int number;

//    회차
    private int round;
    
//    질문
    @Column(nullable = false)
    private String content;

//    답변 타입
    @Column
    private String type;

//    답변
    @Column
    private String item1;
    @Column
    private String item2;
    @Column
    private String item3;
    @Column
    private String item4;
    @Column
    private String item5;
    @Column
    private String item6;
    @Column
    private String item7;

    // 수정 시 사용되는 메서드
    public void change(String content, String type, String item1, String item2, String item3, String item4, String item5, String item6, String item7) {
        this.content = content;
        this.type = type;

        // 선택안함이거나 주관식일 때, 입력이 없으면 0으로 설정
        if ("선택안함".equals(type) || "주관식".equals(type)) {
            this.item1 = null;
            this.item2 = null;
            this.item3 = null;
            this.item4 = null;
            this.item5 = null;
            this.item6 = null;
            this.item7 = null;
        } else {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
            this.item4 = item4;
            this.item5 = item5;
            this.item6 = item6;
            this.item7 = item7;
        }
    }

}
