package com.homework.controller.coin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoinController {
	
	@RequestMapping("/coin/coin")
	public String coin() {
		return "/coin/coin";
	}

}
