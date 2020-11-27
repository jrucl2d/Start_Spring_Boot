package com.yu.project02;

import com.yu.project02.domain.Board;
import com.yu.project02.repository.BoardRepository;
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
    public void testInsert(){
        Board board = new Board();
        board.setContent("껄껄내용");
        board.setTitle("껄껄제목");
        board.setWriter("껄껄보이");
        boardRepository.save(board);
    }

    @Test
    public void testRead(){
        boardRepository.findById(1L).ifPresent((board) -> {
            System.out.println(board);
        });
    }

    @Test
    public void testUpdate(){
        boardRepository.findById(1L).ifPresent(board -> { // 최초 select 1회 수행
            board.setContent("깔깔내용");
            board.setTitle("꼴꼴제목");
            boardRepository.save(board); // 테스트 코드는 매번 새롭게 실행되므로 메모리에 관리되는 엔티티 객체가 없으므로 select 후에 update 함
        });
    }
    @Test
    public void testDelete(){
        boardRepository.deleteById(1L); // 메모리에 엔티티 객체가 보관되고 있지 않으므로 select 후에 delete
    }
}
