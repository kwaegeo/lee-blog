package com.cos.blog.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 /이면 index.jsp 허용 
//static이하에 있는 /js/**, /css/**, /image/** 허용
//인증이 필요없으면 auth를 붙이도록 

@Controller
public class UserController {
		
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { //데이터를 리턴해주는 컨트롤러 메서드가 된다

		//여기서 POST방식으로 key = value 타입의 데이터를 요청해야한다. (카카오쪽으로)
		//Retrofit2
		
		RestTemplate rt = new RestTemplate(); 
		
		//Httpheader오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		//body의 데이터가 key = value형태의 데이터라는 것을 알려주는 것
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type" , "authorization_code");
		params.add("client_id" , "0a11c0576595d61402a54f0ddc3d8753");
		params.add("redirect_uri" , "http://localhost:8000/auth/kakao/callback");
		params.add("code" , code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기 
		//RestTemplate라는 라이브러리의 메서드가 담아야 하는 것이 HttpEntity이기 때문에 담은 것
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		//Http 요청하기 - Post방식으로 - 그리고 - response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token", //토큰 발급 요청 주소
					HttpMethod.POST, //요청 방식 
					kakaoTokenRequest, //요청 데이터  (Body, header)
					String.class // 응답 받을 타입
				);
		
		return "카카오 인증 완료: 토큰요청의 응답: " + response;
	}
	
}
