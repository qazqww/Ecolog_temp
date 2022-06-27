package com.thedebuggers.backend.domain.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Reward {

    PLOGGING_REWARD(100, "플로깅 점수 획득"),
    TRASH_CAN_REWARD(100, "쓰레기 등록 점수 획득"),
    CAMPAIGN_REWARD(100, "캠페인 인증 점수 획득");

    private final int point;
    private final String message;
}
