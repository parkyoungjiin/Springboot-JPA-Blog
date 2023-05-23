package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 예외가 발생하면 이 클래스로 들어오게 하는 어노테이션.
@RestController
public class GlobalExceptionHandler { // 예외처리 하는 클래스
	
	@ExceptionHandler(value=Exception.class) // IllegalArgumentException 발생하면 이 함수에 전달하여 getMessage()가 리턴된다.
	public String handleArgumentException(Exception e) {
		return "<h1>" + e.getMessage() + "<h1>";
	}
}
