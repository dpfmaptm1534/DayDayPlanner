package com.example.demo.model.network.request;

import com.example.demo.model.entity.MoneyMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileImageApiRequest {
    private Long memberId;
    private String profileImageDirectory;
}
