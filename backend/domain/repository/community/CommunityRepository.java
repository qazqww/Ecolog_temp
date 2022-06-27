package com.thedebuggers.backend.domain.repository.community;

import com.thedebuggers.backend.domain.entity.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findAllByOrderByNoDesc();

    Community findByNo(long no);

    @Modifying
    @Transactional
    @Query("update Community c set c.title = :#{#community.title}," +
            "c.description = :#{#community.description}, c.image = :#{#community.image}," +
            "c.manager = :#{#community.manager}, c.sido = :#{#community.sido}," +
            "c.sigungu = :#{#community.sigungu}, c.tag = :#{#community.tag} where c.no = :#{#communityNo}")
    void updateCommunity(long communityNo, Community community);
}
