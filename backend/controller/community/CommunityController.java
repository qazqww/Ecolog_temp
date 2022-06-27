package com.thedebuggers.backend.controller.community;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.CommunityDto;
import com.thedebuggers.backend.dto.community.CommunityResDto;
import com.thedebuggers.backend.dto.user.BaseUserInfoResDto;
import com.thedebuggers.backend.service.community.CommunityService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;


@Api(value = "커뮤니티 API", tags = {"Community"})
@Slf4j
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping
    @ApiOperation(value = "커뮤니티 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommunityResDto>> getCommunityList(
            @ApiIgnore Authentication authentication
    ){
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        long userNo = user.getNo();

        List<Community> communityList = communityService.getCommunityList();

        List<CommunityResDto> result = communityList.stream().map(community -> {
            long userCount = communityService.getCommunityMemberCount(community.getNo());
            boolean is_join = communityService.checkCommunityUser(userNo, community.getNo());
            return CommunityResDto.of(community, userCount, is_join);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ApiOperation(value = "커뮤니티 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommunityResDto> registCommunity(
            @RequestPart(value = "community_info") CommunityDto communityDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            Authentication authentication
    ){
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        long userNo = user.getNo();

        Community community = communityService.registCommunity(communityDto, userNo, imageFile);
        long userCount = communityService.getCommunityMemberCount(community.getNo());
        boolean is_join = communityService.checkCommunityUser(userNo, community.getNo());

        return ResponseEntity.ok(CommunityResDto.of(community, userCount, is_join));
    }

    @GetMapping("/{no}")
    @ApiOperation(value = "커뮤니티 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommunityResDto> getCommunity(
            @ApiParam("커뮤니티 번호") @PathVariable long no,
            @ApiIgnore Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        long userNo = user.getNo();

        Community community = communityService.getCommunity(no);
        long userCount = communityService.getCommunityMemberCount(community.getNo());
        boolean is_join = communityService.checkCommunityUser(userNo, community.getNo());

        return ResponseEntity.ok(CommunityResDto.of(community, userCount, is_join));
    }


    @PostMapping("/{no}")
    @ApiOperation(value = "커뮤니티 가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommunityResDto> joinCommunity(
            @ApiParam("커뮤니티 번호") @PathVariable long no,
            Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        Community community = communityService.joinCommunity(no, user);
        long userCount = communityService.getCommunityMemberCount(community.getNo());
        boolean is_join = communityService.checkCommunityUser(user.getNo(), community.getNo());


        return ResponseEntity.ok(CommunityResDto.of(community, userCount, is_join));
    }

    @PutMapping("/{communityNo}")
    @ApiOperation(value = "커뮤니티 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<CommunityResDto> updateCommunity(
        @ApiParam("커뮤니티 번호") @PathVariable long communityNo,
        @RequestPart(value = "community_info") CommunityDto communityDto,
        @RequestPart(value = "image", required = false) MultipartFile imageFile,
        Authentication authentication
    ){
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        Community community = communityService.updateCommunity(communityNo, user, communityDto, imageFile);
        long userCount = communityService.getCommunityMemberCount(community.getNo());
        boolean is_join = communityService.checkCommunityUser(user.getNo(), community.getNo());

        return ResponseEntity.ok(CommunityResDto.of(community, userCount, is_join));
    }

    @DeleteMapping("/{communityNo}")
    @ApiOperation(value = "커뮤니티 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<String> quitCommunity(
            @ApiParam("커뮤니티 번호") @PathVariable long communityNo,
            Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        long userNo = user.getNo();

        try {
            communityService.quitCommunity(communityNo, userNo);
        }catch (Exception e) {
            return ResponseEntity.ok("Failed");
        }
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/{no}/member")
    @ApiOperation(value = "커뮤니티 멤버 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<BaseUserInfoResDto>> getCommunityMember(
            @ApiParam("커뮤니티 번호") @PathVariable long no
    ){
        List<User> result = communityService.getCommunityMember(no);
        List<BaseUserInfoResDto> profileResDtoList = result.stream().map(BaseUserInfoResDto::of).collect(Collectors.toList());
        return ResponseEntity.ok(profileResDtoList);
    }

    @DeleteMapping("/{communityNo}/delete")
    @ApiOperation(value = "커뮤니티 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<String> deleteCommunity(
            @ApiParam("커뮤니티 번호") @PathVariable long communityNo,
            Authentication authentication
    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();

        try {
            communityService.deleteCommunity(communityNo, user);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("Failed");
        }
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/mine")
    @ApiOperation(value = "내가 가입한 커뮤니티 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommunityResDto>> getMyCommunityList(@ApiIgnore Authentication authentication){
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        long userNo = userDetails.getUser().getNo();

        List<Community> communityList = communityService.getMyCommunityList(userNo);

        List<CommunityResDto> result = communityList.stream().map(community -> {
            long userCount = communityService.getCommunityMemberCount(community.getNo());
            boolean is_join = communityService.checkCommunityUser(userNo, community.getNo());
            return CommunityResDto.of(community, userCount, is_join);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/popular")
    @ApiOperation(value = "인기 커뮤니티 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    private ResponseEntity<List<CommunityResDto>> getPopularCommunityList(
            @ApiIgnore Authentication authentication

    ) {
        ELUserDetails userDetails = (ELUserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        long userNo = user.getNo();

        List<Community> communityList = communityService.getPopularCommunityList();

        return ResponseEntity.ok(communityList.stream().map(community -> {
            long userCount = communityService.getCommunityMemberCount(community.getNo());
            boolean is_join = communityService.checkCommunityUser(userNo, community.getNo());
            return CommunityResDto.of(community, userCount, is_join);
        }).collect(Collectors.toList()));
    }

}
