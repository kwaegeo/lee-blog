package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temphome()");
		//파일 리턴의 기본 경로: src/main/resources/static
		//리턴명: /home.html <- 이런식으로 앞에 /를 붙여야함.
		
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.png";
	}
	
	
	@GetMapping("/temp/jsp")
	public String tempjsp() {
		//prefix: /WEB-INF/views/
		//suffix: jsp
		//풀네임: /WEB-INF/views/test.jsp이기 때문에 이 파일명만 남긴다.
		return "test";
	}

}
