package com.thedebuggers.backend.dto.asset;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MyAssetResDto {
    List<Long> avatarList;
    List<Long> roomList;
}