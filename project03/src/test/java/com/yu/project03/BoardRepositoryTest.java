package com.yu.project03;

import com.yu.project03.domain.Board;
import com.yu.project03.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        Pageable pageable = PageRequest.of(0, 10);
        Collection<Board> results = boardRepository.findByBnoGreaterThanOrderByBnoDesc(0L, pageable);

        results.forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike5() {
        // Pageable 자체에서 sort 구현
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
        Collection<Board> results = boardRepository.findByBnoGreaterThan(0L, pageable);

        results.forEach(board -> System.out.println(board));
    }

    @Test
    public void testByLike6() {
        // Pageable 자체에서 sort 구현
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
        Page<Board> result = boardRepository.findByBnoLessThan(50L, pageable); // 처음엔 데이터 가져오는 select, 다음엔 count 가져오는 select 두 번 실행

        System.out.println("페이지 사이즈 : " + result.getSize());
        System.out.println("총 페이지 수 : " + result.getTotalPages());
        System.out.println("토탈 카운트 : " + result.getTotalElements());
        System.out.println("Next는 : " + result.nextPageable());

        List<Board> theBoards = result.getContent();

        theBoards.forEach(board -> System.out.println(board));
    }

    // @Query를 사용
    @Test
    public void testByTitleQuery(){
        boardRepository.findByTitle("17").forEach(board -> System.out.println(board));
    }
    
    @Test
    public void testByTitleQuery2(){
        boardRepository.findByTitle2("17").forEach(arr -> System.out.println(Arrays.toString(arr))); // Object[]형으로 리턴됨
    }

    @Test
    public void testByPage( ){
        Pageable pageable = PageRequest.of(0, 10);
        boardRepository.findByPage(pageable).forEach(board -> System.out.println(board));
    }
}