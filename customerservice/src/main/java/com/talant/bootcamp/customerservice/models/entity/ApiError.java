package com.talant.bootcamp.customerservice.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    private String message;

    public ApiError(int code, String message){
        super();
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
