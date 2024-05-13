package com.ezen.management.service;

import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.NoticeCategoryRepository;
import com.ezen.management.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeCategoryRepository noticeCategoryRepository;

    @Override
    public PageResponseDTO<Notice> findAll(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable();
        Page<Notice> all = noticeRepository.findAll(pageable);

        List<Notice> dtoList = all.getContent();

        return PageResponseDTO.<Notice>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)all.getTotalElements())
                .build();
    }

    @Override
    public int insert(NoticeDTO noticeDTO) {

        Optional<NoticeCategory> noticeCategoryById = noticeCategoryRepository.findById(noticeDTO.getCategoryIdx());

        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .writer(noticeDTO.getWriter())
                .admin(noticeDTO.isAdmin())
                .category(noticeCategoryById.get())
                .build();

        Notice save = noticeRepository.save(notice);

        return save.getIdx();
    }

    @Override
    public Notice findById(int idx) {

        return noticeRepository.findByIdWithCategory(idx);


    }
}
