package com.thedebuggers.backend.domain.entity.community;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.community.campaign.Campaign;
import com.thedebuggers.backend.domain.entity.community.post.Post;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "COMMUNITY")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private long no;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = User.class)
    @JoinColumn(name = "manager", updatable = false)
    private User manager;

//    @Column(name = "manager")
//    private long manager;

    @Column(name = "image")
    private String image;

    @Column(name = "sido")
    private String sido;

    @Column(name = "sigungu")
    private String sigungu;

    @Column(name = "tag")
    private String tag;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_COMMUNITY",
        joinColumns = @JoinColumn(name = "community_no"),
        inverseJoinColumns = @JoinColumn(name = "user_no")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Campaign> campaigns = new HashSet<>();

}
