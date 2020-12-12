package com.yu.project08.controller;

import com.yu.project08.domain.Member;
import com.yu.project08.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/join")
    public void join(){
    }

    @Transactional
    @PostMapping("/join")
    public String joinPost(@ModelAttribute Member member){
        String encryptPw = passwordEncoder.encode(member.getUpw());
        member.setUpw(encryptPw);
        memberRepository.save(member);
        return "/member/joinResult";
    }
}
