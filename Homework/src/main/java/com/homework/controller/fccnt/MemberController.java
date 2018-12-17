package com.homework.controller.fccnt;

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
	
	@RequestMapping("/fccnt/member")
	public String member(final Model model) {

		model.addAttribute("memberList", memberService.selectMemberList());
		
		return "/fccnt/member";
	}

	@PostMapping("/fccnt/memberAdd")
	@ResponseBody
	public boolean memberAdd(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.addMember(member);
		
		return true;
	}

	@PostMapping("/fccnt/memberUpdate")
	@ResponseBody
	public boolean memberUpdate(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.updateMember(member);
		
		return true;
	}

	@PostMapping("/fccnt/memberDelete")
	@ResponseBody
	public boolean memberDelete(@RequestBody final Member member) {

		System.out.println("###################### Tostring : " + member.toString() );

		memberService.deleteMember(member);
		
		return true;
	}
}
