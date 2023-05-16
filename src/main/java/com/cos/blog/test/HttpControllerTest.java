package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
// @RestController
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest:";
	
	//롬복 테스트
	@GetMapping("/http/lombok")
	public String lomboktest() {
		Member m = new Member(1,"ssar","1234","email");
		System.out.println(TAG+"getter : " + m.getId());
		m.setId(100);
		System.out.println(TAG+"setter : " + m.getId());
		
		return "lombok test 완료";
	}
	
	
	
	@GetMapping("/http/get")
	public String getTest() {
		return "get요청";
	}
	@PostMapping("/http/post")
	public String postTest() {
		return "post요청";
	}
	@PutMapping("/http/put")
	public String putTest() {
		return "put요청";
	}
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
