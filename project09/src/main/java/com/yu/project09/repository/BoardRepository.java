package com.yu.project09.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.yu.project09.domain.Board;
import com.yu.project09.domain.QBoard;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
    public default Predicate makePredicate(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QBoard board = QBoard.board;
        
        // where bno > 0의 조건이 생성됨
        builder.and(board.bno.gt(0));

        if(type == null){
            return builder; // type을 명시 안 하면 bno > 0의 조건만 적용
        }

        // type에 따라서 키워드 like 조건을 추가
        switch (type){
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;
            case "c":
                builder.and(board.content.like("%" + keyword + "%"));
                break;
            case "w":
                builder.and(board.writer.like("%" + keyword + "%"));
                break;
        }
        
        return builder;
    }
}
