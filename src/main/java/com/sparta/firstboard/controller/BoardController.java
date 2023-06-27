package com.sparta.firstboard.controller;

import com.sparta.firstboard.dto.BoardRequestDto;
import com.sparta.firstboard.dto.BoardResponseDto;
import com.sparta.firstboard.entity.Board;
import com.sparta.firstboard.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //게시글 게시
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    //전체 게시글 조회
    @GetMapping("/board")
    public List<BoardResponseDto> getMemos() {
        return boardService.getBoards();
    }

    //선택한 게시글 조회
    @GetMapping("/board/{id}")
    public Optional<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PutMapping("/board/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/board/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }


}
