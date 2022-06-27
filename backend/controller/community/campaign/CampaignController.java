package com.thedebuggers.backend.controller.community.campaign;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.campaign.CampaignReqDto;
import com.thedebuggers.backend.dto.community.campaign.CampaignResDto;
import com.thedebuggers.backend.service.community.campaign.CampaignService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "캠페인 API", tags = "Campaign")
@Slf4j
@RequestMapping("/api/v1/community/{communityNo}/campaign")
@RequiredArgsConstructor
@RestController
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    @ApiOperation(value = "캠페인 모집 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CampaignResDto> registCampaign(
            @RequestPart(value = "campaign_info") CampaignReqDto campaignReqDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @PathVariable long communityNo,
            Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();


        CampaignResDto campaignResDto = campaignService.registCampaign(campaignReqDto, communityNo, user, imageFile);


        return ResponseEntity.ok(campaignResDto);
    }

    @GetMapping
    @ApiOperation(value = "캠페인 모집 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CampaignResDto>> getCampaignList(
            @PathVariable long communityNo,
            @ApiIgnore Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        List<CampaignResDto> result = campaignService.getCampaignList(communityNo, user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{campaignNo}")
    @ApiOperation(value = "켐페인 모집 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CampaignResDto> getCampaign(
            @PathVariable long campaignNo,
            @ApiIgnore Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        CampaignResDto campaignResDto = campaignService.getCampaign(campaignNo, user);
        return ResponseEntity.ok(campaignResDto);
    }

    @PostMapping("/{campaignNo}")
    @ApiOperation(value = "캠페인 모집 참가 / 참가취소")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CampaignResDto> joinCampaign(
            @PathVariable long campaignNo,
            Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        CampaignResDto campaignResDto = campaignService.joinCampaign(campaignNo, user);
        return ResponseEntity.ok(campaignResDto);
    }

    @PutMapping("/{campaignNo}")
    @ApiOperation(value = "캠페인 모집 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CampaignResDto> updateCampaign(
            @RequestPart(value = "campaign_info") CampaignReqDto campaignReqDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @PathVariable long campaignNo,
            @ApiIgnore Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        CampaignResDto campaignResDto = campaignService.updateCampaign(campaignReqDto, campaignNo, user, imageFile);

        return ResponseEntity.ok(campaignResDto);
    }

    @DeleteMapping("/{campaignNo}")
    @ApiOperation(value = "캠페인 모집 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<Boolean> deleteCampaign(
            @PathVariable long campaignNo,
            @ApiIgnore Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        boolean result = campaignService.deleteCampaign(user, campaignNo);

        return ResponseEntity.ok(result);
    }

}
