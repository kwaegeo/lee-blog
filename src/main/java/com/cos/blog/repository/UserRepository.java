package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO와 같은 기능
//자동으로 빈으로 등록된다
//@Repository 이 어노테이션이 생략이 된다.
public interface UserRepository extends JpaRepository<User, Integer>{
		
}
