package com.yu.project06.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "pageList")
@Log
public class PageMaker<T> {
    private Page<T> result;

    private Pageable prevPage;
    private Pageable nextPage;

    private int currentPageNum;
    private int totalPageNum;

    private Pageable currentPage;
    private List<Pageable> pageList;

    public PageMaker(Page<T> result){
        this.result = result;
        this.currentPage = result.getPageable();
        this.currentPageNum = currentPage.getPageNumber() + 1;
        this.totalPageNum = result.getTotalPages();
        this.pageList = new ArrayList<>();
        calcPages();
    }
    private void calcPages(){
        int tmpEndNum = (int)(Math.ceil(this.currentPageNum/10.0) * 10); // 13이라고 치면 20이 tmpEndNum(끝 숫자)
        int startNum = tmpEndNum - 9; // 13이라고 치면 11이 첫 숫자

        Pageable startPage = this.currentPage;

        // move to start Pageable
        for(int i = startNum; i < this.currentPageNum; i++){
            startPage = startPage.previousOrFirst();
        }
        this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();

        // log.info("템프엔드넘: " + tmpEndNum);
        // log.info("토탈: " + totalPageNum);

        if(this.totalPageNum < tmpEndNum){
            tmpEndNum = this.totalPageNum;
            this.nextPage = null;
        }
        for(int i = startNum; i <= tmpEndNum; i++){
            pageList.add(startPage);
            startPage = startPage.next();
        }
        this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
    }
}
