package mapper;

import dto.*;
import entity.*;

public class SystemConfigMapping {

    public static SystemConfig toSystemConfig(SystemConfigDb db) {
        if (db == null) {
            return null;
        }

        double configValueDouble = 0.0;

        try {
            configValueDouble = Double.parseDouble(db.getConfigValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Optional: log warning
        }

        return new SystemConfig(
            db.getId(),
            db.getConfigKey(),
            configValueDouble,
            db.getDescription()
        );
    }

    public static SystemConfigDb toDb(SystemConfig config) {
        if (config == null) {
            return null;
        }

        return new SystemConfigDb(
            config.getId(),
            config.getConfigKey(),
            String.valueOf(config.getConfigValue()), // convert double to String
            config.getDescription()
        );
    }
}
