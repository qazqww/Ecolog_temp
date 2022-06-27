package com.thedebuggers.backend.controller.community.post;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.CommentReqDto;
import com.thedebuggers.backend.dto.community.post.CommentResDto;
import com.thedebuggers.backend.service.community.post.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "댓글 관련 API", tags = "Comment")
@Slf4j
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{communityNo}/post/{postNo}/comment")
    @ApiOperation(value = "댓글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommentResDto>> getCommentList(@ApiParam(defaultValue = "1") @PathVariable long postNo) {

        List<CommentResDto> commentList = commentService.getCommentList(postNo);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/{communityNo}/post/{postNo}/comment")
    @ApiOperation(value = "댓글 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> registComment(@ApiParam(defaultValue = "1") @PathVariable long postNo, @RequestBody CommentReqDto commentDto, Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        commentService.registComment(postNo, user, commentDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{communityNo}/post/{postNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommentResDto> getCommentDetail(@ApiParam(defaultValue = "1") @PathVariable long commentNo) {

        CommentResDto comment = commentService.getCommentByNo(commentNo);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{communityNo}/post/{postNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> updateComment(@ApiParam(defaultValue = "1") @PathVariable long commentNo, @RequestBody CommentReqDto commentDto, Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        commentService.updateComment(commentNo, commentDto, user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{communityNo}/post/{postNo}/comment/{commentNo}")
    @ApiOperation(value = "댓글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<String> deleteComment(@ApiParam(defaultValue = "1") @PathVariable long commentNo, Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        commentService.deleteComment(commentNo, user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{communityNo}/mine")
    @ApiOperation(value = "커뮤니티 내 내 댓글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommentResDto>> getMyCommentListInCommunity(@ApiParam(defaultValue = "1") @PathVariable long communityNo, Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long userNo = userDetails.getUser().getNo();

        List<CommentResDto> commentList = commentService.getUserCommentsInCommunity(communityNo, userNo);

        return ResponseEntity.ok(commentList);
    }
}
