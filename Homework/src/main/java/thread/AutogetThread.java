package thread;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.homework.model.Target;

public class AutogetThread implements Runnable {
	
	private Target target;

	public AutogetThread(Target target) {
		this.target = target;
	}
	
	 // Thread로 동작할 내용을 동작할 메서드
	 @Override
	 public void run(){
         ResponseEntity<String> response = null;
         RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<String> entity = new HttpEntity<String>(headers);
         long nowMillis;
        
		 // 0.5초 마다 숫자를 하나씩 증가시키고 숫자와 Thread의 이름을 출력한다
		 while(true){
			try{
				response = restTemplate.exchange(target.getTargetURL(), HttpMethod.GET, entity, String.class);
				nowMillis = response.getHeaders().getDate();
				target.setNowMillis(nowMillis);

				if( nowMillis >= target.getTargetMillis() ) {
					target.setCompleteFlag(true);
					
					System.out.println("########### " + Thread.currentThread().getName() + " complete ##########");
					System.out.println("targetMillis : " + target.getTargetMillis());
					System.out.println("nowMillis    : " + nowMillis);
					System.out.println("systemMills  : " + LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli());
					
					break;
				}

				System.out.println("########### " + Thread.currentThread().getName() + " progressing ##########");
				System.out.println("targetMillis : " + target.getTargetMillis());
				System.out.println("nowMillis    : " + nowMillis);
				System.out.println("systemMills  : " + LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli());

				Thread.sleep(500);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
	    }
	}

}
