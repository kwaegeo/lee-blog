package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional(readOnly = true) //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)을 유지시킬 수 있음.
	public User 로그인(User user) {
		
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
}
