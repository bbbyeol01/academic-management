package com.ezen.management.service;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.MemberRole;
import com.ezen.management.dto.MemberDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberService {

    void adminInsert(MemberDTO memberDTO);

    void teacherInsert(MemberDTO memberDTO);

//    List<Member> findAll(Pageable pageable);

    PageResponseDTO<Member> findAll(PageRequestDTO pageRequestDTO);

    PageResponseDTO<Member> findBySpecificRoles(Set<MemberRole> memberRoleSet, PageRequestDTO pageRequestDTO);


    void delete(String id) throws Exception;

    void modify(MemberDTO memberDTO) throws IOException;

    Optional<Member> findById(String id);

    int countAll();



}
