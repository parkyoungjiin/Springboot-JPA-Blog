package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용.

import com.cos.blog.service.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

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
	
	@GetMapping("/user/testVersion")
	public String test() {
		return "versionTest";
	}
}
