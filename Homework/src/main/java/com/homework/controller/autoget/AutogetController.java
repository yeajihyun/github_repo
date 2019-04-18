package com.homework.controller.autoget;

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

import com.homework.model.Target;

import thread.AutogetThread;

@Controller
public class AutogetController {
	
	@RequestMapping("/autoget/autoget")
	public String coin() {
		return "/autoget/autoget";
	}

	@ResponseBody
	@RequestMapping("/autoget/getTime")
	public Object getTime(Model model, Target target) {
		System.out.println("targetURL : " + target.getTargetURL());
		System.out.println("targetTime : " + target.getTargetTime());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime targetLDT = LocalDateTime.parse(target.getTargetTime(), formatter);
		ZonedDateTime targetZDT = targetLDT.atZone(ZoneId.of("Asia/Seoul"));
		long targetMillis = targetZDT.toInstant().toEpochMilli();
		target.setTargetMillis(targetMillis);
		
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        response = restTemplate.exchange(target.getTargetURL(), HttpMethod.GET, entity, String.class);
        long nowMillis = response.getHeaders().getDate();
        target.setNowMillis(nowMillis);
        
        target.setCompleteFlag(false);
        
        long sysMillis = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        
		// 결과 리턴
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("serverTime", response.getHeaders().getDate());
		
		if( targetMillis > nowMillis ) {
			System.out.println("########## start ##########");
			System.out.println("milliceconds target time : " + targetMillis);
			System.out.println("milliceconds now time    : " + nowMillis);
			System.out.println("milliseconds system time : " + sysMillis);

			for(int i=1; i<=5; i++) {
				Runnable autogetRunnable = new AutogetThread(target); 
				Thread autogetThread = new Thread(autogetRunnable);
				autogetThread.setName("autogetThread " + i);
				autogetThread.setDaemon(true);
				autogetThread.start();
			}
			
			while(true) {
				if( target.getNowMillis() >= target.getTargetMillis() ) {
					System.out.println("########## Main Complete ##########");
					System.out.println("milliceconds target time : " + target.getTargetMillis());
					System.out.println("milliceconds now time    : " + target.getNowMillis());
					System.out.println("milliseconds system time : " + LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli());
					break;
				}
			}
		}

		return resultMap;
	}

}
