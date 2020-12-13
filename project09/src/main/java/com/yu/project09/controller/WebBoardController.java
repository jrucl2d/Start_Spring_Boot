package com.yu.project09.controller;

import com.yu.project09.domain.Board;
import com.yu.project09.repository.BoardRepository;
import com.yu.project09.repository.CustomCrudRepository;
import com.yu.project09.vo.PageMaker;
import com.yu.project09.vo.PageVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {

    @Autowired
//    private BoardRepository boardRepository;
    private CustomCrudRepository boardRepository;

    @GetMapping("/list") // 뒤에 ?page=11&size=11 를 붙이면 자동으로 파라미터 수집됨
    public void list(@ModelAttribute("pageVO") PageVO pageVO, Model model){ // PageableDefault 사용시 안 좋으므로 VO를 두고 사용
        Pageable pageable = pageVO.makePageable(0, "bno"); // 0이면 DESC

//        Page<Board> result = boardRepository.findAll(boardRepository.makePredicate(pageVO.getType(), pageVO.getKeyword()), pageable);

        Page<Object[]> result = boardRepository.getCustomPage(pageVO.getType(), pageVO.getKeyword(), pageable);

        log.info("" + pageable);
        log.info("" + result);

        model.addAttribute("result", new PageMaker(result));
    }

    @GetMapping("/register")
    public void registerGET(@ModelAttribute("vo") Board vo){ // vo라는 모델을 추가
        log.info("register get");
    }

    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("vo") Board vo, RedirectAttributes redirectAttributes){
        boardRepository.save(vo);
        redirectAttributes.addFlashAttribute("msg", "success");

        return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
        log.info("BNO : " + bno);
        boardRepository.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
    }

    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model){
        log.info("MODIFY bno : " +bno);
        boardRepository.findById(bno).ifPresent(v -> model.addAttribute("vo", v));
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageVO vo, RedirectAttributes redirectAttributes){
        boardRepository.deleteById(bno);
        redirectAttributes.addFlashAttribute("msg", "success");

        redirectAttributes.addAttribute("page", vo.getPage());
        redirectAttributes.addAttribute("size", vo.getSize());
        redirectAttributes.addAttribute("type", vo.getType());
        redirectAttributes.addAttribute("keyword", vo.getKeyword());
        return "redirect:/boards/list";
    }

    @PostMapping("/modify")
    public String modifyPost(Board board, PageVO vo, RedirectAttributes redirectAttributes){
        boardRepository.findById(board.getBno()).ifPresent(origin -> {
            origin.setTitle(board.getTitle());
            origin.setContent(board.getContent());

            boardRepository.save(origin);
            redirectAttributes.addFlashAttribute("msg", "success");
            redirectAttributes.addAttribute("bno", origin.getBno());
        });

        redirectAttributes.addAttribute("page", vo.getPage());
        redirectAttributes.addAttribute("size", vo.getSize());
        redirectAttributes.addAttribute("type", vo.getType());
        redirectAttributes.addAttribute("keyword", vo.getKeyword());
        return "redirect:/boards/view";
    }
}
