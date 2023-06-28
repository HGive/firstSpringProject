package com.sparta.firstboard.service;

import com.sparta.firstboard.dto.BoardRequestDto;
import com.sparta.firstboard.dto.BoardResponseDto;
import com.sparta.firstboard.entity.Board;
import com.sparta.firstboard.repository.BoardRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(ApplicationContext context) {
        BoardRepository boardRepository = context.getBean(BoardRepository.class);
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard);
    }

    public List<BoardResponseDto> getBoards() {
        // DB 조회
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        Board board = findBoard(id);
        if(checkPassword(board.getPassword(), requestDto.getPassword())){
            board.update(requestDto);
        }else{
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            System.out.println("비밀번호가 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }

        return new BoardResponseDto(board);
    }

    public String deleteBoard(Long id , BoardRequestDto requestDto) {
        Board board = findBoard(id);
        if(board.getPassword()==requestDto.getPassword()){
            boardRepository.delete(board);
        }else{
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            System.out.println("비밀번호가 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
        return "Success";
    }


    private boolean checkPassword(int a, int b){
        return a==b;
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.")
        );
    }
}
