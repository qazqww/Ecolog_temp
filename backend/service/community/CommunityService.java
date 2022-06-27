package com.thedebuggers.backend.service.community;

import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.CommunityDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityService {
    List<Community> getCommunityList();

    Community getCommunity(long no);

    Community registCommunity(CommunityDto communityResDto, long userNo, MultipartFile imageFile);

    Community joinCommunity(long no, User user);

    List<User> getCommunityMember(long no);

    Community updateCommunity(long communityNo, User user, CommunityDto communityDto, MultipartFile imageFile);

    void deleteCommunity(long communityNo, User user) throws Exception;

    void quitCommunity(long communityNo, long userNo) throws Exception;

    List<Community> getMyCommunityList(long userNo);

    List<Community> getPopularCommunityList();

    long getCommunityMemberCount(long communityNo);

    boolean checkCommunityUser(long userNo, long communityNo);
}
