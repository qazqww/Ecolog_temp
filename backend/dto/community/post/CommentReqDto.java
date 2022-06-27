package com.thedebuggers.backend.dto.community.post;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentReqDto {
    private String content;
}
