package org.example.benchmark.loom.data;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 宋志宗 on 2023/8/17
 */
public class AlertRule {

    @Nullable
    private AlertType alertType = null;

    @Nullable
    private DataSourceType dataSourceType = null;

    @Nullable
    private String name = null;

    @Nullable
    private String note = null;

    private boolean allDataSource;

    @Nullable
    private Set<Long> dataSourceIds = null;

    private boolean enabled = true;

    @Nullable
    private List<PeriodicTime> effectiveTimes = null;

    @Nullable
    private Integer frequency = null;

    @Nullable
    private Integer pendingDuration = null;

    @Nullable
    private Integer stayDuration = null;

    @Nullable
    private Map<String, String> additionalLabels = null;

    @Nullable
    private Set<String> callbacks = null;

    @Nullable
    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(@Nullable AlertType alertType) {
        this.alertType = alertType;
    }

    @Nullable
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(@Nullable DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    public void setNote(@Nullable String note) {
        this.note = note;
    }

    public boolean isAllDataSource() {
        return allDataSource;
    }

    public void setAllDataSource(boolean allDataSource) {
        this.allDataSource = allDataSource;
    }

    @Nullable
    public Set<Long> getDataSourceIds() {
        return dataSourceIds;
    }

    public void setDataSourceIds(@Nullable Set<Long> dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Nullable
    public List<PeriodicTime> getEffectiveTimes() {
        return effectiveTimes;
    }

    public void setEffectiveTimes(@Nullable List<PeriodicTime> effectiveTimes) {
        this.effectiveTimes = effectiveTimes;
    }

    @Nullable
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(@Nullable Integer frequency) {
        this.frequency = frequency;
    }

    @Nullable
    public Integer getPendingDuration() {
        return pendingDuration;
    }

    public void setPendingDuration(@Nullable Integer pendingDuration) {
        this.pendingDuration = pendingDuration;
    }

    @Nullable
    public Integer getStayDuration() {
        return stayDuration;
    }

    public void setStayDuration(@Nullable Integer stayDuration) {
        this.stayDuration = stayDuration;
    }

    @Nullable
    public Map<String, String> getAdditionalLabels() {
        return additionalLabels;
    }

    public void setAdditionalLabels(@Nullable Map<String, String> additionalLabels) {
        this.additionalLabels = additionalLabels;
    }

    @Nullable
    public Set<String> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(@Nullable Set<String> callbacks) {
        this.callbacks = callbacks;
    }
}
