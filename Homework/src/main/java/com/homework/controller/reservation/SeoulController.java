package com.homework.controller.reservation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.homework.model.Seoul;
import com.homework.model.Target;

import thread.AutogetThread;

@Controller
public class SeoulController {
	
	@RequestMapping("/reservation/seoul")
	public String reservationSeoul() {
		return "/reservation/seoul";
	}

	@ResponseBody
	@RequestMapping("/reservation/seoul/get")
	public Object getTime(Model model, Seoul seoul) {
		System.out.println("id : " + seoul.getId());
		System.out.println("password : " + seoul.getPassword());
		
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> param = new HashMap<>();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //로그인
        response = restTemplate.exchange("https://www.seoul.go.kr/member/userlogin/login.do", HttpMethod.POST, entity, String.class);
        long nowMillis = response.getHeaders().getDate();
        
        long sysMillis = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        
		// 결과 리턴
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("serverTime", response.getHeaders().getDate());
		

		return resultMap;
	}

}
