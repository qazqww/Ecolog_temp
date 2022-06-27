package com.thedebuggers.backend.dto.community.campaign;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CampaignReqDto {
    private String title;
    private String content;
    private String location;

    private LocalDateTime start_date;
    private LocalDateTime end_date;

    private long max_personnel;
}
