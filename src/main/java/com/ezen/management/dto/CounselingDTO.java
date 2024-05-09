package com.ezen.management.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CounselingDTO {


    private Long idx;                           //상담idx

    private Long studentIdx;                    //학생idx

    @Size(min = 1, max = 6)
    private int round;                          //회차

    private LocalDateTime counselingDate;       //상담일자

    private String content;                     //내용

    @Size(min = 0, max = 2)
    private int method;                         //방식

    private String writer;                      //담당자
//    private LocalDateTime regDate;            //추가날짜
//    private LocalDateTime modDate;            //수정날짜




}
