package com.yu.project06.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.yu.project06.domain.Board;
import com.yu.project06.domain.QBoard;
import com.yu.project06.domain.QReply;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Log
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomBoard {
    public CustomCrudRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Page<Object[]> getCustomPage(String type, String keyword, Pageable pageable) {
        log.info("===========================");
        log.info("TYPE : " + type);
        log.info("KEYWORD : " + keyword);
        log.info("PAGE : " + pageable);
        log.info("===========================");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);

        JPQLQuery<Tuple> tuple = query.select(board.bno, board.title, reply.count(), board.writer, board.regdate);


        tuple.leftJoin(reply);
        tuple.on(board.bno.eq(reply.board.bno));
        tuple.where(board.bno.gt(0L));

        if(type != null){
            switch (type.toLowerCase()){
                case "t":
                    tuple.where(board.title.like("%" + keyword + "%"));
                    break;
                case "c":
                    tuple.where(board.content.like("%" + keyword + "%"));
                    break;
                case "w":
                    tuple.where(board.writer.like("%" + keyword + "%"));
                    break;
            }
        }

        tuple.groupBy(board.bno);
        tuple.orderBy(board.bno.desc());
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> list = tuple.fetch();

        List<Object[]> resultList = new ArrayList<>();
        list.forEach(t -> {
            resultList.add(t.toArray());
        });

        long total = tuple.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
