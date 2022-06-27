package com.thedebuggers.backend.domain.repository.community.post;

import com.thedebuggers.backend.domain.entity.community.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostNo(@Param(value = "no") long postNo);

    Optional<Comment> findByNo(long commentNo);

    @Query("select c from Comment c where c.user.no = :userNo and c.post.community.no = :communityNo")
    List<Comment> getUserCommentsInCommunity(long communityNo, long userNo);
}
