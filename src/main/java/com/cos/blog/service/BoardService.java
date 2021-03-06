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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
//	@Autowired //의존 주입
//	private BoardRepository boardRepository;
// Autowired 의존주입 방식
//	@Autowired
//	private ReplyRepository replyRepository;
	

//	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyRepository = rRepo;
//		//생성자 의존 주입 방식
//	}

	
	@Transactional
	public void 글쓰기(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	//페이징을 해서 보내게 되면 return값이 page여야 한다!
	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable); 
	}
	
	@Transactional(readOnly=true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제하기:" + id);
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시에 트랜잭션이 Service가 종료될 때) 트랜잭션이 종료된다. 이 때 더티체킹이 일어나며 - DB쪽으로 자동 업데이트 된다. (DB flush)		
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
			int result = replyRepository.LeeSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
			
			System.out.println(result); //오브젝트를 출력하게 되면 자동으로 toString이 호출한다.
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}
