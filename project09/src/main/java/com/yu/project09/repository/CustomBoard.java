package com.yu.project09.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoard {
    public Page<Object[]> getCustomPage(String type, String keyword, Pageable pageable);
}
