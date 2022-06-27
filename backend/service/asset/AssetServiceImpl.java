package com.thedebuggers.backend.service.asset;

import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.Constants;
import com.thedebuggers.backend.common.util.ErrorCode;
import com.thedebuggers.backend.domain.entity.asset.Asset;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserAsset;
import com.thedebuggers.backend.domain.repository.asset.AssetRepository;
import com.thedebuggers.backend.domain.repository.user.UserAssetRepository;
import com.thedebuggers.backend.domain.repository.user.UserRepository;
import com.thedebuggers.backend.dto.asset.AssetReqDto;
import com.thedebuggers.backend.dto.asset.MyAssetResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final UserAssetRepository userAssetRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public boolean buyAsset(User user, AssetReqDto assetReqDto) {

        long assetIdx = assetReqDto.getAssetIdx();
        int assetType = assetReqDto.getAssetType();

        if (assetType == 1)
            assetIdx += Constants.ROOM_ITEM_NO;
        else if (assetType > 1)
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);

        Asset asset = assetRepository.findById(assetIdx).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        int price = asset.getPrice();
        if (user.getCoin() < price) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_COIN);
        }

        boolean hasAsset = userAssetRepository.existsByUserNoAndAssetNo(user.getNo(), assetIdx);
        if (hasAsset) {
            throw new CustomException(ErrorCode.DUPLICATED_ASSET);
        }

        UserAsset userAsset = UserAsset.builder()
                .user(user)
                .asset(asset)
                .build();

        userRepository.updateCoinByNo(user.getNo(), -price);
        userAssetRepository.save(userAsset);
        return true;
    }

    @Override
    public MyAssetResDto getMyAssetList(User user) {

        List<Long> avatarList = new ArrayList<>();
        List<Long> roomList = new ArrayList<>();

        try {
            userAssetRepository.findAllByUserNo(user.getNo()).stream().forEach(
                    data -> {
                        if (data.getAsset().getNo() < Constants.ROOM_ITEM_NO)
                            avatarList.add(data.getAsset().getNo());
                        else
                            roomList.add(data.getAsset().getNo() - Constants.ROOM_ITEM_NO);
                    }
            );
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.CONTENT_EMPTY);
        }

        MyAssetResDto myAssetResDto = MyAssetResDto.builder()
                .avatarList(avatarList)
                .roomList(roomList)
                .build();

        return myAssetResDto;
    }
}
