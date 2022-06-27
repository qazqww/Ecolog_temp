package com.thedebuggers.backend.service.community.campaign;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.campaign.CampaignReqDto;
import com.thedebuggers.backend.dto.community.campaign.CampaignResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CampaignService {
    CampaignResDto registCampaign(CampaignReqDto campaignReqDto, long communityNo, User user, MultipartFile imageFile);

    List<CampaignResDto> getCampaignList(long communityNo, User user);

    List<User> getCampaignMember(long campaignNo);

    CampaignResDto getCampaign(long campaignNo, User user);

    CampaignResDto joinCampaign(long campaignNo, User user);

    CampaignResDto updateCampaign(CampaignReqDto campaignReqDto, long campaignNo, User user, MultipartFile imageFile);

    boolean deleteCampaign(User user, long campaignNo);
}
