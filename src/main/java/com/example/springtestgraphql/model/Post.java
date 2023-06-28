package com.example.springtestgraphql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "author")
@ToString(exclude = "author")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Author author;
    private Status status;

}
