package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
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
	

	
	//회원가입 작업
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user){
		System.out.println("UserApiController : save 호출됨.");
		
		int result = userService.회원가입(user);//실제로 DB에 insert하고, 아래 return 하면 된다.
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
