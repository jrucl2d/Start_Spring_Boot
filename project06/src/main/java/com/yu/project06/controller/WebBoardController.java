package com.yu.project06.controller;

import com.yu.project06.domain.Board;
import com.yu.project06.repository.BoardRepository;
import com.yu.project06.vo.PageMaker;
import com.yu.project06.vo.PageVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list") // 뒤에 ?page=11&size=11 를 붙이면 자동으로 파라미터 수집됨
    public void list(PageVO pageVO, Model model){ // PageableDefault 사용시 안 좋으므로 VO를 두고 사용
        Pageable pageable = pageVO.makePageable(0, "bno"); // 0이면 DESC

        Page<Board> result = boardRepository.findAll(boardRepository.makePredicate(null, null), pageable);

        log.info("" + pageable);
        log.info("" + result);

        model.addAttribute("result", new PageMaker(result));
    }
}
