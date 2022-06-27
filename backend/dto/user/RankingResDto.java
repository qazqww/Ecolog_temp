package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RankingResDto implements Cloneable {
    private int ranking;
    private BaseUserInfoResDto user;
    private int cnt;
    private double dist;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static RankingResDto of(User user, int cnt, double dist) {
        return RankingResDto.builder()
                .user(BaseUserInfoResDto.of(user))
                .cnt(cnt)
                .dist(dist)
                .build();
    }

    public static RankingResDto of(int ranking, User user, int cnt, double dist) {
        return RankingResDto.builder()
                .ranking(ranking)
                .user(BaseUserInfoResDto.of(user))
                .cnt(cnt)
                .dist(dist)
                .build();
    }
}
