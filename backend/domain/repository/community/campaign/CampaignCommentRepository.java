package com.thedebuggers.backend.domain.repository.community.campaign;

import com.thedebuggers.backend.domain.entity.community.campaign.CampaignComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CampaignCommentRepository extends JpaRepository<CampaignComment, Long> {

    List<CampaignComment> findByCampaignNo(@Param(value = "no") long campaignNo);

    Optional<CampaignComment> findByNo(long commentNo);

    @Query("select cc from CampaignComment cc where cc.user.no = :userNo and cc.campaign.community.no = :communityNo")
    List<CampaignComment> getUserCampaignCommentsInCommunity(long communityNo, long userNo);
}
