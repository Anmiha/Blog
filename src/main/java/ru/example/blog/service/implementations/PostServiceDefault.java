package ru.example.blog.service.implementations;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.example.blog.dto.PlainPostDto;
import ru.example.blog.dto.response.PostResponse;
import ru.example.blog.mappers.EntityMapper;
import ru.example.blog.model.Post;
import ru.example.blog.repository.PostRepository;
import ru.example.blog.repository.UserRepository;
import ru.example.blog.service.PostService;
import ru.example.blog.service.TagService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceDefault implements PostService {
    private final PostRepository repository;
    private final UserRepository userRepository;
    private final TagService tagService;


    private EntityMapper entityMapper;

    @Override
    public PostResponse getPosts(String mode, Pageable pageable) {
        Page<Post> pagePost = findByMode(mode, pageable);
        List<Post> posts = pagePost.getContent();

        List<PlainPostDto> postsList = new ArrayList<>();
        for (Post post : posts) {
            PlainPostDto postDto = entityMapper.postToPlainPostDto(post);
            postsList.add(postDto);
        }

        PostResponse response = new PostResponse();
        response.setPosts(postsList, pagePost.getTotalElements());
        return response;
    }

    private Page<Post> findByMode(String mode, Pageable pageable) {
        Page<Post> list = Page.empty();
        switch (mode) {
            case "best":
                list = repository.getBestPosts(pageable);
                break;
            case "recent":
                list = repository.findRecentPosts(pageable);
                break;
            case "popular":
                list = repository.getPopularPosts(pageable);
                break;
            case "early":
                list = repository.findEarlyPosts(pageable);
                break;
        }
        return list;
    }

 }
