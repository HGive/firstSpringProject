package com.sparta.firstboard.controller;

import com.sparta.firstboard.dto.BoardRequestDto;
import com.sparta.firstboard.dto.BoardResponseDto;
import com.sparta.firstboard.entity.Board;
import com.sparta.firstboard.jwt.JwtUtil;
import com.sparta.firstboard.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    //게시글 게시
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto ,
                                        @RequestHeader(JwtUtil.AUTHORIZATION_HEADER) String tokenValue ) {
        return boardService.createBoard(requestDto, tokenValue);
    }

    //전체 게시글 조회
    @GetMapping("/board")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    //선택한 게시글 조회
    @GetMapping("/board/{id}")
    public Optional<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto ,
                                        @RequestHeader(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        return boardService.updateBoard(id, requestDto , tokenValue );
    }

    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable Long id,
                              @RequestHeader(JwtUtil.AUTHORIZATION_HEADER) String tokenValue,
                              HttpServletResponse res) {
        return boardService.deleteBoard(id, tokenValue ,res);
    }
}
