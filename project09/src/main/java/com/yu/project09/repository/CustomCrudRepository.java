package com.yu.project09.repository;

import com.yu.project09.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface CustomCrudRepository extends CrudRepository<Board, Long>, CustomBoard {
}
