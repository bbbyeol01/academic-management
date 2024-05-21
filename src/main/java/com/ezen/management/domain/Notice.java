package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "category")
public class Notice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private NoticeCategory category;

//    작성자 아이디
    private String writer;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

//    회원 전용
    @Builder.Default
    private boolean admin = false;

//    상단 고정
    @Builder.Default
    private boolean hold = false;

    @Builder.Default
    private boolean del = false;

    public void modify(NoticeCategory category, String title, String content, boolean admin, boolean hold){
        this.category = category;
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.hold = hold;
    }

    public void delete(){
        this.del = true;
    }
}
