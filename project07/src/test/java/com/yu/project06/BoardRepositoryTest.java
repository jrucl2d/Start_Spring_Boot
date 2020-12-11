package com.yu.project06;

import com.yu.project06.domain.Board;
import com.yu.project06.repository.BoardRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertDummyBoardDatas(){
        IntStream.range(0, 300).forEach(i -> {
            Board board = new Board();

            board.setTitle("제목 " + i);
            board.setContent("내용 " + i + " 껄껄");
            board.setWriter("user0"+(i%10));

            boardRepository.save(board);
        });
    }

    @Test
    public void testList1(){
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<Board> result = boardRepository.findAll(boardRepository.makePredicate(null, null), pageable);

        log.info("페이지 : " + result.getPageable());

        log.info("-----------------------------");
        result.getContent().forEach(board -> log.info("" + board));
    }

    @Test
    public void testList2(){
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<Board> result = boardRepository.findAll(boardRepository.makePredicate("t", "10"), pageable); // title에 10이 들어가는 게시글만

        log.info("페이지 : " + result.getPageable());

        log.info("-----------------------------");
        result.getContent().forEach(board -> log.info("" + board));
    }

}
