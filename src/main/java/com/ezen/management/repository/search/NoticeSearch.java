package com.ezen.management.repository.search;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.MemberRole;
import com.ezen.management.domain.Notice;
import com.ezen.management.domain.NoticeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Set;

public interface NoticeSearch {
    Page<Notice> searchNotice(String[] types, String keyword, Pageable pageable, NoticeCategory category);
}
