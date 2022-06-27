package com.thedebuggers.backend.dto.community;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommunityResDto {
    private long no;
    private String title;
    private String description;
    private BaseUserInfoResDto manager;
    private String image;
    private String sido;
    private String sigungu;
    private String tag;

    private long joinCount;
    private boolean isJoin;

    public static CommunityResDto of(Community community, long userCount, boolean is_join) {
        return CommunityResDto.builder()
                .no(community.getNo())
                .title(community.getTitle())
                .description(community.getDescription())
                .manager(BaseUserInfoResDto.of(community.getManager()))
                .image(community.getImage())
                .sido(community.getSido())
                .sigungu(community.getSigungu())
                .tag(community.getTag())
                .joinCount(userCount)
                .isJoin(is_join)
                .build();
    }
}
