package com.ezen.management.dto;

import com.ezen.management.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long idx;

    private Long lessonIdx;
//    private Lesson lesson;

    private String lessonName;
    private int lessonNumber;
    private String name;
    private String birthday;
    private String email;
    private String phone;
    private String uuid;
    private String fileName;
    private String extension;
    private int counseling;
    private boolean pretest;
    private int score;
    private int survey;
    private int done;
    private String etc;

}
