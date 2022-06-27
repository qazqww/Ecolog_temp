package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.LoginType;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("LoginResponse")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginReqDto {

    private String email;
    private String name;
    private LoginType loginType;

}
