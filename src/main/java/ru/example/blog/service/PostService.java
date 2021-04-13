package ru.example.blog.service;


import org.springframework.data.domain.Pageable;
import ru.example.blog.dto.response.PostResponse;

public interface PostService {
    PostResponse getPosts(String mode, Pageable pageable);

//    PostWithCommentsResponse getPost(Integer id);
//
//    CalendarDto getCalendar(Integer year);
//
//    PostResponse findPostsByQuery(String query, Pageable pageable);
//
//    PostResponse findPostsByDate(String dateTime, Pageable pageable);
//
//    PostResponse findPostsByTag(String tag, Pageable pageable);
//
//    PostResponse findPostsForModeration(ModerationStatus status, Pageable pageable);
//
//    PostResponse findMyPosts(PostModerationStatus status, Pageable pageable);
//
//    @Transactional
//    ResultResponse<NewPostResponse> addNewPost(NewPostRequest request, Errors errors);
//
//    @Transactional
//    ResultResponse<NewPostResponse> editPost(Integer id, NewPostRequest request, Errors errors);
//
//    boolean moderatePost(ModerateRequest request);
//
//    User checkCurrentUser();
//
//    Post findPostById(Integer id);
}
