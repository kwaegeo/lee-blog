package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //페이지로 이동하는게 아니라 응답만해주도록 
public class DummyControllerTest {

	@Autowired //의존성 주입
	private UserRepository userRepository;

	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id); //delete는 return하는게 없음(void)
		} catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다";
		}
		
		
		
		return "삭제되었습니다. id " + id;
	}
	
	
	
	
	
	
	//save함수는 id를 전달하지 않으면 insert를 해주고 
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	//email, password 2개를 받고  수정 
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 요청 -> Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아준다.)
		System.out.println("id :"+id);
		System.out.println("password :"+ requestUser.getPassword());
		System.out.println("email :" + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});  //이 과정이 영속화 하는 과정 (DB -> 영속성 컨텍스트)
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user); 
		
		//더티 체킹
		return user;
	}
	//ex) http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
		//findAll의 return 타입이 List이기 때문에 한번에 넘어간다.
	}
	
	//한 페이지당 2건에 데이터를 return 받아 볼 예정 
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort="id", direction=Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	//{id}주소로 파라미터를 전달받을 수 있다.
	//ex) http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 되고
		// 그럼 return null이 되기에 문제가 생긴다.
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return한다.
		
		//타입을 찾고 override핳고 하는 것보다 람다식이 더 편할 수 있다.
		//익명처리로 해결한다 return 타입을 몰라도된다.
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return IllegalArgumentException("해당 사용자는 없습니다.");
//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
			@Override
			public IllegalArgumentException get() {
				
				return new IllegalArgumentException("해당 유저는 없습니다. " );
			}
			
		});
		//요청 : 웹브라우저
		//User객체 = 자바오브젝트
		//변환 (웹브라우저가 이해가능한 데이터로 변환해야함 -> json
		//스프링 부트는 MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 return하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
	
	
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
