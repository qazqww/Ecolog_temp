package com.thedebuggers.backend.service.community.post;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.CommentReqDto;
import com.thedebuggers.backend.dto.community.post.CommentResDto;

import java.util.List;

public interface CommentService {
    List<CommentResDto> getCommentList(long postNo);

    void registComment(long postNo, User user, CommentReqDto commentDto);

    CommentResDto getCommentByNo(long commentNo);

    void updateComment(long commentNo, CommentReqDto commentDto, User user);

    void deleteComment(long commentNo, User user);

    List<CommentResDto> getUserCommentsInCommunity(long communityNo, long userNo);
}
