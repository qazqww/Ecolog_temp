package com.thedebuggers.backend.service.community.campaign;

import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.ErrorCode;
import com.thedebuggers.backend.domain.entity.community.campaign.Campaign;
import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserCampaign;
import com.thedebuggers.backend.domain.repository.community.campaign.CampaignRespository;
import com.thedebuggers.backend.domain.repository.user.UserCampaignRepository;
import com.thedebuggers.backend.domain.repository.user.UserRepository;
import com.thedebuggers.backend.dto.community.campaign.CampaignReqDto;
import com.thedebuggers.backend.dto.community.campaign.CampaignResDto;
import com.thedebuggers.backend.service.community.CommunityService;
import com.thedebuggers.backend.common.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CampaignServiceImpl implements CampaignService {

    private final CommunityService communityService;

    private final CampaignRespository campaignRespository;
    private final UserCampaignRepository userCampaignRepository;

    private final S3Service s3Service;


    @Override
    public CampaignResDto registCampaign(CampaignReqDto campaignReqDto, long communityNo, User user, MultipartFile imageFile) {

        Community community = communityService.getCommunity(communityNo);

        if (community == null) throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);

        String imageUrl = null;

        if (imageFile != null) {
            imageUrl = s3Service.upload(imageFile);
        }

        Campaign campaign = Campaign.builder()
                .title(campaignReqDto.getTitle())
                .content(campaignReqDto.getContent())
                .image(imageUrl)
                .start_date(campaignReqDto.getStart_date())
                .end_date(campaignReqDto.getEnd_date())
                .max_personnel(campaignReqDto.getMax_personnel())
                .location(campaignReqDto.getLocation())
                .community(community)
                .user(user)
                .build();

        campaign = campaignRespository.save(campaign);

        UserCampaign userCampaign = UserCampaign.builder()
                .user(user)
                .campaign(campaign)
                .build();

        userCampaignRepository.save(userCampaign);

        List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaign.getNo());

        CampaignResDto campaignResDto = CampaignResDto.of(campaign, userList, isUserJoiningInCampaign(campaign.getNo(), user.getNo()));

        return campaignResDto;
    }

    @Override
    public List<CampaignResDto> getCampaignList(long communityNo, User user) {
        List<Campaign> campaignList = campaignRespository.findAllByCommunityNo(communityNo);

        List<CampaignResDto> result = campaignList.stream().map(campaign -> {
            List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaign.getNo());
            return CampaignResDto.of(campaign, userList, isUserJoiningInCampaign(campaign.getNo(), user.getNo()));
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<User> getCampaignMember(long campaignNo) {
        List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaignNo);
        return userList;
    }

    @Override
    public CampaignResDto getCampaign(long campaignNo, User user) {
        Campaign campaign = campaignRespository.findByNo(campaignNo).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaignNo);

        return CampaignResDto.of(campaign, userList, isUserJoiningInCampaign(campaign.getNo(), user.getNo()));
    }

    @Override
    public CampaignResDto joinCampaign(long campaignNo, User user) {
        Campaign campaign = campaignRespository.findByNo(campaignNo).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        UserCampaign existUserCampaign = userCampaignRepository.findByCampaignNoAndUserNo(campaign.getNo(), user.getNo());
        if (existUserCampaign == null) {
            UserCampaign userCampaign = UserCampaign.builder()
                    .campaign(campaign)
                    .user(user)
                    .build();
            userCampaignRepository.save(userCampaign);
        } else {
            long existNo = existUserCampaign.getNo();
            userCampaignRepository.deleteById(existNo);
        }
        List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaignNo);

        return CampaignResDto.of(campaign, userList, isUserJoiningInCampaign(campaign.getNo(), user.getNo()));
    }

    @Override
    public CampaignResDto updateCampaign(CampaignReqDto campaignReqDto, long campaignNo, User user, MultipartFile imageFile) {
        Campaign campaign = campaignRespository.findByNo(campaignNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (campaign.getUser().getNo() != user.getNo()) throw new CustomException(ErrorCode.CONTENT_UNAUTHORIZED);

        String imageUrl = null;

        if (imageFile != null) {
            imageUrl = s3Service.upload(imageFile);
        }


        campaign.setTitle(campaignReqDto.getTitle());
        campaign.setContent(campaignReqDto.getContent());
        campaign.setImage(imageUrl);
        campaign.setLocation(campaignReqDto.getLocation());
        campaign.setStart_date(campaignReqDto.getStart_date());
        campaign.setEnd_date(campaignReqDto.getEnd_date());
        campaign.setMax_personnel(campaignReqDto.getMax_personnel());

        campaign = campaignRespository.save(campaign);

        List<User> userList = userCampaignRepository.findAllUserByCampaignNo(campaignNo);

        return CampaignResDto.of(campaign, userList, isUserJoiningInCampaign(campaign.getNo(), user.getNo()));

    }

    @Override
    public boolean deleteCampaign(User user, long campaignNo) {
        Campaign campaign = campaignRespository.findByNo(campaignNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (campaign.getUser().getNo() != user.getNo()) throw new CustomException(ErrorCode.CONTENT_UNAUTHORIZED);

        campaignRespository.delete(campaign);


        return true;
    }

    private boolean isUserJoiningInCampaign(long campaignNo, long userNo) {
        return userCampaignRepository.existsByCampaignNoAndUserNo(campaignNo, userNo);
    }
}
