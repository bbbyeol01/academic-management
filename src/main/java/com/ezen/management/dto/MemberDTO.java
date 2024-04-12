package com.ezen.management.dto;

import com.ezen.management.domain.MemberState;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

    private String id;
    private String pwd;
    private String name;
    private String uuid;
    private String fileName;
    private MemberState memberState;

}
