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
        List<BorrowRecord> records = borrowRecordDao.getAll();
        return records.stream()
            .map(BorrowRecordMapping::toBorrowRecordDTO)
            .collect(Collectors.toList());
    }

    public BorrowRecordDTO getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Record ID must be positive");
        }
        BorrowRecord record = borrowRecordDao.getById(id);
        return BorrowRecordMapping.toBorrowRecordDTO(record);
    }
}
