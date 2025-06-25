package service;

import dao.*;
import dto.*;
import entity.*;
import enums.*;
import mapper.*;

import java.time.*;
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

    public List<BorrowRecordDTO> getAllBorrowed() {
        List<BorrowRecord> records = borrowRecordDao.getAllBorrowed();
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        return records.stream()
            .map(BorrowRecordMapping::toBorrowRecordDTO)
            .collect(Collectors.toList());
    }

    public List<BorrowRecordDTO> getAllOverdue() {
        List<BorrowRecord> overdueRecords = borrowRecordDao.getAllOverdue();
        if (overdueRecords == null || overdueRecords.isEmpty()) {
            return Collections.emptyList();
        }
        return overdueRecords.stream()
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

    public void addBorrowRecord(BorrowRecordDTO recordDTO) {
        //
    }

    public void checkAndUpdateOverdue() {
        List<BorrowRecordDTO> allBorrowed = getAllBorrowed(); // Chỉ lấy BORROWED

        if (allBorrowed == null || allBorrowed.isEmpty()) return;

        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        for (BorrowRecordDTO dto : allBorrowed) {
            if (BorrowStatus.BORROWED.toString().equals(dto.getStatus())
                && dto.getDueDate() != null
                && dto.getDueDate().isBefore(now)) {
                // Nếu quá hạn, cập nhật trạng thái
                updateStatus(dto.getId(), BorrowStatus.OVERDUE);
            }
        }
    }

    public void updateStatus(long id, BorrowStatus newStatus) {
        if (id <= 0) throw new IllegalArgumentException("Record ID must be positive");
        if (newStatus == null) throw new IllegalArgumentException("Status must not be null");

        BorrowRecord record = borrowRecordDao.getById(id);
        if (record == null) throw new NoSuchElementException("No record found with ID: " + id);

        // Cập nhật cả trên entity và DB
        record.setStatus(newStatus);
        borrowRecordDao.updateStatus(id, newStatus);
    }

}
