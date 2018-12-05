package com.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.model.Member;
import com.homework.service.MemberService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class MemberController {
	
	@NonNull
	private final MemberService memberService;
	
	@RequestMapping("/member")
	public String member(final Model model) {

		model.addAttribute("memberList", memberService.selectMemberList());
		
		return "member";
	}

	@PostMapping("/member/memberAdd")
	@ResponseBody
	public boolean memberAdd(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.addMember(member);
		
		return true;
	}

	@PostMapping("/member/memberUpdate")
	@ResponseBody
	public boolean memberUpdate(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.updateMember(member);
		
		return true;
	}

	@PostMapping("/member/memberDelete")
	@ResponseBody
	public boolean memberDelete(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.deleteMember(member);
		
		return true;
	}
}
