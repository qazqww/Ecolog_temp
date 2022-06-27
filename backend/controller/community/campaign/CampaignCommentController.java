package com.thedebuggers.backend.controller.community.campaign;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.CommentReqDto;
import com.thedebuggers.backend.dto.community.post.CommentResDto;
import com.thedebuggers.backend.service.community.campaign.CampaignCommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "캠페인 댓글 관련 API", tags = "CampaignComment")
@Slf4j
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
@RestController
public class CampaignCommentController {

    private final CampaignCommentService campaignCommentService;

    @GetMapping("/{communityNo}/campaign/{campaignNo}/comment")
    @ApiOperation(value = "댓글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommentResDto>> getCampaignCommentList(@ApiParam(defaultValue = "1") @PathVariable long campaignNo) {
        List<CommentResDto> commentList = campaignCommentService.getCampaignCommentList(campaignNo);

        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/{communityNo}/campaign/{campaignNo}/comment")
    @ApiOperation(value = "댓글 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> registCampaignComment(@ApiParam(defaultValue = "1") @PathVariable long campaignNo, @RequestBody CommentReqDto commentDto, @ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        campaignCommentService.registCampaignComment(campaignNo, user, commentDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{communityNo}/campaign/{campaignNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommentResDto> getCampaignCommentDetail(@ApiParam(defaultValue = "1") @PathVariable long commentNo) {

        CommentResDto comment = campaignCommentService.getCampaignCommentByNo(commentNo);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{communityNo}/campaign/{campaignNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> updateCampaignComment(@ApiParam(defaultValue = "1") @PathVariable long commentNo, @RequestBody CommentReqDto commentDto, @ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        campaignCommentService.updateCampaignComment(commentNo, commentDto, user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{communityNo}/campaign/{campaignNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<String> deleteCampaignComment(@ApiParam(defaultValue = "1") @PathVariable long commentNo, @ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        campaignCommentService.deleteCampaignComment(commentNo, user);

        return ResponseEntity.noContent().build();
    }

}
