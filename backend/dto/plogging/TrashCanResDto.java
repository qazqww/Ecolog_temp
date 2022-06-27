package com.thedebuggers.backend.dto.plogging;

import com.thedebuggers.backend.domain.entity.plogging.TrashCan;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TrashCanResDto {
    private long no;
    private String address;
    private String image;
    private double lat;
    private double lng;

    private BaseUserInfoResDto user;

    public static TrashCanResDto of(TrashCan trashCan) {
        return TrashCanResDto.builder()
                .no(trashCan.getNo())
                .address(trashCan.getAddress())
                .image(trashCan.getImage())
                .lat(trashCan.getLocation().getY())
                .lng(trashCan.getLocation().getX())
                .user(BaseUserInfoResDto.of(trashCan.getUser()))
                .build();
    }
}
