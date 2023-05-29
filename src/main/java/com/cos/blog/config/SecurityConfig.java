package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 해시 암호화 
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		//BCryptPasswordEncoder 객체를 리턴함.
		return new BCryptPasswordEncoder();
	}
	
	// 인증없이 접근할 수 있는 리스트를 배열로 저장함. (화이트리스트)
    private static final String[] AUTH_WHITELIST = {
            "/", "/auth/**", "/js/**", "/css/**", "/image/**"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
        		.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .shouldFilterAllDispatcherTypes(false) // 디스패처 필터링 여부 결정(false이기에 필터링 X)
                        .requestMatchers(AUTH_WHITELIST) //요청에 대한 매처를 지정함. 
                        .permitAll()//matchers에 지정한 리소스에 접근을 인증절차 없이 허용한다.
                        .anyRequest() //나머지 요청들은 
                        .authenticated()) // 인증을 완료해야 접근이 가능하다. 
                .build(); // 메서드 호출을 통해 시큐리티 구성을 완료하고 보안 필터 체인을 생성할 수 있다.
    }
}