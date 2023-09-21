package org.example.benchmark.loom.data;

import javax.annotation.Nullable;

/**
 * @author 宋志宗 on 2023/8/23
 */
public class FixedTime {

    @Nullable
    private Long startTime;

    @Nullable
    private Long endTime;

    @Nullable
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(@Nullable Long startTime) {
        this.startTime = startTime;
    }

    @Nullable
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable Long endTime) {
        this.endTime = endTime;
    }
}
