package com.example.demo.model.network.response;

import com.example.demo.model.entity.MoneyMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileImageApiResponse {
    private Long id;
    private MoneyMember member;
    private String profileImageDirectory;
}
