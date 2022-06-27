package com.thedebuggers.backend.service.community.campaign;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.CommentReqDto;
import com.thedebuggers.backend.dto.community.post.CommentResDto;

import java.util.List;

public interface CampaignCommentService {
    List<CommentResDto> getCampaignCommentList(long campaignNo);

    void registCampaignComment(long campaignNo, User user, CommentReqDto commentDto);

    CommentResDto getCampaignCommentByNo(long campaignCommentNo);

    void updateCampaignComment(long campaignCommentNo, CommentReqDto commentDto, User user);

    void deleteCampaignComment(long campaignCommentNo, User user);

    List<CommentResDto> getUserCampaignCommentsInCommunity(long communityNo, long userNo);
}
