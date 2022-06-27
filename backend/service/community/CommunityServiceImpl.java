package com.thedebuggers.backend.service.community;


import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserCommunity;
import com.thedebuggers.backend.domain.repository.community.CommunityRepository;
import com.thedebuggers.backend.domain.repository.user.UserCommunityRepository;
import com.thedebuggers.backend.domain.repository.user.UserRepository;
import com.thedebuggers.backend.dto.community.CommunityDto;
import com.thedebuggers.backend.common.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final UserCommunityRepository userCommunityRepository;
    private final S3Service s3Service;

    @Override
    public List<Community> getCommunityList() {

        return communityRepository.findAllByOrderByNoDesc();
    }

    @Override
    public Community getCommunity(long no) {
        return communityRepository.findByNo(no);
    }

    @Override
    public Community registCommunity(CommunityDto communityDto, long userNo, MultipartFile imageFile) {
//        User user = userRepository.findByNo(userNo).orElseThrow(new CustomException(ErrorCode.NOT_FOUND));

        String imageUrl = null;
        if (imageFile != null) {
            imageUrl = s3Service.upload(imageFile);
        }

        Community community = Community.builder()
                .title(communityDto.getTitle())
                .description(communityDto.getDescription())
                .image(imageUrl)
                .manager(userRepository.findByNo(userNo).orElse(null))
                .sido(communityDto.getSido())
                .sigungu(communityDto.getSigungu())
                .tag(communityDto.getTag())
                .build();

        community = communityRepository.save(community);

        UserCommunity userCommunity = UserCommunity.builder()
                .user(userRepository.findByNo(userNo).orElse(null))
                .community(community)
                .build();

//        joinCommunity(community.getNo(), user);
        userCommunity = userCommunityRepository.save(userCommunity);

        return community;
    }

    @Override
    public Community joinCommunity(long no, User user) {

        Community community = communityRepository.findByNo(no);

        UserCommunity userCommunity = UserCommunity.builder()
                .user(user)
                .community(community)
                .build();
        userCommunity = userCommunityRepository.save(userCommunity);

        return community;
    }

    @Override
    public List<User> getCommunityMember(long no) {

        List<User> userList = userCommunityRepository.findAllUserByCommunityNo(no);

        return userList;
    }

    @Override
    public long getCommunityMemberCount(long communityNo) {

        long userCount = userCommunityRepository.findCommunityCountByCommunityNo(communityNo);

        return userCount;
    }

    @Override
    public boolean checkCommunityUser(long userNo, long communityNo) {

        UserCommunity userCommunity = userCommunityRepository.findByCommunityNoAndUserNo(communityNo, userNo);

        if (userCommunity != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Community updateCommunity(long communityNo, User user, CommunityDto communityDto, MultipartFile imageFile) {

        String imageUrl = null;
        if (imageFile != null) {
            imageUrl = s3Service.upload(imageFile);
        }

        try {
            Community community = Community.builder()
                    .title(communityDto.getTitle())
                    .description(communityDto.getDescription())
                    .image(imageUrl)
                    .manager(userRepository.findByNo(communityDto.getUserNo()).orElse(null))
                    .sido(communityDto.getSido())
                    .sigungu(communityDto.getSigungu())
                    .tag(communityDto.getTag())
                    .build();
            communityRepository.updateCommunity(communityNo, community);
            return community;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    @Override
    public void deleteCommunity(long communityNo, User user) throws Exception {

        Community community = communityRepository.findByNo(communityNo);
//        List<UserCommunity> userCommunityList = userCommunityRepository.findAllByCommunityNo(communityNo);

        // 커뮤니티 게시글 조회 >> 삭제
        // 댓글 삭제
        // 좋아요 중개 테이블 삭제


        if (community == null) {
            System.out.println("1번 에러");
            throw new Exception();
        }

        if (community.getManager().getNo() != user.getNo()) {
            System.out.println("2번 에러");
            throw new Exception();
        }

//        userCommunityRepository.deleteAll(userCommunityList);
        communityRepository.delete(community);

    }

    @Override
    public void quitCommunity(long communityNo, long userNo) throws Exception {
        UserCommunity userCommunity = userCommunityRepository.findAllByCommunityNoAndUserNo(communityNo, userNo);

        Community community = userCommunity.getCommunity();

//        Community community = communityRepository.findByNo(communityNo);

        if (userCommunity == null) {
            throw new Exception();
        }

        if (community.getManager().getNo() == userNo) {
            throw new Exception();
        }

        userCommunityRepository.delete(userCommunity);
    }

    @Override
    public List<Community> getMyCommunityList(long userNo) {
        return userCommunityRepository.findAllCommunityByUserNo(userNo);
    }

    @Override
    public List<Community> getPopularCommunityList() {
//        List<Long> communityNoList = userCommunityRepository.findTop5CommunityNo();
//        List<Community> communityList = communityRepository.findAllById(communityNoList);
        Pageable limitFive = PageRequest.of(0, 5);

        List<Community> communityList = userCommunityRepository.findTop5Community(limitFive);

        return communityList;
    }


}
