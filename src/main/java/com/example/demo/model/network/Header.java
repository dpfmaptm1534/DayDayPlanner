package com.example.demo.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header<T> {
    private LocalDateTime transactionTime;
    private String status;
    private String description;

    private T data;


    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .status("OK")
                .description("정상")
                .build();
    }

    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .status("OK")
                .description("정상")
                .data(data)
                .build();
    }

    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .status("ERROR")
                .description(description)
                .build();
    }

    public static <T> Header<T> ERROR(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .status("ERROR")
                .build();
    }
}