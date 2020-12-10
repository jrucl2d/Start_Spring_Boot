package com.yu.project06;

import com.yu.project06.domain.Board;
import com.yu.project06.repository.BoardRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}
