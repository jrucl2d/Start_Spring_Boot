package com.yu.project03.repository;

import com.yu.project03.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

    public List<Board> findBoardByTitle(String title);

    public List<Board> findByWriter (String writer);

    public Collection<Board> findByWriterContaining(String writer);

    public List<Board> findByTitleContainingOrContentContaining(String title, String content);

    public List<Board> findByTitleContainingAndBnoGreaterThan(String title, Long num);

    public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable pageable);

    public List<Board> findByBnoGreaterThan(Long bno, Pageable pageable); // pageable의 정렬 기능을 사용

    public Page<Board> findByBnoLessThan(Long bno, Pageable pageable);
}
