package com.example.demo.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PasswordApiRequest {
    private Long id;
    private String oldPw;
    private String newPw;
}
