package com.thedebuggers.backend.controller.user;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.asset.AssetChangeReqDto;
import com.thedebuggers.backend.dto.community.post.PostResDto;
import com.thedebuggers.backend.dto.user.MyInfoResDto;
import com.thedebuggers.backend.dto.user.ProfileResDto;
import com.thedebuggers.backend.dto.user.UserUpdateReqDto;
import com.thedebuggers.backend.service.community.post.PostService;
import com.thedebuggers.backend.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "사용자 정보 관련 API", tags = {"User"})
@Slf4j
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping
    @ApiOperation(value = "내 정보")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<MyInfoResDto> myInfo(@ApiIgnore Authentication authentication) {

        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long userNo = userDetails.getUser().getNo();

        MyInfoResDto myInfoResDto = userService.getMyInfo(userNo);
        return ResponseEntity.ok(myInfoResDto);
    }

    @GetMapping("/{userNo}")
    @ApiOperation(value = "프로필 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<ProfileResDto> userPosts(@PathVariable Long userNo, @ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long requestUserNo = userDetails.getUser().getNo();

        ProfileResDto profileResDto = userService.getUserProfile(requestUserNo, userNo);

        return ResponseEntity.ok(profileResDto);
    }

    @GetMapping("/{userNo}/post")
    @ApiOperation(value = "회원 게시글 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<PostResDto>> profile(@PathVariable Long userNo) {

        List<PostResDto> posts = postService.getMyPostList(userNo);

        return ResponseEntity.ok(posts);
    }
    @PutMapping
    @ApiOperation(value = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> update(@RequestPart(value = "user_info") UserUpdateReqDto updateReqDto,
                                     @RequestPart(value = "image", required = false) MultipartFile imageFile,
                                     @ApiIgnore Authentication authentication) {

        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        userService.updateUser(user, updateReqDto, imageFile);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @ApiOperation(value = "회원 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<?> delete(@ApiIgnore Authentication authentication) {

        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        userService.deleteUser(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userNo}/follow")
    @ApiOperation(value = "회원 팔로우 / 언팔로우")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<ProfileResDto> followUser(@PathVariable Long userNo, @ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long followerNo = userDetails.getUser().getNo();

        userService.followUser(followerNo, userNo);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/asset-change")
    @ApiOperation(value = "아바타 및 배경 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<User> changeAsset(@ApiIgnore Authentication authentication,
                                             @ApiParam AssetChangeReqDto assetChangeReqDto) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        userService.changeAsset(user, assetChangeReqDto);
        return ResponseEntity.noContent().build();
    }
}