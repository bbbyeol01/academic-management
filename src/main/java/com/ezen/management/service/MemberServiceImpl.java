package com.ezen.management.service;

import com.ezen.management.domain.Member;
import com.ezen.management.domain.MemberRole;
import com.ezen.management.domain.MemberState;
import com.ezen.management.domain.QMember;
import com.ezen.management.dto.MemberDTO;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLDataException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Value("${com.ezen.management.upload.path}")
    private String uploadPath;


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void adminInsert(MemberDTO memberDTO) {

        Member member = Member.builder()
                .id(memberDTO.getId())
                .pwd(passwordEncoder.encode(memberDTO.getPwd()))
                .name(memberDTO.getName())
                .build();

        if(!memberDTO.getFileName().isEmpty()){
            member.changeProfile(memberDTO.getUuid(), memberDTO.getFileName());
        }

        member.addRole(MemberRole.ADMIN);

        memberRepository.save(member);

    }

    @Override
    public void teacherInsert(MemberDTO memberDTO) {

        Member member = Member.builder()
                .id(memberDTO.getId())
                .pwd(passwordEncoder.encode(memberDTO.getPwd()))
                .name(memberDTO.getName())
                .build();

        if(memberDTO.getFileName() != null){
            member.changeProfile(memberDTO.getUuid(), memberDTO.getFileName());
        }

        member.addRole(MemberRole.TEACHER);

        memberRepository.save(member);

    }

    @Override
    public PageResponseDTO<Member> findAll(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("regDate");
//        String keyword = pageRequestDTO.getKeyword();

//        List<Member> dtoList = memberRepository.findAll();
        Page<Member> all = memberRepository.getAllByWithRoles(pageable);

        List<Member> dtoList = all.getContent();

        log.info("memberServiceImpl dtoList " + dtoList);
        dtoList.forEach(member -> {
            log.info("member roleSet" + member.getRoleSet());
        });

        return PageResponseDTO.<Member>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(memberRepository.countAll())
                .build();

    }

    @Override
    public PageResponseDTO<Member> findBySpecificRoles(Set<MemberRole> memberRoleSet, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable();

        String[] types = pageRequestDTO.getTypes(); // split("_")
        String keyword = pageRequestDTO.getKeyword();

        log.info("types: " + Arrays.toString(types));
        log.info("keyword: " + keyword);

//        검색 카테고리, 키워드, 특정 권한을 가진 멤버 regDate 내림차순으로 페이징 처리해서 가져옴
        Page<Member> memberPage = memberRepository.searchMember(types, keyword, pageable, memberRoleSet);

//        페이지 안에 담긴 dtoList
        List<Member> dtoList = memberPage.getContent();

        return PageResponseDTO.<Member>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
//                검색했을 때 총 개수(페이지에 담긴 개수 말고 검색 결과 개수)
                .total((int)memberPage.getTotalElements())
                .build();
    }


    @Override
    public void delete(String id) throws Exception {
        log.info("id......" + id);

        Optional<Member> result = memberRepository.findById(id);
        Member member = result.orElseThrow();

//        교사 삭제 시 외래키가 존재하는 경우  수업, 학생에도 영향 -> 삭제하지 않고 상태만 변경
//
        try{
            memberRepository.delete(member);

//             파일 삭제
            if(member.getUuid() != null){
                Resource resource = new FileSystemResource(uploadPath + File.separator + member.getUuid() + '_' + member.getFileName());

                try{
                    resource.getFile().delete();
                }catch (Exception e){
                    throw new IOException();
                }
            }

        }catch (Exception e){
            log.error(e.getMessage());
            log.error("삭제되지 않았습니다.");
            throw new Exception();
        }finally {
            member.quit();
            memberRepository.save(member);
        }

    }

    @Override
    public void modify(MemberDTO memberDTO) throws IOException {

        Optional<Member> result = memberRepository.findById(memberDTO.getId());
        Member member = result.orElseThrow();

//        프로필 사진, 이름만 변경 가능
        member.changeName(memberDTO.getName());

//        수정 시 주의 : 기존(member)에 사진이 있고, DTO에 사진이 있다면 기존 사진을 서버에서 삭제
        if(member.getUuid() != null && memberDTO.getUuid() != null){
            Resource resource = new FileSystemResource(uploadPath + File.separator + member.getUuid() + '_' + member.getFileName());

            try{
                resource.getFile().delete();
            }catch (Exception e){
                log.info("delete exception");
                throw new IOException();
            }
        }


        if(memberDTO.getUuid() != null){
            member.changeProfile(memberDTO.getUuid(), memberDTO.getFileName());
        }

        log.info("member......" + member);
        memberRepository.save(member);

    }


    @Override
    public Optional<Member> findById(String id) {

        return memberRepository.findById(id);
    }

    @Override
    public int countAll() {
        return memberRepository.countAll();
    }





}
