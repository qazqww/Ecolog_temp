package com.thedebuggers.backend.domain.repository.user;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCampaignRepository extends JpaRepository<UserCampaign, Long> {


    @Query("select uc.user from UserCampaign uc where uc.campaign.no = :campaignNo")
    List<User> findAllUserByCampaignNo(long campaignNo);

    UserCampaign findByCampaignNoAndUserNo(long campaignNo, long no);

    boolean existsByCampaignNoAndUserNo(long campaignNo, long userNo);
}
