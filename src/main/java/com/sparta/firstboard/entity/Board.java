package com.sparta.firstboard.entity;

import com.sparta.firstboard.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "board") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    public Board(BoardRequestDto requestDto , String username) {
        this.username = username;
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }

    public void update(BoardRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }
}
