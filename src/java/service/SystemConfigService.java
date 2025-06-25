package service;

import dao.*;
import dto.*;
import entity.*;
import mapper.*;

import java.util.*;
import java.util.stream.*;

public class SystemConfigService {
    private final SystemConfigDao systemConfigDao = new SystemConfigDao();

    public List<SystemConfig> getAllConfigs() {
        List<SystemConfigDb> systemConfigsDb = systemConfigDao.getAll();

        return systemConfigsDb.stream()
            .map(SystemConfigMapping::toSystemConfig)
            .collect(Collectors.toList());
    }

    public SystemConfig getConfigByConfigKey(String configKey) {
        if (configKey == null || configKey.isEmpty()) {
            throw new IllegalArgumentException("Config key cannot be null or empty");
        }
        SystemConfigDb configDb = systemConfigDao.getByConfigKey(configKey);
        return SystemConfigMapping.toSystemConfig(configDb);
    }


    public void add(SystemConfigDb config) {
        SystemConfig db = SystemConfigMapping.toSystemConfig(config);
        systemConfigDao.add(db);
    }

    public void update(SystemConfigDb config) {
        SystemConfig db = SystemConfigMapping.toSystemConfig(config);
        systemConfigDao.update(db);
    }

    public void delete(long id) {
        systemConfigDao.delete(id);
    }
}
