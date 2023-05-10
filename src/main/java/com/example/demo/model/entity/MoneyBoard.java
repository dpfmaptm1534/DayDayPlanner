package com.example.demo.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "moneyboard")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private MoneyMember member;
    private Long eventDate;
    private String eventTitle;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;
}
