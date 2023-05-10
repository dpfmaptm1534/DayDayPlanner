package com.example.demo.model.network.request;

import com.example.demo.model.entity.MoneyMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SheetApiRequest {
    private Long id;
    private Long memberId;
    private String plannerTitle;

}
