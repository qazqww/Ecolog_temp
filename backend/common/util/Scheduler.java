package com.thedebuggers.backend.common.util;

import com.thedebuggers.backend.service.plogging.PloggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PloggingService ploggingService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void rankingRewardByMonth() {
        ploggingService.rankingRewardByMonth();
    }

    @Scheduled(cron = "0 0 0 * * 0")
    public void rankingRewardByWeek() {
        ploggingService.rankingRewardByWeek();
    }

}
