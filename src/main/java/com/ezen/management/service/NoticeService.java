package com.ezen.management.service;

import com.ezen.management.domain.Notice;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;

public interface NoticeService {

    PageResponseDTO<Notice> findAll(PageRequestDTO pageRequestDTO);

    int insert(NoticeDTO noticeDTO);

    Notice findById(int idx);

}
