package com.thedebuggers.backend.domain.entity.community.post;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.community.Community;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private boolean isOpen;
    private int likeCount;
    private int type;

    @ManyToOne
    @JoinColumn(name = "community_no")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private Set<Comment> comments = new HashSet<>();

}
