package com.ezen.management.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class SubjectTestListDTO {

    private Long idx;

    private Long studentIdx;

    private String subject;

    private String state;
}
