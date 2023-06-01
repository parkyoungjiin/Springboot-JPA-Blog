package com.cos.blog.controller.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserApiController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//회원가입 작업
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user){
		System.out.println("UserApiController : save 호출됨.");
		
		int result = userService.회원가입(user);//실제로 DB에 insert하고, 아래 return 하면 된다.
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
	
	//회원정보 수정
	//RequestBody를 통해 JSON 데이터를 받을 수 있다.
	@PutMapping("/api/user/update")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//수정이 됐을 때 세션값은 변경되지 않은 상태이기에 직접 세션값을 변경해줘야 함.
		
		//세션 등록 (authentication객체를 생성함.)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}

	
	
//	--로그인 작업(기존 방식, 시큐리티 사용 전 방식임.)--
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
//		System.out.println("UserApiController : login 호출됨.");
//		User principal = userService.로그인(user); //principal : 접근 주체
//		
//		if(principal != null) {
//			//세션 저장
//			session.setAttribute("principal", principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK, 1);
//	}
	
	
}
