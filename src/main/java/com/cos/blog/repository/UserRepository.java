package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO와 같은 기능
//자동으로 빈으로 등록된다
//@Repository 이 어노테이션이 생략이 된다.
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//SELECT * FROM user WHERE username = 1?
	Optional<User> findByUsername(String username);

}

//JPA Naming 쿼리 전략
//Select * from user Where username =?1 AND password =?
//User findByUsernameAndPassword(String username, String password);

//@Query(value= "Select * from user Where username =? AND password", nativeQuery = true)
//User login(String username, String password);