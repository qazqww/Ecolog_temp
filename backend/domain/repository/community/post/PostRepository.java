package com.thedebuggers.backend.domain.repository.community.post;

import com.thedebuggers.backend.domain.entity.community.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTypeAndIsOpenTrueOrderByNoDesc(int type);
    List<Post> findAllByCommunityNoAndTypeOrderByNoDesc(long communityNo, int type);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + :count where p.no = :postNo")
    void updateLike(long postNo, int count);

    List<Post> findAllByCommunityNoAndUserNo(long communityNo, long userNo);

    List<Post> findAllByUserNoAndIsOpenTrue(long userNo);
}
