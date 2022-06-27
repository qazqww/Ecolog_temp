package com.thedebuggers.backend.service.user;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.asset.AssetChangeReqDto;
import com.thedebuggers.backend.dto.user.LoginReqDto;
import com.thedebuggers.backend.dto.user.MyInfoResDto;
import com.thedebuggers.backend.dto.user.ProfileResDto;
import com.thedebuggers.backend.dto.user.UserUpdateReqDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User getUserByEmail(String email);

    User createUser(LoginReqDto loginDto);

    User getUserByUserNo(Long userNo);

    ProfileResDto getUserProfile(long requestUserNo, long userNo);

    void deleteUser(User user);

    void updateUser(User user, UserUpdateReqDto updateDto, MultipartFile imageFile);

    void followUser(long followerNo, long followeeNo);

    MyInfoResDto getMyInfo(long userNo);

    void changeAsset(User user, AssetChangeReqDto assetChangeReqDto);
}