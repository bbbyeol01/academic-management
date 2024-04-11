package com.ezen.management.dto;

import com.ezen.management.domain.SubjectTest;
import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class SubjectTestList implements Iterable<SubjectTestListDTO> {

    private List<SubjectTestListDTO> subjectTestList;

    public SubjectTestList(){ subjectTestList = new ArrayList<>();}

    @Override
    public Iterator<SubjectTestListDTO> iterator() {
        return subjectTestList.iterator();
    }
}
