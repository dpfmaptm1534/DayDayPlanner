package com.example.demo.model.repository;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SheetRepository extends JpaRepository<Sheet,Long> {
     List<Sheet> findAllByMemberIdOrderByIdDesc(Long memberId);
}
