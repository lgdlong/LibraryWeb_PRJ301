package service;

import dao.*;
import db.*;
import dto.*;
import entity.*;
import enums.*;
import mapper.*;

import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class BorrowRecordService {
    private final BorrowRecordDao borrowRecordDao = new BorrowRecordDao();
    private final BookDao bookDao = new BookDao();
    private final FineDao fineDao = new FineDao();

    public void addNewBorrowRecord(Connection conn, BorrowRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("BorrowRecord must not be null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("Connection must not be null");
        }
        borrowRecordDao.add(conn, record);
    }

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

    public List<BorrowRecordDTO> getOverdueByUserId(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        List<BorrowRecord> overdueRecords = borrowRecordDao.getOverdueByUserId(userId);
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

    // Tracking statistics class for overdue processing
    public static class OverdueProcessingResult {
        private final int totalChecked;
        private final int updatedToOverdue;
        private final int finesCreated;
        private final int errorCount;
        private final LocalDateTime processTime;
        private final List<String> errors;

        public OverdueProcessingResult(int totalChecked, int updatedToOverdue, int finesCreated, int errorCount, LocalDateTime processTime, List<String> errors) {
            this.totalChecked = totalChecked;
            this.updatedToOverdue = updatedToOverdue;
            this.finesCreated = finesCreated;
            this.errorCount = errorCount;
            this.processTime = processTime;
            this.errors = new ArrayList<>(errors);
        }

        // Getters
        public int getTotalChecked() {
            return totalChecked;
        }

        public int getUpdatedToOverdue() {
            return updatedToOverdue;
        }

        public int getFinesCreated() {
            return finesCreated;
        }

        public int getErrorCount() {
            return errorCount;
        }

        public LocalDateTime getProcessTime() {
            return processTime;
        }

        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }

        @Override
        public String toString() {
            return String.format("OverdueProcessing[checked=%d, updated=%d, fines=%d, errors=%d, time=%s]",
                totalChecked, updatedToOverdue, finesCreated, errorCount, processTime);
        }
    }

    // Enhanced overdue checking with detailed tracking and logging
    public OverdueProcessingResult checkAndUpdateOverdueWithTracking() {
        LocalDateTime startTime = LocalDateTime.now();
        List<BorrowRecordDTO> allBorrowed = getAllBorrowed(); // Only get BORROWED records

        int totalChecked = 0;
        int updatedToOverdue = 0;
        int finesCreated = 0;
        int errorCount = 0;
        List<String> errors = new ArrayList<>();

        if (allBorrowed == null || allBorrowed.isEmpty()) {
            System.out.println("No borrowed records found for overdue processing at " + startTime);
            return new OverdueProcessingResult(0, 0, 0, 0, startTime, errors);
        }

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        FineService fineService = new FineService();

        System.out.println("Starting overdue processing at " + startTime + " for " + allBorrowed.size() + " borrowed records");

        for (BorrowRecordDTO dto : allBorrowed) {
            totalChecked++;

            try {
                if (dto.getDueDate() != null && dto.getDueDate().isBefore(now)) {
                    // Record is overdue
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dto.getDueDate(), now);
                    System.out.println("Processing overdue record ID: " + dto.getId() +
                        " (due: " + dto.getDueDate() + ", days overdue: " + daysOverdue + ")");

                    // 1. Update status to OVERDUE
                    updateStatus(dto.getId(), BorrowStatus.OVERDUE);
                    updatedToOverdue++;

                    // 2. Create fine if not exists
                    dto.setStatus(BorrowStatus.OVERDUE.toString());

                    // Check if fine already exists before creating
                    Fine existingFine = fineDao.getByBorrowRecordId(dto.getId());
                    if (existingFine == null) {
                        fineService.createFineForOverdue(dto);
                        finesCreated++;
                        System.out.println("Created fine for overdue record ID: " + dto.getId());
                    } else {
                        System.out.println("Fine already exists for record ID: " + dto.getId());
                    }
                }
            } catch (Exception e) {
                errorCount++;
                String errorMsg = "Failed to process record " + dto.getId() + ": " + e.getMessage();
                errors.add(errorMsg);
                System.err.println(errorMsg);
                // Don't print full stack trace in production, just log the message
            }
        }

        OverdueProcessingResult result = new OverdueProcessingResult(
            totalChecked, updatedToOverdue, finesCreated, errorCount, startTime, errors);

        System.out.println("Completed overdue processing: " + result);
        return result;
    }

    public void checkAndUpdateOverdue() {
        List<BorrowRecordDTO> allBorrowed = getAllBorrowed(); // Chỉ lấy BORROWED

        if (allBorrowed == null || allBorrowed.isEmpty()) return;

        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        FineService fineService = new FineService();

        for (BorrowRecordDTO dto : allBorrowed) {
            if (dto.getDueDate() != null
                && dto.getDueDate().isBefore(now)) {
                // Nếu quá hạn, cập nhật trạng thái và tạo phạt
                try {
                    // 1. Cập nhật trạng thái thành OVERDUE
                    updateStatus(dto.getId(), BorrowStatus.OVERDUE);

                    // 2. Tạo phạt cho bản ghi quá hạn (nếu chưa có)
                    // Cập nhật status của DTO để phản ánh trạng thái mới
                    dto.setStatus(BorrowStatus.OVERDUE.toString());
                    fineService.createFineForOverdue(dto);

                } catch (Exception e) {
                    System.err.println("Failed to update overdue status and create fine for record " + dto.getId() + ": " + e.getMessage());
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

    public void returnBorrowedRecord(BorrowRecordDTO recordDTO, boolean isPaid) {
        Connection conn = null;
        try {
            conn = DbConfig.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Kiểm tra đầu vào
            if (recordDTO == null) {
                throw new IllegalArgumentException("BorrowRecordDTO must not be null");
            }

            BorrowRecord record = BorrowRecordMapping.toBorrowRecord(recordDTO);
            if (record == null) {
                throw new IllegalArgumentException("BorrowRecord mapping failed");
            }

            // 2. Nếu record bị quá hạn và đã trả phạt, cập nhật tình trạng paid cho Fine
            boolean wasOverdue = BorrowStatus.OVERDUE.toString().equals(recordDTO.getStatus());
            if (wasOverdue) {
                // Cập nhật trạng thái fine nếu có
                Fine fine = fineDao.getByBorrowRecordId(conn, record.getId());
                if (fine != null && !fine.isPaid() && isPaid) {
                    fine.markAsPaid(); // cập nhật trường paid, ngày thanh toán, v.v.
                    fineDao.update(conn, fine);
                }
            }

            // 3. mark record's status to RETURNED and set return date is now
            record.markAsReturned();

            // 4. update the record in DB
            borrowRecordDao.update(conn, record);

            // 5. increase the book's available count
            bookDao.increaseBookAvailable(conn, record.getBookId());

            conn.commit();
            System.out.println("Borrow record (ID: " + record.getId() + ") has been marked as returned. Book available increased.");
        } catch (Exception e) {
            // Nếu bất kỳ lỗi nào thì rollback
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public List<BorrowRecordDTO> getBorrowHistoryByUserId(long userId) {
        if (userId <= 0) throw new IllegalArgumentException("Invalid user ID");

        List<BorrowRecord> history = borrowRecordDao.getBorrowHistoryByUserId(userId);

        System.out.println("Borrow history for user ID " + userId + ":");
        history.forEach(System.out::println); // Debugging line to check history

        if (history == null || history.isEmpty()) {
            return Collections.emptyList();
        }

        return history.stream()
            .map(BorrowRecordMapping::toBorrowRecordDTO)
            .collect(Collectors.toList());
    }

    public int sendBorrowRequest(long userId, List<Book> books) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
        if (books == null || books.isEmpty()) {
            throw new IllegalArgumentException("Book list must not be empty.");
        }

        return borrowRecordDao.sendBorrowRequest(userId, books);
    }

    // Get statistics about overdue records
    public Map<String, Object> getOverdueStatistics() {
        Map<String, Object> stats = new HashMap<>();

        try {
            List<BorrowRecordDTO> allOverdue = getAllOverdue();
            List<BorrowRecordDTO> allBorrowed = getAllBorrowed();

            // Count overdue by days
            LocalDate now = LocalDate.now();
            long recentOverdue = 0; // 1-7 days
            long moderateOverdue = 0; // 8-30 days
            long severeOverdue = 0; // 30+ days

            for (BorrowRecordDTO record : allOverdue) {
                if (record.getDueDate() != null) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), now);
                    if (daysOverdue <= 7) {
                        recentOverdue++;
                    } else if (daysOverdue <= 30) {
                        moderateOverdue++;
                    } else {
                        severeOverdue++;
                    }
                }
            }

            stats.put("totalOverdue", allOverdue.size());
            stats.put("totalBorrowed", allBorrowed.size());
            stats.put("recentOverdue", recentOverdue); // 1-7 days
            stats.put("moderateOverdue", moderateOverdue); // 8-30 days
            stats.put("severeOverdue", severeOverdue); // 30+ days
            stats.put("generatedAt", LocalDateTime.now());

        } catch (Exception e) {
            System.err.println("Error generating overdue statistics: " + e.getMessage());
            stats.put("error", e.getMessage());
        }

        return stats;
    }

    // Get records that are approaching due date (for proactive management)
    public List<BorrowRecordDTO> getRecordsApproachingDueDate(int daysBeforeDue) {
        if (daysBeforeDue < 1) {
            throw new IllegalArgumentException("Days before due must be at least 1");
        }

        List<BorrowRecordDTO> allBorrowed = getAllBorrowed();
        if (allBorrowed == null || allBorrowed.isEmpty()) {
            return Collections.emptyList();
        }

        LocalDate threshold = LocalDate.now().plusDays(daysBeforeDue);

        return allBorrowed.stream()
            .filter(record -> record.getDueDate() != null)
            .filter(record -> !record.getDueDate().isAfter(threshold))
            .filter(record -> record.getDueDate().isAfter(LocalDate.now())) // Not yet overdue
            .sorted((r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))
            .collect(Collectors.toList());
    }
}
