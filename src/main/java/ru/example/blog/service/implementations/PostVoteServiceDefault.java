package ru.example.blog.service.implementations;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.blog.enums.VoteType;
import ru.example.blog.model.Post;
import ru.example.blog.model.PostVote;
import ru.example.blog.model.User;
import ru.example.blog.repository.PostVoteRepository;
import ru.example.blog.service.PostService;
import ru.example.blog.service.PostVoteService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostVoteServiceDefault implements PostVoteService {

    private final PostVoteRepository repository;
    private final PostService postService;

    @Override public boolean vote(VoteType vote, int postId) {
        User currentUser = postService.checkCurrentUser();
        Post currentPost = postService.findPostById(postId);

        if (currentUser.getId() == 0) {
            return false;
        }

        Optional<PostVote> pv = repository.findPostVoteByPostAndUser(currentPost, currentUser);
        int voteRequested = vote.equals(VoteType.LIKE) ? 1 : -1;

        int userVote = pv.map(PostVote::getValue).orElse(0);

        //if already liked
        if (userVote == voteRequested) {
            return false;
        }

        PostVote postVote = pv.orElse(new PostVote());
        postVote.setPost(currentPost);
        postVote.setTime(LocalDateTime.now());
        postVote.setUser(currentUser);
        postVote.setValue(voteRequested);
        repository.save(postVote);

        return true;
    }

}
