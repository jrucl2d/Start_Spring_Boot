package com.yu.project06.controller;

import com.yu.project06.domain.Board;
import com.yu.project06.domain.Reply;
import com.yu.project06.repository.ReplyRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/replies/*")
public class WebReplyController {
    @Autowired
    private ReplyRepository replyRepository;

    private List<Reply> getListsByBoard(Board board) throws RuntimeException{
        log.info("이 보드의 댓글 가져올 것 : " + board);
        return replyRepository.getRepliesOfBoard(board);
    }

    @PostMapping("/{bno}")
    public ResponseEntity<List<Reply>> addReply(@PathVariable("bno") Long bno, @RequestBody Reply reply){
        log.info("댓글 추가");
        log.info("번호 " + bno);
        log.info("댓글" + reply);

        Board board = new Board();
        board.setBno(bno);

        reply.setBoard(board);
        replyRepository.save(reply);

        return new ResponseEntity<>(getListsByBoard(board), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<Reply>> remove(@PathVariable Long bno, @PathVariable Long rno){
        log.info("삭제할 댓글 " + rno);

        replyRepository.deleteById(rno);

        Board board = new Board();
        board.setBno(bno);
        return new ResponseEntity<>(getListsByBoard(board), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{bno}")
    public ResponseEntity<List<Reply>> modify(@PathVariable("bno") Long bno, @RequestBody Reply reply){
        log.info("수정할 댓글 " + reply);

        replyRepository.findById(reply.getRno()).ifPresent(origin -> {
            origin.setReplyText(reply.getReplyText());
            replyRepository.save(origin);
        });
        Board board = new Board();
        board.setBno(bno);

        return new ResponseEntity<>(getListsByBoard(board), HttpStatus.CREATED);
    }

    @GetMapping("/{bno}")
    public ResponseEntity<List<Reply>> getReplies(@PathVariable("bno") Long bno){
        Board board = new Board();
        board.setBno(bno);
        return new ResponseEntity<>(getListsByBoard(board), HttpStatus.OK);
    }
}
