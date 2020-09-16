package com.github.wechat.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时服务
 */
@Component
public class Task {

    @Scheduled(cron ="0 0 22,23 * * ?")
    private void process(){

    }

}
