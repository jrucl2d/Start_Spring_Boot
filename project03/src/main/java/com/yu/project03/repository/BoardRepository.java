package com.yu.project03.repository;

import com.yu.project03.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    public List<Board> findBoardByTitle(String title);

    public List<Board> findByWriter (String writer);

    public Collection<Board> findByWriterContaining(String writer);

    public List<Board> findByTitleContainingOrContentContaining(String title, String content);

    public List<Board> findByTitleContainingAndBnoGreaterThan(String title, Long num);

    public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable pageable);

    public List<Board> findByBnoGreaterThan(Long bno, Pageable pageable); // pageable의 정렬 기능을 사용

    public Page<Board> findByBnoLessThan(Long bno, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
    public List<Board> findByTitle(String title);

    // 엔티티 이름을 바로 참조
    @Query("SELECT b FROM #{#entityName} b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
    public List<Board> findByContent(@Param("content") String content);

    @Query("SELECT b.bno, b.title, b.writer, b.regdate FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
    public List<Object[]> findByTitle2(String title);

    // native query -> nativeQuery를 true로 하면 됨
    @Query(value="select bno, title, writer from board where title like concat('%', ?1, '%') and bno > 0 order by bno desc", nativeQuery = true)
    public List<Object[]> findByTitleNative(String title);

    @Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
    public List<Board> findByPage(Pageable pageable);
}
