package com.thedebuggers.backend.domain.repository.user;

import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.entity.user.UserCommunity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommunityRepository extends JpaRepository<UserCommunity, Long> {
    @Query("select uc.user from UserCommunity uc where uc.community.no = :communityNo")
    List<User> findAllUserByCommunityNo(long communityNo);


    List<UserCommunity> findAllByCommunityNo(long communityNo);

    UserCommunity findAllByCommunityNoAndUserNo(long communityNo, long userNo);

    @Query("select uc.community from UserCommunity  uc where uc.user.no = :userNo")
    List<Community> findAllCommunityByUserNo(long userNo);

    @Query("select uc.community from UserCommunity uc group by uc.community order by count(uc.user) desc")
    List<Community> findTop5Community(Pageable pageable);

    @Query("select count(uc.user) from UserCommunity uc where uc.community.no = :communityNo")
    long findCommunityCountByCommunityNo(long communityNo);

    UserCommunity findByCommunityNoAndUserNo(long communityNo, long userNo);
}
