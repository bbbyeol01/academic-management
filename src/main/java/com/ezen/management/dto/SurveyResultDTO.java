package com.ezen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResultDTO {

    private String an;
    private int sumOf1;
    private int sumOf2;
    private int sumOf3;
    private int sumOf4;
    private int sumOf5;
    private int sumOf6;
    private int sumOf7;

}
