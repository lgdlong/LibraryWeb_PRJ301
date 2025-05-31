package service;

import dao.*;
import dto.*;
import entity.*;
import mapper.*;

import java.util.*;
import java.util.stream.*;

public class BorrowRecordService {
    private final BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

    public List<BorrowRecordDTO> getAll() {
        List<BorrowRecord> records;
        try {
            records = borrowRecordDao.getAll();
            if (records == null || records.isEmpty()) {
                return new ArrayList<>();
            }
            return records.stream()
                .map(BorrowRecordMapping::toBorrowRecordDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the error appropriately
            e.printStackTrace();
        }
        return null;
    }

}
