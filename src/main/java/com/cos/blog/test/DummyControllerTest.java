package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

//html파일이 아니라, data를 리턴해주는 controller = RestController
//요청 : 웹브라우저
//웹 브라우저가 이해할 수 있는 데이터로 변환 해줘야 한다.
// 스프링부트 => MessageConverter라는 클래스가 응답시에 자동으로 작동한다.
// 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출하여 user 오브젝트를 json으로 변환하여 브라우저에게 전달.

@Transactional
@RestController
public class DummyControllerTest {
	//DummyControllerTest를 메모리에 띄울 때 Autowired를 통해 UserRepository도 같이 메모리에 띄워준다. (널값으로 원래는 띄우지 않음.)
	
	@Autowired // 의존성 주입(DI) 
	private UserRepository userRepository;

	
	// 수정(Put) : email, password만 수정.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email:" + requestUser.getEmail());
		
		//findById를 통해서 id에 해당하는 객체를 user에 저장함.
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패함.");
		});
		
		//requestUser를 통해 받은 password, email값을 user객체에 저장하여 password와 email만 update를 수행.
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
//		requestUser.setId(id); //id값을 전달했을 때 값이 존재하면 Update를 수행함.
//		requestUser.setUsername("test");
		
		
//		userRepository.save(user);
		//update 시에는 save를 잘 사용하지 않는다.
		//id가 있을 경우 null인 컬럼은 Null로 변해버린다.
		return null;
	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있음.
	// http://localhost:8000/blog/dummy/user/3
	// 전체 유저 조회 ( findAll() )
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll(); //List<Generic> Generice 타입에 명시된 객체를 리턴함. 즉, User 객체를 리턴함.
		
	}
	
	// 페이징 처리(한 페이지당 2건을 리턴)
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 1, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> users = userRepository.findAll(pageable);
		return users;
	}
	
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//findById 의 return이 optional임.(Optional로 객체를 감싸서 리턴함. 즉, User 객체가 리턴됨.)
		// 만약 id값이 없을 경 우 null이 리턴되어 오류가 발생할 수 있기에 별도로 null인지 아닌 지 판별해야 함.
		// -> 1. get() : User객체를 Optional에서 뽑아서 바로 리턴함.
		// -> 2. orElseGet() : null이 리턴될 경우 실행되는데 빈 객체를 User에 넣어준다.(Null이 리턴되지 않음.)
		// -> 3. orElseThrow(new Supplier) 
		
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			// interface(Supplier)이기에 new를 하게 되면 Override를 해줘서 객체 생성이 가능하다.
//			@Override
//			public User get() {
//				return new User();
//			}
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		return user;
	}
	
	
	//----------회원가입----------
	//requestParam을 쓰지 않고 변수명을 정확하게 적으면 변수에 적절하게 들어간다.
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		//Enum을 통해 Role을 USER 라는 고정된 String을 set해준다. (Enum을 통해 개발자의 실수를 줄일 수 있음.)
		//값을 강제하여 통일성을 부여할 수 있음.
		//데이터의 도메인(범위)를 설정할 때 Enum을 사용한다. 
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입 완료";
	}
}
