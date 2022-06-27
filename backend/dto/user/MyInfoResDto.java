package com.thedebuggers.backend.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.LoginType;
import com.thedebuggers.backend.domain.entity.user.User;
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
public class MyInfoResDto extends BaseUserInfoResDto {
    private double height;
    private double weight;
    private String phone;
    private String address;
    private long coin;
    private long avatar;
    private long room;
    private LoginType loginType;

    private List<BaseUserInfoResDto> followingUser;
    private List<BaseUserInfoResDto> followerUser;

    public static MyInfoResDto of(User user){
        return MyInfoResDto.builder()
                .no(user.getNo())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .height(user.getHeight())
                .weight(user.getWeight())
                .phone(user.getPhone())
                .image(user.getImage())
                .address(user.getAddress())
                .coin(user.getCoin())
                .avatar(user.getAvatar())
                .room(user.getRoom())
                .loginType(user.getLoginType())
                .build();
    }
}
