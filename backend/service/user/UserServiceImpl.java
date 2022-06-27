package com.thedebuggers.backend.service.user;

import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.Constants;
import com.thedebuggers.backend.common.util.ErrorCode;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserAsset;
import com.thedebuggers.backend.domain.entity.user.UserFollow;
import com.thedebuggers.backend.domain.repository.asset.AssetRepository;
import com.thedebuggers.backend.domain.repository.user.UserAssetRepository;
import com.thedebuggers.backend.domain.repository.user.UserFollowRepository;
import com.thedebuggers.backend.domain.repository.user.UserRepository;
import com.thedebuggers.backend.dto.asset.AssetChangeReqDto;
import com.thedebuggers.backend.dto.user.*;
import com.thedebuggers.backend.common.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${ecolog.default.profile}")
    public String PROFILE_DEFAULT_URL;

    private final UserRepository userRepository;
    private final UserFollowRepository followRepository;
    private final UserAssetRepository userAssetRepository;
    private final AssetRepository assetRepository;

    private final S3Service s3Service;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User createUser(LoginReqDto loginDto) {

        User user = User.builder()
                .email(loginDto.getEmail())
                .name(loginDto.getName())
                .password("")
                .coin(700)
                .nickname(loginDto.getEmail().substring(0, loginDto.getEmail().indexOf('@')))
                .image(PROFILE_DEFAULT_URL)
                .loginType(loginDto.getLoginType())
                .build();

        user = userRepository.save(user);

        UserAsset userAsset = UserAsset.builder().user(user).asset(assetRepository.findById(0L).orElse(null)).build();
        userAssetRepository.save(userAsset);
        userAsset = UserAsset.builder().user(user).asset(assetRepository.findById(0L + Constants.ROOM_ITEM_NO).orElse(null)).build();
        userAssetRepository.save(userAsset);

        return user;
    }

    @Override
    public User getUserByUserNo(Long userNo) {
        return userRepository.findByNo(userNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ProfileResDto getUserProfile(long requestUserNo, long userNo) {
        User profileUser = getUserByUserNo(userNo);
        User requestUser = getUserByUserNo(requestUserNo);

        List<User> profileUserFollowing = followRepository.findAllFolloweeByFollower(profileUser);
        List<User> profileUserFollower = followRepository.findAllFollowerByFollowee(profileUser);

        List<User> requestUserFollowing = followRepository.findAllFolloweeByFollower(requestUser);

        List<FollowUserResDto> followingUserResDtoList
                = profileUserFollowing.stream().map(user -> FollowUserResDto.builder()
                        .no(user.getNo())
                        .email(user.getEmail())
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .birth(user.getBirth())
                        .image(user.getImage())
                        .isFollowing(requestUserFollowing.contains(user))
                        .build()).collect(Collectors.toList());

        List<FollowUserResDto> followerUserResDtoList
                = profileUserFollower.stream().map(user -> FollowUserResDto.builder()
                .no(user.getNo())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .image(user.getImage())
                .isFollowing(requestUserFollowing.contains(user))
                .build()).collect(Collectors.toList());

        return ProfileResDto.builder()
                .no(profileUser.getNo())
                .email(profileUser.getEmail())
                .name(profileUser.getName())
                .nickname(profileUser.getNickname())
                .birth(profileUser.getBirth())
                .image(profileUser.getImage())
                .isFollowing(requestUserFollowing.contains(profileUser))
                .followingUser(followingUserResDtoList)
                .followerUser(followerUserResDtoList)
                .build();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void updateUser(User user, UserUpdateReqDto updateDto, MultipartFile imageFile) {

        String imageUrl = null;

        if (!imageFile.isEmpty()) {
            imageUrl = s3Service.upload(imageFile);
        }

        if (updateDto.getName() != null)
            user.setName(updateDto.getName());
        if (updateDto.getNickname() != null)
            user.setNickname(updateDto.getNickname());
        if (updateDto.getBirth() != null)
            user.setBirth(updateDto.getBirth());
        if (updateDto.getHeight() != null)
            user.setHeight(updateDto.getHeight());
        if (updateDto.getWeight() != null)
            user.setWeight(updateDto.getWeight());
        if (updateDto.getPhone() != null)
            user.setPhone(updateDto.getPhone());
        if (updateDto.getAddress() != null)
            user.setAddress(updateDto.getAddress());

        if (imageUrl != null)
            user.setImage(imageUrl);

        userRepository.save(user);
    }

    @Override
    public void followUser(long followerNo, long followeeNo) {

        User follower = userRepository.findByNo(followerNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        User followee = userRepository.findByNo(followeeNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional<UserFollow> optionalSavedFollowInfo = followRepository.findByFollowerAndFollowee(follower, followee);

        if (optionalSavedFollowInfo.isPresent()) {
            UserFollow savedFollowInfo = optionalSavedFollowInfo.get();

            follower.getFollowing().remove(savedFollowInfo);
            followee.getFollower().remove(savedFollowInfo);

            followRepository.delete(savedFollowInfo);
        } else {
            UserFollow newFollowInfo = UserFollow.builder()
                    .follower(follower)
                    .followee(followee)
                    .build();

            follower.getFollowing().add(newFollowInfo);
            followee.getFollower().add(newFollowInfo);

            followRepository.save(newFollowInfo);
        }

    }

    @Override
    public MyInfoResDto getMyInfo(long userNo) {
        User user = userRepository.findByNo(userNo).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        List<User> userFollowing = followRepository.findAllFolloweeByFollower(user);
        List<User> userFollower = followRepository.findAllFollowerByFollowee(user);

        return MyInfoResDto.builder()
                .no(user.getNo())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .height(user.getHeight())
                .weight(user.getWeight())
                .phone(user.getPhone())
                .image(user.getImage())
                .address(user.getAddress())
                .coin(user.getCoin())
                .avatar(user.getAvatar())
                .room(user.getRoom())
                .loginType(user.getLoginType())
                .followingUser(userFollowing.stream().map(BaseUserInfoResDto::of).collect(Collectors.toList()))
                .followerUser(userFollower.stream().map(BaseUserInfoResDto::of).collect(Collectors.toList()))
                .build();
    }

    @Override
    public void changeAsset(User user, AssetChangeReqDto assetChangeReqDto) {

        long avatarNo = assetChangeReqDto.getAvatarNo();
        long roomNo = assetChangeReqDto.getRoomNo();

        if (userAssetRepository.existsByUserNoAndAssetNo(user.getNo(), avatarNo)
                && userAssetRepository.existsByUserNoAndAssetNo(user.getNo(), roomNo + Constants.ROOM_ITEM_NO)) {
            user.setAvatar(avatarNo);
            user.setRoom(roomNo);
        }
        else {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        userRepository.save(user);
    }
}