package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@ApiModel("FollowUserResDto")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FollowUserResDto extends BaseUserInfoResDto {

    private boolean isFollowing;

}
