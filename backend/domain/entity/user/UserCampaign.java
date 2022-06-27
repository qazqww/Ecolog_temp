package com.thedebuggers.backend.domain.entity.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.community.campaign.Campaign;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name="USER_CAMPAIGN")
public class UserCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    long no;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "campaign_no")
    private Campaign campaign;

}
