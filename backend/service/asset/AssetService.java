package com.thedebuggers.backend.service.asset;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.asset.AssetReqDto;
import com.thedebuggers.backend.dto.asset.MyAssetResDto;

public interface AssetService {
    boolean buyAsset(User user, AssetReqDto assetReqDto);
    MyAssetResDto getMyAssetList(User user);
}
