package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserapiContoller: save호출됨 ");
		
		//실제로 DB에 insert를 한 다음 아래에서 return을 해주면 된다.
		user.setRole(RoleType.USER);
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
			 
		//자바 오브젝트를 자동으로 JSON으로 변환하여 return 해준다.
		//스프링의 메세지컨버터의 Jackson
	}
	
	
//	//스프링 시큐리티 이용해서 로그인 
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user HttpSession session){
//		System.out.println("UserapiContoller: login 호출됨 ");
//		User principal = userService.로그인(user); //principal (접근주체)
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
}
 