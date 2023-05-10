package com.example.demo.model.entity;

import com.example.demo.model.config.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;

@Entity(name = "moneymember")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPw;
    private String userName;
    private String profileImage;
}
