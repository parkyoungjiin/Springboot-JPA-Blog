package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


//스프링이 컴포넌트 스캔을 통해 BEAN에 등록해준다.(IOC를 수행함.)
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int 회원가입(User user) {
		try {
			//role을 eunm을 통해 값 지정.
			user.setRole(RoleType.USER);
			// 패스워드 암호화
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입() :" + e.getMessage());
		}
		return -1;
	}
//	-- 시큐리티 사용 전 방식임 -- 
//	@Transactional(readOnly = true) //select시 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료까지 정합성을 유지해준다. = 같은 데이터가 조회될 수 있도록 한다.
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
//	
}
