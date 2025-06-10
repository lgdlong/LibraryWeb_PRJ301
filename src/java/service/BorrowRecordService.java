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

    public BorrowRecordDTO getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Record ID must be positive");
        }
        BorrowRecord record = borrowRecordDao.getById(id);
        return BorrowRecordMapping.toBorrowRecordDTO(record);
    }

    public long countCurrentlyBorrowedBooks() {
        return borrowRecordDao.countCurrentlyBorrowed();
    }

    public List<MostBorrowedBookDTO> getMostBorrowedBooks() {
        List<Map.Entry<Long, Long>> entries = borrowRecordDao.getMostBorrowedBooks();
        if (entries == null || entries.isEmpty()) {
            return Collections.emptyList();
        }
        return entries.stream()
            .map(BorrowRecordMapping::toMostBorrowedBookDTO)
            .collect(Collectors.toList());
    }
}
