package com.ezen.management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NoticeDTO {

    private int idx;
    private int categoryIdx;
//    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String writeDate;
    private String categoryName;
    private String title;
    private String content;
    private String writer;
    private String writerName;
    private boolean admin;
    private boolean hold;
    private String fileName;
    private String uuid;
    private String extension;
}
