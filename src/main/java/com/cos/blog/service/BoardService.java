package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import com.oracle.wls.shaded.org.apache.regexp.recompile;

import jakarta.persistence.EntityManager;


//스프링이 컴포넌트 스캔을 통해 BEAN에 등록해준다.(IOC를 수행함.)
@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EntityManager em;
	
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
	
	// 댓글 작성
	// 파라미터 : reply(content, userId, boardId)
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		//natvie query 적용
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("댓글쓰기 : " + result);
		// --------native query 작성 전---------
//		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 : 회원 id 찾을 수 없습니다 (id : " + replySaveRequestDto.getUserId() + ")");
//		}); //영속화 완료
//		
//		//boardId 만 존재하기에, repository의 findById 메서드를 통해 Board 객체를 생성하여 reply에 board 형태로 저장.
//		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id 찾을 수 없습니다 (id : " + replySaveRequestDto.getBoardId() + ")");
//		}); //영속화 완료
//		
//		Reply reply = Reply.builder()
//				.user(user)
//				.board(board)
//				.content(replySaveRequestDto.getContent())
//				.build();
//		
//		
//		replyRepository.save(reply);
	}
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
		
	}
	@Transactional
	public void 즉시로딩() {
		Board board = new Board();
		board.setTitle("testTitle");
		board.setContent("test");
		board.setCount(1);
		
		em.persist(board);
		
		User user = new User();
		user.setUsername("testMan");
		user.setEmail("test@test.com");
		user.setPassword("test1234");
		user.setOauth("kakao");
		user.setRole(RoleType.USER);
		em.persist(user);
		
		em.flush();
		em.clear();
		
		Board findBoard = em.find(Board.class, board.getTitle());
		
	}
}
