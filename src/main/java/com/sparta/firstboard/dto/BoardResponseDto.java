package com.sparta.firstboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.firstboard.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.title = board.getTitle();
        this.createdAt= board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

//    public MemoResponseDto(Long id, String username, String contents) {
//        this.id = id;
//        this.username =username;
//        this.contents = contents;
//    }
}