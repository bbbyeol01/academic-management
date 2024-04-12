package com.ezen.management.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{

    @Id
    private String id;

    @Column(nullable = false)
    private String pwd;

    @Column(length = 30, nullable = false)
    private String name;
//    private int access;




    private String uuid;

    @Builder.Default
    private String fileName = "default_profile.jpg";

    public void changeProfile(String uuid, String fileName){
        this.uuid = uuid;
        this.fileName = fileName;
    }


    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberState memberState = MemberState.WORKING;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void changePassword(String pwd){
        this.pwd = pwd;
    }

    public void clearRoles(){
        this.roleSet.clear();
    }

    public void changeName(String name){
        this.name = name;
    }

    public void quit(){
        this.memberState = MemberState.QUIT;
    }


}