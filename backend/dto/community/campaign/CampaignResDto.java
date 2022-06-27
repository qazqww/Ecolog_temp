package com.thedebuggers.backend.dto.community.campaign;

import com.thedebuggers.backend.domain.entity.community.campaign.Campaign;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CampaignResDto {
    private long no;
    private String title;
    private String content;
    private String image;
    private String location;

    private LocalDateTime start_date;
    private LocalDateTime end_date;

    private long max_personnel;
    private List<BaseUserInfoResDto> join_personnel;

    private BaseUserInfoResDto writer;

    private boolean isJoining;

    public static CampaignResDto of(Campaign campaign, List<User> userList, boolean isJoining){
        return CampaignResDto.builder()
                .no(campaign.getNo())
                .title(campaign.getTitle())
                .content(campaign.getContent())
                .image(campaign.getImage())
                .location(campaign.getLocation())
                .start_date(campaign.getStart_date())
                .end_date(campaign.getEnd_date())
                .max_personnel(campaign.getMax_personnel())
                .writer(BaseUserInfoResDto.of(campaign.getUser()))
                .join_personnel(userList.stream().map(BaseUserInfoResDto::of).collect(Collectors.toList()))
                .isJoining(isJoining)
                .build();

    }
}
