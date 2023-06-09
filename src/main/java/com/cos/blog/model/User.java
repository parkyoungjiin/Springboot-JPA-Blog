package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// 테이블 화 시키기 위해 @Entity라는 어노테이션을 붙인다.
// boot 실행 시 User클래스를 읽어서 자동으로 MYSQL에 테이블이 생성된다.
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@DynamicInsert //insert 시 null인 데이터를 제외시켜준다. 
public class User {
//	@Id = primary Key선언
//	@GenerateValue 는 일반적으로, PRIMARY 키의 기본값을 자동으로 생성할때 사용한다.
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK값을 해당 DB의 넘버링 전략을 따라간다.(오라클 -> 시퀀스, MYSQL -> Auto Increment)
	private int id; // auto Increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 200) 
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//DB는 RoleType이라는 객체가 없다. 
	// Enum을 쓰는게 좋다.(도메인을 만들어줄 수 있기 때문임. 도메인 = 범위)
	// 회원가입 -> Admin, user, manager 등 권한 설정 시 사용 가능.
	// 여기서 String을 설정하면 managerr와 같이 통일성과 정확성 측면에서 떨어질 수 있기에 Enum으로 하는 게 좋음. 
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	private String oauth; //kakao, google
	
	@CreationTimestamp // 시간이 자동으로 입력된다.(now()를 쓰지 않아도 자동으로!)
	private Timestamp createDate; // 생성날짜(수정날짜는 제외함.)
	
	
}
