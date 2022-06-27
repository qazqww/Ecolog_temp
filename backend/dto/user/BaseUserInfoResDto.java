package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel("WriterUserInfoResponse")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseUserInfoResDto {
    private long no;
    private String email;
    private String name;
    private String nickname;
    private String birth;
    private String image;

    public static BaseUserInfoResDto of(User user){
        return BaseUserInfoResDto.builder()
                .no(user.getNo())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .image(user.getImage())
                .build();
    }
}
