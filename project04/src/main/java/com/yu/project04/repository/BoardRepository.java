package com.yu.project04.repository;

import com.yu.project04.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

    public List<Board> findByBnoGreaterThan(Long bno, Pageable pageable);

    @Query("SELECT b.bno, b.title, count(r) " +
            "FROM Board b LEFT OUTER JOIN b.replies r " +
            "WHERE b.bno > 0 GROUP BY b")
    public List<Object[]> getPage(Pageable pageable);

}
