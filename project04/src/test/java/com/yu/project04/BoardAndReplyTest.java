package com.yu.project04;

import com.yu.project04.domain.Board;
import com.yu.project04.domain.Reply;
import com.yu.project04.repository.BoardRepository;
import com.yu.project04.repository.ReplyRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class BoardAndReplyTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertDummy(){
        IntStream.range(1, 200).forEach(i -> {
            Board board = new Board();
            board.setTitle("보드 " + i);
            board.setContent("내용 " + i);
            board.setWriter("user" + i%10);
            boardRepository.save(board);
        });
    }

    @Transactional // cascade 설정도 해줘야 함. 양방향으로 처리하려면 복잡함
    @Test
    public void insertReplyTest(){
        boardRepository.findById(198L).ifPresent(board -> {
            List<Reply> replies = board.getReplies();
            Reply reply = new Reply();
            reply.setReply("껄껄껄");
            reply.setReplyer("껄쟁이00");
            reply.setBoard(board);
            replies.add(reply);
            board.setReplies(replies);

            boardRepository.save(board);
        });
    }

    @Test // 단방향 방식으로 처리. 더 간단함
    public void insertReplyTest2(){
        Board board = new Board();
        board.setBno(199L);

        Reply reply = new Reply();
        reply.setReply("껄껄껄");
        reply.setReplyer("껄껄맨00");
        reply.setBoard(board);

        replyRepository.save(reply);
    }

    @Test // 단순 게시글만 불러오기
    public void testList1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION.DESC, "bno");

        boardRepository.findByBnoGreaterThan(0L, pageable).forEach(board -> {
            log.info(board.getBno()  + ":" + board.getTitle());
        });
    }

    @Test
    @Transactional // OneToMany를 fetch=FetchType.EAGER로 하든지 Transactional을 주면 되긴 한다. 그러나 비효율적
    public void testList2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION.DESC, "bno");
        boardRepository.findByBnoGreaterThan(0L, pageable).forEach(board -> {
            log.info(board.getBno()  + ":" + board.getTitle() + "," + board.getReplies().size());
        });
    }

    @Test // @Query를 사용한 조인
    public void testList3(){
        Pageable pageable = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION.DESC, "bno");

        boardRepository.getPage(pageable).forEach(board -> {
            log.info(Arrays.toString(board));
        });
    }
}
