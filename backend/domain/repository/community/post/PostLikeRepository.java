package com.thedebuggers.backend.domain.repository.community.post;

import com.thedebuggers.backend.domain.entity.community.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostNoAndUserNo(long postNo, long userNo);

    void deleteByPostNoAndUserNo(long postNo, long userNo);
}