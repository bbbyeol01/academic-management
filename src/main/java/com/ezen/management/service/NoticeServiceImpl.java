package com.ezen.management.service;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import com.ezen.management.dto.NoticeDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.MemberRepository;
import com.ezen.management.repository.NoticeCategoryRepository;
import com.ezen.management.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeCategoryRepository noticeCategoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageResponseDTO<NoticeDTO> findAll(PageRequestDTO pageRequestDTO, NoticeCategory category) {

        Pageable pageable = pageRequestDTO.getPageable();
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Notice> all = noticeRepository.searchNotice(types, keyword, pageable, category);

        List<Notice> noticeList = all.getContent();

        LocalDateTime now = LocalDateTime.now();

        List<NoticeDTO> dtoList = new ArrayList<>();
        noticeList.forEach( notice -> {

            LocalDateTime regDate = notice.getRegDate();
            String writeDate = "";

                Duration duration = Duration.between(regDate, now);
                long minutes = duration.toMinutes();

                if (minutes < 1) {
                    writeDate = "지금 막";
                } else if (minutes < 60) {
                    writeDate = minutes + "분 전";
                } else if (minutes < 1440) {
                    writeDate =  minutes / 60 + "시간 전";
                } else if (minutes < 10080) {
                    writeDate =  minutes / 1440 + "일 전";
                } else if (minutes < 43800) {
                    writeDate =  minutes / 10080 + "주 전";
                } else if (minutes < 525600) {
                    writeDate =  minutes / 43800 + "달 전";
                } else {
                    writeDate =  minutes / 525600 + "년 전";
                }

            Member writer = memberRepository.findById(notice.getWriter()).get();

            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .idx(notice.getIdx())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .writer(notice.getWriter())
                    .writerName(writer.getName())
                    .modDate(notice.getModDate())
                    .writeDate(writeDate)
                    .categoryIdx(notice.getCategory().getIdx())
                    .categoryName(notice.getCategory().getName())
                    .admin(notice.isAdmin())
                    .hold(notice.isHold())
                    .uuid(writer.getUuid())
                    .fileName(writer.getFileName())
                    .extension(writer.getExtension())
                    .build();

            dtoList.add(noticeDTO);
        });

        return PageResponseDTO.<NoticeDTO>withAll()
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
                .hold(noticeDTO.isHold())
                .category(noticeCategoryById.get())
                .build();

        Notice save = noticeRepository.save(notice);

        return save.getIdx();
    }

    @Override
    public NoticeDTO findById(int idx) {

        Notice notice = noticeRepository.findByIdWithCategory(idx);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime regDate = notice.getRegDate();
        String writeDate = "";

        Duration duration = Duration.between(regDate, now);
        long minutes = duration.toMinutes();

        if (minutes < 1) {
            writeDate = "지금 막";
        } else if (minutes < 60) {
            writeDate = minutes + "분 전";
        } else if (minutes < 1440) {
            writeDate =  minutes / 60 + "시간 전";
        } else if (minutes < 10080) {
            writeDate =  minutes / 1440 + "일 전";
        } else if (minutes < 43800) {
            writeDate =  minutes / 10080 + "주 전";
        } else if (minutes < 525600) {
            writeDate =  minutes / 43800 + "달 전";
        } else {
            writeDate =  minutes / 525600 + "년 전";
        }

        Member writer = memberRepository.findById(notice.getWriter()).get();

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .idx(notice.getIdx())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .writerName(memberRepository.findById(notice.getWriter()).get().getName())
                .modDate(notice.getModDate())
                .writeDate(writeDate)
                .categoryIdx(notice.getCategory().getIdx())
                .categoryName(notice.getCategory().getName())
                .admin(notice.isAdmin())
                .hold(notice.isHold())
                .uuid(writer.getUuid())
                .fileName(writer.getFileName())
                .extension(writer.getExtension())
                .build();


        return noticeDTO;
    }

    @Override
    public List<NoticeDTO> getIndexList() {
        List<Notice> noticeLimit = noticeRepository.getNoticeLimit();

        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (Notice notice : noticeLimit) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime regDate = notice.getRegDate();
            String writeDate = "";

            Duration duration = Duration.between(regDate, now);
            long minutes = duration.toMinutes();

            if (minutes < 1) {
                writeDate = "지금 막";
            } else if (minutes < 60) {
                writeDate = minutes + "분 전";
            } else if (minutes < 1440) {
                writeDate =  minutes / 60 + "시간 전";
            } else if (minutes < 10080) {
                writeDate =  minutes / 1440 + "일 전";
            } else if (minutes < 43800) {
                writeDate =  minutes / 10080 + "주 전";
            } else if (minutes < 525600) {
                writeDate =  minutes / 43800 + "달 전";
            } else {
                writeDate =  minutes / 525600 + "년 전";
            }

            Member writer = memberRepository.findById(notice.getWriter()).get();

            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .idx(notice.getIdx())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .writer(notice.getWriter())
                    .writerName(memberRepository.findById(notice.getWriter()).get().getName())
                    .modDate(notice.getModDate())
                    .writeDate(writeDate)
                    .categoryIdx(notice.getCategory().getIdx())
                    .categoryName(notice.getCategory().getName())
                    .admin(notice.isAdmin())
                    .hold(notice.isHold())
                    .uuid(writer.getUuid())
                    .fileName(writer.getFileName())
                    .extension(writer.getExtension())
                    .build();

            noticeDTOList.add(noticeDTO);
        }


        return noticeDTOList;
    }

    @Override
    public int update(NoticeDTO noticeDTO) {

        Optional<Notice> byId = noticeRepository.findById(noticeDTO.getIdx());

        if(byId.isEmpty()){
            throw new NoSuchElementException("존재하지 않는 공지사항입니다.");
        }

        Notice notice = byId.get();

        Optional<NoticeCategory> byId1 = noticeCategoryRepository.findById(noticeDTO.getCategoryIdx());
        NoticeCategory noticeCategory = byId1.get();

        notice.modify(noticeCategory, noticeDTO.getTitle(), noticeDTO.getContent(), noticeDTO.isAdmin(), noticeDTO.isHold());

        Notice save = noticeRepository.save(notice);

        return save.getIdx();
    }

    @Override
    public int delete(int idx) throws NoSuchElementException {

        Optional<Notice> byId = noticeRepository.findById(idx);

        Notice notice = byId.get();
        notice.delete();

        Notice save = noticeRepository.save(notice);

        return save.getIdx();

    }
}
