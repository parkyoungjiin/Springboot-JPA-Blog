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
import com.oracle.wls.shaded.org.apache.regexp.recompile;


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
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
//		Page<Board> pagingBoards = boardRepository.findAll(pageable);
//		Page<User> pagingUsers = userRepository.findAll(pageable);

		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다 (id : " + id + ")");
				});
	}
	@Transactional
	public void 삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		// 영속화(수정 작업을 위해서) -> find() 함수를 통해 DB에 저장된 상태가 아니라 영속성 컨텍스트에서 관리되고 있다.
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 게시글을 찾을 수 없습니다 (id : " + id + ")");
				});
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 함수 종료 시 (Service가 종료될 떄 ) 트랜잭션이 종료된다. 이 때 더티체킹 - 자동 업데이트가 된다. (DB Flush)
	}
}
