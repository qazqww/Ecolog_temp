package com.thedebuggers.backend.domain.repository.community.campaign;

import com.thedebuggers.backend.domain.entity.community.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignRespository extends JpaRepository<Campaign, Long> {

    Optional<Campaign> findByNo(long campaignNo);

    List<Campaign> findAllByCommunityNo(long communityNo);
}
