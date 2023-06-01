package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
	
	@Transactional(readOnly = true)
	public User 유저정보조회(int id) {
		return userRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("유저정보를 찾을 수 없습니다. id :" + id);
				});
	}
//	-- 시큐리티 사용 전 방식임 -- 
//	@Transactional(readOnly = true) //select시 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료까지 정합성을 유지해준다. = 같은 데이터가 조회될 수 있도록 한다.
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
//	
	@Transactional
	public void 회원수정(User user) {
		// 영속화 시킨 후 영속화된 User 오브젝트를 수정해야 함.
		// 영속화된 오브젝트가 변경되면 자동으로 DB에 update문을 전송한다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		
		String rawPassword = user.getPassword();
		// 암호화
		String encPassword = encoder.encode(rawPassword);
		// 암호화 된 값으로 변경.
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		
		
		
	}
}
