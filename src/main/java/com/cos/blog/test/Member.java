package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
//getter, setter 모두 하고 싶으면 Data로 사용.
@Data
//파라미터 생성자 어노테이션
// @AllArgsConstructor
// 기본 생성자(빈 생성자)
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	// 1. 객체에 값을 넣을 때 생성자 순서를 지키지 않아도 된다.
    // 2. 객체 값 순서를 헷갈려서 객체의 값을 잘못 넣는 실수하는 것을 방지한다.

	
	
	
	
}
