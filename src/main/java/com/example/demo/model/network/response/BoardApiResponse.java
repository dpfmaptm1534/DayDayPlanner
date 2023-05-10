package com.example.demo.model.network.response;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardApiResponse {
    private Long id;
    private Long eventDate;
    private String eventTitle;
    private Sheet sheet;
}
