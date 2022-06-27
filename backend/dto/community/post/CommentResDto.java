package com.thedebuggers.backend.dto.community.post;

import com.thedebuggers.backend.domain.entity.community.campaign.CampaignComment;
import com.thedebuggers.backend.domain.entity.community.post.Comment;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentResDto {
    private long no;
    private BaseUserInfoResDto writer;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResDto of(Comment comment){
        return CommentResDto.builder()
                .no(comment.getNo())
                .writer(BaseUserInfoResDto.of(comment.getUser()))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static CommentResDto of(CampaignComment comment){
        return CommentResDto.builder()
                .no(comment.getNo())
                .writer(BaseUserInfoResDto.of(comment.getUser()))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
