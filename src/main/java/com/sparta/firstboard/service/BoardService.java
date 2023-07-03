package com.sparta.firstboard.service;

import com.sparta.firstboard.dto.BoardRequestDto;
import com.sparta.firstboard.dto.BoardResponseDto;
import com.sparta.firstboard.entity.Board;
import com.sparta.firstboard.jwt.JwtUtil;
import com.sparta.firstboard.repository.BoardRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final  HttpServletRequest request;

    private final JwtUtil jwtUtil;

    public BoardService(BoardRepository boardRepository, HttpServletRequest request, JwtUtil jwtUtil) {
//        BoardRepository boardRepository = context.getBean(BoardRepository.class);
        this.boardRepository = boardRepository;
        this.request = request;
        this.jwtUtil = jwtUtil;
    }


    public BoardResponseDto createBoard(BoardRequestDto requestDto , String tokenValue) {
        if(checkValidToken(request)){
            Board board = new Board(requestDto ,getUsernameFromtoken(tokenValue));
            Board saveBoard = boardRepository.save(board);
            return new BoardResponseDto(saveBoard);
        }else{
            System.out.println("유효하지 않은 토큰");
            return null;
        }

    }

    public List<BoardResponseDto> getBoards() {
        // DB 조회
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, String tokenValue) {
        Board board = findBoard(id);
        if(checkValidToken(request)&&board.getUsername().equals(getUsernameFromtoken(tokenValue))){  //checkPassword(board.getPassword(), requestDto.getPassword())
            board.update(requestDto);
        }else{
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            System.out.println("비밀번호가 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰.");
        }

        return new BoardResponseDto(board);
    }

    public String deleteBoard(Long id , String tokenValue) {
        Board board = findBoard(id);
        if(checkValidToken(request)&&board.getUsername().equals(getUsernameFromtoken(tokenValue))){ //board.getPassword()==requestDto.getPassword()
            boardRepository.delete(board);
        }else{
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            System.out.println("비밀번호가 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰");
        }
        return "Success";
    }


    private boolean checkPassword(int a, int b){
        return a==b;
    }

    private boolean checkValidToken(HttpServletRequest request){
        String tokenValue = jwtUtil.getTokenFromRequest(request);
        String token = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }
        return true;
    }

    private String getUsernameFromtoken(String tokenValue){
        String token = jwtUtil.substringToken(tokenValue);
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        return info.getSubject();
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.")
        );
    }
}
