package com.example.springtestgraphql;

import com.example.springtestgraphql.dto.AuthorInputRequest;
import com.example.springtestgraphql.dto.PostInputRequest;
import com.example.springtestgraphql.model.Author;
import com.example.springtestgraphql.model.Post;
import com.example.springtestgraphql.model.Status;
import com.example.springtestgraphql.repository.AuthorRepository;
import com.example.springtestgraphql.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Controller
@Slf4j
public class GraphQLController {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public GraphQLController(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    @MutationMapping
    public Post newPost(@Argument("input") PostInputRequest inputRequest) {
        log.info("newPost, input: {}", inputRequest);

        Author author = authorRepository
                .findById(inputRequest.getAuthorId())
                .orElse(new Author("Nazar", 32));

        if (ObjectUtils.isEmpty(author.getId())) {
            authorRepository.save(author);
        }

        Post post = Post.builder()
                .author(author)
                .content(inputRequest.getContent())
                .title(inputRequest.getTitle())
                .status(Status.ACTIVE)
                .build();

        post = postRepository.save(post);

        return post;
    }

    @MutationMapping
    public Author newAuthor(@Argument("input") AuthorInputRequest inputRequest) {
        log.info("newAuthor, input: {}", inputRequest);
        Author author = new Author(inputRequest.getName(), inputRequest.getAge());
        author = authorRepository.save(author);
        return author;
    }

    @QueryMapping
    public List<Post> recentPosts(Integer limit, Integer offset, String orderBy) {
        log.info("recentPosts, params: {}, {}", limit, offset);
        PageRequest pageRequest = PageRequest.of(limit, offset, Sort.Direction.DESC, orderBy);
        return postRepository.findAll(pageRequest).getContent();
    }

    @QueryMapping
    public List<Author> authorsWithTopPosts() {
        log.info("authorsWithTopPosts");
        return authorRepository.findAuthorsWithPosts();
    }
}
