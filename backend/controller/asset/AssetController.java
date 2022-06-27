package com.thedebuggers.backend.controller.asset;

import com.thedebuggers.backend.auth.ELUserDetails;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.asset.AssetReqDto;
import com.thedebuggers.backend.dto.asset.MyAssetResDto;
import com.thedebuggers.backend.service.asset.AssetService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "유니티 에셋 관련 API", tags = {"Asset"})
@Slf4j
@RequestMapping("/api/v1/asset")
@RequiredArgsConstructor
@RestController
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    @ApiOperation(value = "에셋 구매")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    ResponseEntity<Boolean> buyAsset(@ApiIgnore Authentication authentication,
                                     @ApiParam(value = "") @RequestBody AssetReqDto assetReqDto) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        boolean result = assetService.buyAsset(user, assetReqDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @ApiOperation(value = "보유 에셋 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    ResponseEntity<MyAssetResDto> getMyAssetList(@ApiIgnore Authentication authentication) {
        ELUserDetails userDetails = (ELUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        MyAssetResDto myAssetList = assetService.getMyAssetList(user);
        return ResponseEntity.ok(myAssetList);
    }

}
