package com.thedebuggers.backend.domain.entity.user;

import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.community.campaign.CampaignComment;
import com.thedebuggers.backend.domain.entity.community.post.Comment;
import com.thedebuggers.backend.domain.entity.community.post.Post;
import com.thedebuggers.backend.domain.entity.community.post.PostLike;
import com.thedebuggers.backend.domain.entity.plogging.Plogging;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String birth;

    private double height;

    private double weight;

    private String phone;

    private String image;

    private String address;

    private int coin = 700;

    private long avatar;

    private long room;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();

    @OneToMany(mappedBy = "followee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> follower = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserAsset> assets = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserCampaign> campaigns = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostLike> postLikes = new HashSet<>();

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Community> communities = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CampaignComment> campaignComments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Plogging> ploggings = new HashSet<>();
}
