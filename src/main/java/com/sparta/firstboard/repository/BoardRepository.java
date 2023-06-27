package com.sparta.firstboard.repository;

import com.sparta.firstboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findById(Long id);

}
