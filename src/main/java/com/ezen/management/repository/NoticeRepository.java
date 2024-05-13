package com.ezen.management.repository;

import com.ezen.management.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Query("select n from Notice n left join n.category nc where n.idx = :idx")
    Notice findByIdWithCategory(int idx);

}
