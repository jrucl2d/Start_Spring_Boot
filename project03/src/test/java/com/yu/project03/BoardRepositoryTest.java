package com.yu.project03;

import com.yu.project03.domain.Board;
import com.yu.project03.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void dummyInsert() {
        for (int i = 1; i <= 200; i++) {
            Board board = new Board();
            board.setTitle("더미" + i);
            board.setContent("더미내용" + i);
            board.setWriter("사람0" + (i % 10));
            boardRepository.save(board);
        }
    }

    // 쿼리 메소드를 활용
    @Test
    public void testByTitle() {
        boardRepository.findBoardByTitle("더미20").forEach(board -> System.out.println(board));
    }

    @Test
    public void testByWriter() {
        boardRepository.findByWriter("사람00").forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike() {
        boardRepository.findByWriterContaining("사람1").forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike2() {
        boardRepository.findByTitleContainingOrContentContaining("더미12", "더미내용1").forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike3() {
        boardRepository.findByTitleContainingAndBnoGreaterThan("더미12", 50L).forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike4() {
        boardRepository.findByBnoGreaterThanOrderByBnoDesc(150L).forEach(board -> System.out.println(board));
    }
}