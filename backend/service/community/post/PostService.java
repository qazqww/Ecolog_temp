package com.thedebuggers.backend.service.community.post;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.community.post.PostReqDto;
import com.thedebuggers.backend.dto.community.post.PostResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostResDto registPost(User user, PostReqDto postReqDto, long communityNo, MultipartFile imageFile);

    List<PostResDto> getAllPost();

    List<PostResDto> getPostList(long communityNo, String type);

    List<PostResDto> getMyPostList(long userNo);

    List<PostResDto> getMyPostListInCommunity(long communityNo, long userNo);

    PostResDto getPost(long userNo, long postNo);

    boolean modifyPost(long userNo, long postNo, PostReqDto postDto, MultipartFile imageFile);

    boolean deletePost(long userNo, long postNo);

    boolean likePost(long userNo, long postNo);

}
