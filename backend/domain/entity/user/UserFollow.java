package com.thedebuggers.backend.domain.entity.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@Table(name = "USER_FOLLOW")
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long no;

    @ManyToOne
    @JoinColumn(name = "from_user_no")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "to_user_no")
    private User followee;

}
