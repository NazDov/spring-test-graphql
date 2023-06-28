package com.example.springtestgraphql.dto;

import lombok.Data;

@Data
public class AuthorInputRequest {
    private String name;
    private Integer age;
}
