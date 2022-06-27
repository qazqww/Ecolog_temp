package com.thedebuggers.backend.domain.entity.community.campaign;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CampaignComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "campaign_no")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

}
