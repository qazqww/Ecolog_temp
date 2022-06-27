package com.thedebuggers.backend.dto.community;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommunityResponse")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommunityDto {

    private long no;
    private String title;
    private String description;
    private long userNo;
    private String image;
    private String sido;
    private String sigungu;
    private String tag;

}
