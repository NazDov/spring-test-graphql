package com.example.springtestgraphql.dto;

import lombok.Data;

@Data
public class PostInputRequest {
    String title;
    String content;
    Long authorId;
}
