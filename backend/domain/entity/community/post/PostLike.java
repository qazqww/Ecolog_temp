package com.thedebuggers.backend.domain.entity.community.post;

import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long no;

    @ManyToOne
    @JoinColumn(name = "user_no")
    User user;

    @ManyToOne
    @JoinColumn(name = "post_no")
    Post post;
}
