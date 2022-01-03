package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //페이지로 이동하는게 아니라 응답만해주도록 
public class DummyControllerTest {

	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email의 데이터를 가지고 요청하게 되면
	// 그러면 join메서드에 쏙쏙 들어간다. 그대신 매개변수의 명과 키값이 정확해야한다.
	//객체로 받으려면 객체의 멤버변수가 없는 것은 자동으로 null값이 들어가야하고
	//요청한 데이터 속에 멤버변수와 변수명이 다르면? 안들어간다.
	
	@PostMapping("/dummy/join11")
	public String join(User user) { 
		
		
		System.out.println("username :"+user.getUsername());
		System.out.println("userid :"+user.getId());
		System.out.println("role :"+user.getRole());
		System.out.println("password :"+user.getPassword());
		System.out.println("email :"+ user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원 가입이 완료되었습니다";
	
	}
	
}
