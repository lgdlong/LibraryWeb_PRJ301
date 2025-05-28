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
}
