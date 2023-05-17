package com.example.demo.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberApiRequest {
    private Long id;
//    private String userId;
//    private String userPw;ㅎ
    private String userName;
//    private String profileImage;
}
