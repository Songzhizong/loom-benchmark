package org.example.benchmark.loom.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Set;

/**
 * 周期时间配置
 *
 * @author 宋志宗 on 2023/4/20
 */
public class PeriodicTime {

    @Nullable
    private Set<Integer> daysOfWeek;

    @Nullable
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Nullable
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Nullable
    public Set<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(@Nullable Set<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Nullable
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@Nullable LocalTime startTime) {
        this.startTime = startTime;
    }

    @Nullable
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@Nullable LocalTime endTime) {
        this.endTime = endTime;
    }
}
