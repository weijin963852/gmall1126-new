package com.atguigu.gmall.common.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "app.thread")
public class ThreadPoolPoperties {

    private Integer corePoolSize = 4;
    private Integer maxPoolSize = 8;
    private Long keepAliveTime = 30l;
    private Integer capacity = 20;

}
