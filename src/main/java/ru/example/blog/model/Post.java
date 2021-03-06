package ru.example.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.example.blog.enums.ModerationStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_active", columnDefinition = "TINYINT ")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "enum('NEW','ACCEPTED','DECLINED') not null default 'NEW'")
    private ModerationStatus moderationStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private User moderator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id") //inject
    private User user;
    @Column(columnDefinition = "datetime not null")
    private Instant time;
    @Column(columnDefinition = "varchar(255) not null")
    private String title;
    @Column(name = "text", columnDefinition = "text not null")
    private String text;
    @Column(name = "view_count", columnDefinition = "int not null")
    private int viewCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostComment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostVote> postVotes;

    public List<PostVote> getPostVotes() {
        return postVotes;
    }

    public void setPostVotes(List<PostVote> postVotes) {
        this.postVotes = postVotes;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
        joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

}
