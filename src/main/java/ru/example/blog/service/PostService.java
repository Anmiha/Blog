package ru.example.blog.service;


import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import ru.example.blog.dto.CalendarDto;
import ru.example.blog.dto.request.ModerateRequest;
import ru.example.blog.dto.request.NewPostRequest;
import ru.example.blog.dto.response.PostResponse;
import ru.example.blog.dto.response.PostWithCommentsResponse;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.dto.response.type.NewPostResponse;
import ru.example.blog.enums.ModerationStatus;
import ru.example.blog.enums.PostModerationStatus;
import ru.example.blog.model.Post;
import ru.example.blog.model.User;

import javax.transaction.Transactional;

public interface PostService {
    PostResponse getPosts(String mode, Pageable pageable);

    PostWithCommentsResponse getPost(Integer id);

    CalendarDto getCalendar(Integer year);

    PostResponse findPostsByQuery(String query, Pageable pageable);

    PostResponse findPostsByDate(String dateTime, Pageable pageable);

    PostResponse findPostsByTag(String tag, Pageable pageable);

    PostResponse findPostsForModeration(ModerationStatus status, Pageable pageable);

    PostResponse findMyPosts(PostModerationStatus status, Pageable pageable);

    @Transactional
    ResultResponse<NewPostResponse> addNewPost(NewPostRequest request, Errors errors);

    @Transactional
    ResultResponse<NewPostResponse> editPost(Integer id, NewPostRequest request, Errors errors);

    boolean moderatePost(ModerateRequest request);

    User checkCurrentUser();

    Post findPostById(Integer id);
}
