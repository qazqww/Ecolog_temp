package com.thedebuggers.backend.dto.community.post;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.community.post.Post;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostResDto {
    private long no;
    private long communityNo;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private boolean isOpen;
    private long likeCount;
    private BaseUserInfoResDto writer;
    private boolean isLiked;

    public static PostResDto of(Post post) {
        return PostResDto.builder()
                .no(post.getNo())
                .communityNo(post.getCommunity().getNo())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .isOpen(post.isOpen())
                .likeCount(post.getLikeCount())
                .writer(BaseUserInfoResDto.of(post.getUser()))
                .build();
    }

    public static PostResDto of(Post post, boolean isLiked) {
        return PostResDto.builder()
                .no(post.getNo())
                .communityNo(post.getCommunity().getNo())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .isOpen(post.isOpen())
                .likeCount(post.getLikeCount())
                .writer(BaseUserInfoResDto.of(post.getUser()))
                .isLiked(isLiked)
                .build();
    }
}
