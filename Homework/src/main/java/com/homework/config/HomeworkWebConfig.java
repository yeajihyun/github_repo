package com.homework.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class HomeworkWebConfig {
	
	@Configuration
	static class WebMvcConfig extends WebMvcConfigurerAdapter {
		
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// static resource context
			/*
			registry.addResourceHandler("/resources/css/**").addResourceLocations("classpath:css/")
					.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).resourceChain(true)
					.addResolver(new PathResourceResolver())
					.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
					.addTransformer(new CssLinkResourceTransformer());
			// .addResolver(new GzipResourceResolver()).addTransformer(new
			// CssLinkResourceTransformer());
			*/
			registry.addResourceHandler("/resources/css/**").addResourceLocations("classpath:css/")
					.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).resourceChain(true)
					.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
					.addResolver(new GzipResourceResolver()).addResolver(new PathResourceResolver());
			registry.addResourceHandler("/resources/js/**").addResourceLocations("classpath:js/")
					.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).resourceChain(true)
					.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
					// .addResolver(new
					// VersionResourceResolver().addContentVersionStrategy("/**"))
					.addResolver(new GzipResourceResolver()).addResolver(new PathResourceResolver());
			registry.addResourceHandler("/resources/images/**").addResourceLocations("classpath:images/")
					.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)).resourceChain(true)
					.addResolver(new PathResourceResolver());
		}
		

	}

}
