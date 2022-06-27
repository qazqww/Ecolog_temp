package com.thedebuggers.backend.domain.entity.plogging;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;
import org.locationtech.jts.geom.Point;


import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "TRASH_CAN")
public class TrashCan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private String address;
    private String image;
    private Point location;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

}
