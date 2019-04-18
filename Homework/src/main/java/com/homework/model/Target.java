package com.homework.model;

import lombok.Data;

@Data
public class Target {
	private String targetURL;
	private String targetTime;
	private Long   targetMillis;
	private Long   nowMillis;
	private boolean completeFlag;
}
