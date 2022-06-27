package com.thedebuggers.backend.domain.entity.user;

import com.thedebuggers.backend.domain.entity.asset.Asset;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_no")
    private Asset asset;
}
