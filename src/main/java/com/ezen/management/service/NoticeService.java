package com.ezen.management.service;

import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;

import java.util.List;

public interface NoticeService {

    PageResponseDTO<NoticeDTO> findAll(PageRequestDTO pageRequestDTO, NoticeCategory category);

    int insert(NoticeDTO noticeDTO);

    NoticeDTO findById(int idx);

    List<NoticeDTO> getIndexList();

    int update(NoticeDTO noticeDTO);

    int delete(int idx);

}
