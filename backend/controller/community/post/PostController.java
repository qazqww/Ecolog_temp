package com.thedebuggers.backend.controller.community.post;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.PostReqDto;
import com.thedebuggers.backend.dto.community.post.PostResDto;
import com.thedebuggers.backend.service.community.post.PostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "커뮤니티 게시물 관련 API", tags = "Post")
@Slf4j
@RequestMapping("/api/v1/community/{communityNo}/post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    @ApiOperation(value = "게시물 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<PostResDto> registPost(@ApiIgnore Authentication authentication,
                                                  @ApiParam(defaultValue = "1") @PathVariable long communityNo,
                                                  @RequestPart(value = "post_info") PostReqDto postReqDto,
                                                  @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        PostResDto postResDto = postService.registPost(user, postReqDto, communityNo, imageFile);
        return ResponseEntity.ok(postResDto);
    }

    @GetMapping
    @ApiOperation(value = "게시물 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<PostResDto>> getPostList(
            @ApiParam(value = "0 : 전체 커뮤니티의 공개 게시물, 1~ : 해당 커뮤니티의 전체 게시물", defaultValue = "1") @PathVariable long communityNo,
            @ApiParam(value = "입력 키워드: notice, free, campaign", defaultValue = "free") @RequestParam String type) {
        List<PostResDto> postList;

        if (communityNo == 0)
            postList = postService.getAllPost();
        else
            postList = postService.getPostList(communityNo, type);

        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{postNo}")
    @ApiOperation(value = "게시물 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<PostResDto> getPost(@ApiIgnore Authentication authentication,
                                               @ApiParam(defaultValue = "1") @PathVariable long postNo) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        long userNo = userDetails.getUser().getNo();
        PostResDto postResDto = postService.getPost(userNo, postNo);
        return ResponseEntity.ok(postResDto);
    }

    @PutMapping("/{postNo}")
    @ApiOperation(value = "게시글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<Boolean> modifyPost(@ApiIgnore Authentication authentication,
                                               @PathVariable long postNo,
                                               @RequestPart(value = "post_info") PostReqDto postDto,
                                               @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        long userNo = userDetails.getUser().getNo();
        boolean result = postService.modifyPost(userNo, postNo, postDto, imageFile);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{postNo}")
    @ApiOperation(value = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<Boolean> deletePost(@ApiIgnore Authentication authentication,
                                               @PathVariable long postNo) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        long userNo = userDetails.getUser().getNo();
        boolean result = postService.deletePost(userNo, postNo);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{postNo}/like")
    @ApiOperation(value = "게시글 좋아요 / 취소")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<Boolean> likePost(@ApiIgnore Authentication authentication,
                                             @PathVariable long postNo) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long userNo = userDetails.getUser().getNo();
        boolean result = postService.likePost(userNo, postNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mine")
    @ApiOperation(value = "커뮤니티 내 게시물 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<PostResDto>> getMyPostList(@ApiIgnore Authentication authentication,
                                                           @ApiParam(defaultValue = "1") @PathVariable long communityNo) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long userNo = userDetails.getUser().getNo();

        List<PostResDto> postList = postService.getMyPostListInCommunity(communityNo, userNo);

        return ResponseEntity.ok(postList);
    }
}


