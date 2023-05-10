package com.example.demo.model.repository;

import com.example.demo.model.entity.MoneyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyBoardRepository extends JpaRepository<MoneyBoard,Long> {
    List<MoneyBoard> findAllBySheetIdOrderByEventDate(Long sheetId);
}
