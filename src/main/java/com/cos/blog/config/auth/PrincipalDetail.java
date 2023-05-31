package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

// PrincipalDetail : 유저 정보를 담는 클래스(+ DB에 있는 값을 저장함.)

// 1. UserDetails 를 implements 
// 2. **userDetails로만 저장할 수 있기에**, user를 compostion 한다.
// 3. override를 통해 메서드를 구현함. 
// - username, password, 계정만료여부, 계정잠김여부, 비밀번호 만료여부, 계정활성화 여부, 계정권한 여부
@Getter
public class PrincipalDetail implements UserDetails {
	private User user; // 콤포지션 (클래스를 품고 있음.)
	
	//생성자
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다.(true: 만료 안됨.)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다.(true: 잠기지 않음.)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 비밀번호 만료되지 않았는지 리턴한다.(true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정이 활성화(사용가능)인지 리턴한다.(true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 해당 유저의 권한을 return
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//ArrayList가 collection을 상속 받음.
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new GrantedAuthority() {
			
			//추상메서드 오버라이딩
			@Override
			public String getAuthority() {
				return "ROLE_" + user.getRole(); // ROLE은 필수로 작성. (ROLE_XXX 형태로 작성되야 시큐리티에서 인식함.) 
			}
		});
		return collectors;
	}
	
}
