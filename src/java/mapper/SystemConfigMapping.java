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
            // Có thể ghi log ở đây hoặc throw nếu cần cứng rắn
        }

        return new SystemConfig(
            db.getId(),
            db.getConfigKey(),
            configValueDouble,
            db.getDescription()
        );
    }
}
