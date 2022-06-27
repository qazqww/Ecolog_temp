package com.thedebuggers.backend.domain.entity.plogging;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thedebuggers.backend.domain.entity.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Plogging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedAt;


    private String resultImg;
    private String routeImg;
    private int time;
    private double distance;
    private double calories;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
}
