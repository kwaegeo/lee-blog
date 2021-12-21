package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML파일)
//@Controller

//사용자가 요청하면 -> 응답(Data)
@RestController
public class httpControllerTest {
	
	//브라우저 요청은 무조건 get요청밖에 할 수없다.
	
	//http://localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get요청:" + m.getId() +","+ m.getPassword() +","+ m.getUsername() +","+ m.getEmail();	
	}

	//http://localhost:8080/http/post(insert)
	@PostMapping("/http/post") //text/plain, application/json
	public String postTest(@RequestBody Member m) { //Spring의 MessageConverter
		return "get요청:" + m.getId() +","+ m.getPassword() +","+ m.getUsername() +","+ m.getEmail();	
	
	}	
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put요청:" + m.getId() +","+ m.getPassword() +","+ m.getUsername() +","+ m.getEmail();	
	}	
	
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete요청";	
	}
	
}
