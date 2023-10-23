package com.whiteboard.whiteboard;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.whiteboard.whiteboard") // 스캔 대상 패키지 설정
public class WhiteboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteboardApplication.class, args);

		
	}

	@Bean
	public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

}
