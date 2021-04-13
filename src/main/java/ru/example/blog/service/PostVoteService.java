package ru.example.blog.service;


import ru.example.blog.enums.VoteType;

public interface PostVoteService {
    boolean vote(VoteType vote, int postId);
}
