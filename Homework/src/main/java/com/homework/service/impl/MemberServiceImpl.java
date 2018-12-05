package com.homework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.mapper.MemberMapper;
import com.homework.model.Member;
import com.homework.service.MemberService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service("MemberService")
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class MemberServiceImpl implements MemberService {
	
	@NonNull
	private final MemberMapper memberMapper;
	
	@Override
	public List<Member> selectMemberList() {
		return memberMapper.selectMemberList();
	}

	@Override
	public void addMember(Member member) {
		memberMapper.addMember(member);
		return;
	}

	@Override
	public void updateMember(Member member) {
		memberMapper.updateMember(member);
		return;
	}

	@Override
	public void deleteMember(Member member) {
		memberMapper.deleteMember(member);
		return;
	}
}
