package com.homework.service;

import java.util.List;

import com.homework.model.Member;

public interface MemberService {
	
	public List<Member> selectMemberList();
	public void addMember(Member member);
	public void updateMember(Member member);
	public void deleteMember(Member member);

}
