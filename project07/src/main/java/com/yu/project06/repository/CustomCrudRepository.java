package com.yu.project06.repository;

import com.yu.project06.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface CustomCrudRepository extends CrudRepository<Board, Long>, CustomBoard {
}
