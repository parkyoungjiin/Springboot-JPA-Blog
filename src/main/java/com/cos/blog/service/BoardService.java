package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;


//스프링이 컴포넌트 스캔을 통해 BEAN에 등록해준다.(IOC를 수행함.)
@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	// 글 전체 목록
	public Page<Board> 글목록(Pageable pageable) {
//		Page<Board> pagingBoards = boardRepository.findAll(pageable);
//		Page<User> pagingUsers = userRepository.findAll(pageable);

		return boardRepository.findAll(pageable);
	}
}
