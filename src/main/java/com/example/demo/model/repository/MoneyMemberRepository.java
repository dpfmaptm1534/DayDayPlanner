package com.example.demo.model.repository;

import com.example.demo.model.entity.MoneyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoneyMemberRepository extends JpaRepository<MoneyMember,Long> {
    Optional<MoneyMember> findByUserId(String userId);
}
