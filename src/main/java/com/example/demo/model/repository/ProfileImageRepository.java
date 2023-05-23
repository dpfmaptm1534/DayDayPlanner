package com.example.demo.model.repository;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage,Long> {
    Optional<ProfileImage> findByMemberId(Long memberId);
}
