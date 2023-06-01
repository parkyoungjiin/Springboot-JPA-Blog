package com.cos.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private PrincipalDetailService principalDetailService;

    // 해시 암호화 
	@Bean // IoC가 되요!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인을 할 때 password를 가로채서 한다.
	// 이때, 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화하여 DB에 있는 해쉬랑 비교할 수 있다. (최신버전은 알아서 해줌. 밑의 코드를 안써도 됨.)
//	protected void config(AuthenticationManagerBuilder auth) throws Exception {
//		System.out.println("해시작업");
//		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
//		
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	// 인증없이 접근할 수 있는 리스트를 배열로 저장함. (화이트리스트)
    private static final String[] AUTH_WHITELIST = {
            "/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**"
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
                .formLogin(login -> login
                		.loginPage("/auth/loginForm")
                		.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당주소로 요청 오는 로그인을 가로챈다.
                		.defaultSuccessUrl("/") // 정상적으로 요청이 완료될 때 이동 주소.
        		)
                .build(); // 메서드 호출을 통해 시큐리티 구성을 완료하고 보안 필터 체인을 생성할 수 있다.
    }
    
    
}