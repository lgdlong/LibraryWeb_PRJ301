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
    private final BookDao bookDao = new BookDao();

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


    public void checkAndUpdateOverdue() {
        List<BorrowRecordDTO> allBorrowed = getAllBorrowed(); // Chỉ lấy BORROWED

        if (allBorrowed == null || allBorrowed.isEmpty()) return;

        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        for (BorrowRecordDTO dto : allBorrowed) {
            if (dto.getDueDate() != null
                && dto.getDueDate().isBefore(now)) {
                // Nếu quá hạn, cập nhật trạng thái
                try {
                    updateStatus(dto.getId(), BorrowStatus.OVERDUE);
                } catch (Exception e) {
                    System.err.println("Failed to update overdue status for record " + dto.getId() + ": " + e.getMessage());
                }
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

    public List<BorrowRecordDTO> getBorrowHistoryByUserId(long userId) {
        if (userId <= 0) throw new IllegalArgumentException("Invalid user ID");

        List<BorrowRecord> history = borrowRecordDao.getBorrowHistoryByUserId(userId);

        if (history == null || history.isEmpty()) {
            return Collections.emptyList();
        }

        return history.stream()
            .map(BorrowRecordMapping::toBorrowRecordDTO)
            .collect(Collectors.toList());
    }
    public int sendBorrowRequest (long userId, List<Book> books){
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
        if (books == null || books.isEmpty()) {
            throw new IllegalArgumentException("Book list must not be empty.");
        }

        return borrowRecordDao.sendBorrowRequest(userId, books);
    }
 
public void addBorrowRecord(BorrowRecordDTO recordDTO) {
     if(recordDTO == null) throw new IllegalArgumentException("Record must not be null");
     long bookId = recordDTO.getBookId();
     
     BorrowRecord borrowRecord = BorrowRecordMapping.toBorrowRecord(recordDTO);
     borrowRecordDao.add(borrowRecord);
     
     bookDao.decreaseAvailableCopies(bookId);
}
public void approveBookRequest(BookRequest request) {
    if (request == null) {
        throw new IllegalArgumentException("Request is null");
    }

    // 1. Tạo DTO để ghi bản ghi mượn sách
    BorrowRecordDTO dto = new BorrowRecordDTO();
    dto.setUserId(request.getUserId());
    dto.setBookId(request.getBookId());
    dto.setBorrowDate(LocalDate.now());
    dto.setDueDate(LocalDate.now().plusDays(14));
    dto.setStatus("BORROWED");

    // 2. Ghi bản ghi mượn và trừ sách
    addBorrowRecord(dto);

    // 3. Cập nhật trạng thái yêu cầu mượn
    new BookRequestDao().updateStatus(request.getId(), "approved");
}

}
