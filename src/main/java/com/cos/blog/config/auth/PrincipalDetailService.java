package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	// override를 하지 않으면 시큐리티의 기본 username, password가 들어가고 자체의 username, password를 사용할 수 없다.
	//스프링이 로그인 요청을 가로챌 때, username password 변수 2개를 가로챈다.
	//password 부분은 알아서 처리가 되지만 , username은 DB에 있는 지 확인해주면 된다.
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// select를 통해서 username에 맞는 유저 정보를 principal(User객체)에 저장한다.
		// 만약 없다면(orElseThrow) 해당 사용자를 찾을 수 없다는 처리를 함.
		System.out.println("PrincipalDetailService의 loadUserByUsername메서드." + username);
		System.out.println("회원찾기");
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
				});
		System.out.println("principal 존재여부 파악: " + principal.toString() +"username :" + username);
		return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장된다.(생성자를 PrincipalDetail에 만들어주면서 User객체에 저장해준다.)
	}
	
}
