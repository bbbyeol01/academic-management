package com.ezen.management.repository;

import com.ezen.management.domain.Notice;
import com.ezen.management.repository.search.NoticeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeSearch {

    @Query("select n from Notice n left join n.category nc where n.idx = :idx")
    Notice findByIdWithCategory(int idx);

    @Query("select n from Notice n left join n.category nc where n.del = false order by n.idx desc limit 5")
    List<Notice> getNoticeLimit();

}
