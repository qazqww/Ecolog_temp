package com.thedebuggers.backend.domain.entity.community.campaign;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.community.Community;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "CAMPAIGN")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String title;
    private String content;
    private String image;
    private String location;

    private LocalDateTime start_date;
    private LocalDateTime end_date;

    private long max_personnel;

    @ManyToOne
    @JoinColumn(name = "community_no")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_CAMPAIGN",
        joinColumns = @JoinColumn(name = "campaign_no"),
        inverseJoinColumns = @JoinColumn(name = "user_no")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "campaign", cascade = {CascadeType.REMOVE})
    private Set<CampaignComment> comments = new HashSet<>();

}
