package com.example.springtestgraphql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = "posts")
@ToString(exclude = "posts")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private Set<Post> posts;
    private Status status = Status.NON_ACTIVE;

    public Author(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
