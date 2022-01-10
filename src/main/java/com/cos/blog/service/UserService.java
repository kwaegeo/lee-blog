package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링의 컴포넌트 스캔을 통해 Bean에 등록해줌. IoC를 해준다. 메모리를 대신 띄워준다.
@Service
public class UserService {
	
	@Autowired //의존 주입
	private UserRepository userRepository;
	
	@Transactional
	public int 회원가입(User user) {

		try {
			userRepository.save(user);
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입()" +e.getMessage());
		}
		return -1;
	}
	
}
