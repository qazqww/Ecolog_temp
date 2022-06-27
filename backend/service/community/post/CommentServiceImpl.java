package com.thedebuggers.backend.service.community.post;

import com.thedebuggers.backend.common.exception.CustomException;
import com.thedebuggers.backend.common.util.ErrorCode;
import com.thedebuggers.backend.domain.entity.community.post.Comment;
import com.thedebuggers.backend.domain.entity.community.post.Post;
import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.domain.repository.community.post.CommentRepository;
import com.thedebuggers.backend.domain.repository.community.post.PostRepository;
import com.thedebuggers.backend.dto.community.post.CommentReqDto;
import com.thedebuggers.backend.dto.community.post.CommentResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<CommentResDto> getCommentList(long postNo) {

        return commentRepository.findByPostNo(postNo).stream().map(CommentResDto::of).collect(Collectors.toList());
    }

    @Override
    public void registComment(long postNo, User user, CommentReqDto commentDto) {
        Post post = postRepository.findById(postNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(commentDto.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Override
    public CommentResDto getCommentByNo(long commentNo) {
        Comment comment = getComment(commentNo);
        return CommentResDto.of(comment);
    }

    @Override
    public void updateComment(long commentNo, CommentReqDto commentDto, User user) {

        Comment comment = getComment(commentNo);

        validateCommentOwner(user, comment);

        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long commentNo, User user) {

        Comment comment = getComment(commentNo);

        validateCommentOwner(user, comment);

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResDto> getUserCommentsInCommunity(long communityNo, long userNo) {
        return commentRepository.getUserCommentsInCommunity(communityNo, userNo).stream().map(CommentResDto::of).collect(Collectors.toList());
    }

    private Comment getComment(long commentNo) {
        return commentRepository.findByNo(commentNo).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private void validateCommentOwner(User user, Comment comment) {
        if (comment.getUser().getNo() != user.getNo()) throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
    }
}
