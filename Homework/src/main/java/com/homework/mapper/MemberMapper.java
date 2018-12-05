package com.homework.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homework.model.Member;

@Mapper
public interface MemberMapper {

	public List<Member> selectMemberList();
	public void addMember(Member member);
	public void updateMember(Member member);
	public void deleteMember(Member member);

}
