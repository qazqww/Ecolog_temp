package com.thedebuggers.backend.dto.user;

import lombok.*;

import javax.annotation.Nullable;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateReqDto {
    private String name;
    private String nickname;
    private String birth;
    private Double height;
    private Double weight;
    private String phone;
    private String address;
}
