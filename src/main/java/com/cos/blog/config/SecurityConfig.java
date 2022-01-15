package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//import com.cos.blog.config.auth.PrincipalDetailService;


//빈등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //IoC 관리
@EnableWebSecurity  //시큐리티 필터가 등록된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC가 된다.  
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); // 이 객체를 통해서 비밀번호를 암호화한다(해쉬변경).
	}
	
	//시큐리티가 대신 로그인을 해주는데 password를 가로채기 하는데 
	//해당 password가 뭘로 해쉬가 되서 회원가입이 되었는지 알아야 
	//같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트 시 걸어두는 것이 좋다)
			.authorizeRequests() //요청이 들어오면 
				.antMatchers("/", "/auth/**", "/js/**"," /css/**", "/image/**", "/dummy/**") //여기는 
				.permitAll()                           //누구나 들어올 수 있다.
				.anyRequest()                     // 다른 것들은
				.authenticated()              //인증이필요하다(인증된 사람만)
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 오는 로그인 요청을 가로채서 대신 로그인을 수행해준다.
				.defaultSuccessUrl("/"); //로그인이 정상 완료가 되면 이 페이지로 가진다.				
	}
	
}
