package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용.
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.OAuthToken;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	
	@GetMapping("/auth/loginForm")
	public String login() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	/*
	------- 과거 방식(스프링 레거시) -----
	@GetMapping("/user/updateForm/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.유저정보조회(id));
		return "user/updateForm";
	}
	*/
	
	/*
	 * 부트에서 로그인 성공 시 PrincipalDetail에 User 오브젝트를 저장한 연관관계로 이뤄져있다.
	 * 그렇기에 model로 담고 user의 정보를 담고 가기보다는 
	 * principal 변수에 있는 user를 jsp 파일에서 el을 통해 value값을 넣어주면 된다.	
	 */
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	// 카카오 로그인 성공 시 콜백 주소 
	//ResponseBody를 붙여서 Data를 리턴. (code가 리턴된다.)
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {
		//인증 후 토큰을 발급받기 위해 Body에 데이터를 담아서 전송해야 한다. (RestTemplate 라이브러리 사용.)
		RestTemplate rt = new RestTemplate();
		// 헤더 생성.
		HttpHeaders headers = new HttpHeaders();
		//key-value 형태로 담을 것이라고 헤더에 명시함. 
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		//변수에 담아서 하는 것이 좋음.
		params.add("grant_type", "authorization_code");
		params.add("client_id", "b120aff077815a1ac91f79c48a15f2ae");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//HttpHeader,HttpBody를 하나의 오브젝트에 담음.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		//Http 요청 - POST 방식 -Response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				//1. 토큰 요청 주소
				"https://kauth.kakao.com/oauth/token",
				//2. 요청 메서드
				HttpMethod.POST,
				//3. 데이터
				kakaoTokenRequest,
				//4. Response 응답 받을 형식
				String.class
		);	
		
		//ObjectMapper를 통해 json 응답 데이터를 파싱함.
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		//
		try {
			oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(oauthToken.getAccess_token());
		
		//인증 후 토큰을 발급받기 위해 Body에 데이터를 담아서 전송해야 한다. (RestTemplate 라이브러리 사용.)
				RestTemplate rt2 = new RestTemplate();
				// 헤더 생성.
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				 
				//HttpHeader,HttpBody를 하나의 오브젝트에 담음.
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
						new HttpEntity<>(headers2);
				
				//Http 요청 - POST 방식 -Response 변수의 응답 받음.
				ResponseEntity<String> response2 = rt2.exchange(
						//1. 토큰 요청 주소
						"https://kapi.kakao.com/v2/user/me	",
						//2. 요청 메서드
						HttpMethod.POST,
						//3. 데이터
						kakaoProfileRequest2,
						//4. Response 응답 받을 형식
						String.class
				);	
		return "카카오 인증 완료. 2 데이터 : " + response2.getBody();
	}
	
	@GetMapping("/user/testVersion")
	public String test() {
		return "versionTest";
	}
}
