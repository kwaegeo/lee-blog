package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserapiContoller: save호출됨 ");
		userService.회원가입(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
			 
		//자바 오브젝트를 자동으로 JSON으로 변환하여 return 해준다.
		//스프링의 메세지컨버터의 Jackson
	}

}
 