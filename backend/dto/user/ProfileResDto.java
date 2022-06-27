package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import com.thedebuggers.backend.dto.user.FollowUserResDto;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ApiModel("UserInfoResponse")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileResDto extends BaseUserInfoResDto {

    private boolean isFollowing;

    private List<FollowUserResDto> followingUser;
    private List<FollowUserResDto> followerUser;

}
