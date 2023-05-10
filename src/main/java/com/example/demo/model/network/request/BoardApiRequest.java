package com.example.demo.model.network.request;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardApiRequest {
    private Long id;
    private Long memberId;
    private Long eventDate;
    private String eventTitle;
    private Long sheetId;
}
