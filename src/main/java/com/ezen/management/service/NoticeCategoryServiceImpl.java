package com.ezen.management.service;

import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.repository.NoticeCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeCategoryServiceImpl implements NoticeCategoryService{

    private final NoticeCategoryRepository noticeCategoryRepository;


    @Override
    public NoticeCategory findById(int idx) {
        Optional<NoticeCategory> byId = noticeCategoryRepository.findById(idx);

        return byId.get();
    }
}
