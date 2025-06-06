package entity;

public class SystemConfig {
    private long id;
    private String configKey;
    private double configValue;
    private String description;

    public SystemConfig() {
    }

    public SystemConfig(String configKey, double configValue, String description) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
    }

    public SystemConfig(long id, String configKey, double configValue, String description) {
        this.id = id;
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public double getConfigValue() {
        return configValue;
    }

    public void setConfigValue(double configValue) {
        this.configValue = configValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
