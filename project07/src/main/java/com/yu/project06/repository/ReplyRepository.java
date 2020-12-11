package com.yu.project06.repository;

import com.yu.project06.domain.Board;
import com.yu.project06.domain.Reply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReplyRepository extends CrudRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r WHERE r.board = ?1 AND r.rno > 0 ORDER BY r.rno ASC")
    public List<Reply> getRepliesOfBoard(Board board);
}
