package service;

import dao.*;
import dto.*;
import entity.*;
import enums.*;
import mapper.*;

import java.util.*;
import java.util.stream.*;

public class FineService {
    private final FineDao fineDao = new FineDao();
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    public List<FineDTO> getAllFines() {
        try {
            List<Fine> requests = fineDao.getAll();

            if (requests.isEmpty()) {
                return Collections.emptyList();
            }

            return requests.stream()
                .map(FineMapping::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the error appropriately
            e.printStackTrace();
        }
        return null;
    }


    public FineDTO getFineById(long id) {
        Fine fine = fineDao.getById(id);
        return FineMapping.toDTO(fine);
    }

    public FineDTO getFineByBorrowRecordId(long borrowRecordId) {
        if (borrowRecordId <= 0) {
            throw new IllegalArgumentException("Borrow record ID must be positive");
        }
        Fine fine = fineDao.getByBorrowRecordId(borrowRecordId);
        return FineMapping.toDTO(fine);
    }

    public void updateFine(FineDTO fine) {
        fineDao.update(FineMapping.toEntity(fine));
    }

    public void addFine(FineDTO fine) {
        fineDao.add(FineMapping.toEntity(fine));
    }

    public void deleteFine(long id) {
        fineDao.delete(id);
    }

    public long countUnpaidFines() {
        return fineDao.countUnpaidFines();
    }

    public void processOverdueFines() {
        // Lấy danh sách overdue
        List<BorrowRecordDTO> overdueRecords = borrowRecordService.getAllOverdue();

        for (BorrowRecordDTO record : overdueRecords) {
            FineDTO existingFine = getFineByBorrowRecordId(record.getId());
            double fineAmount = calculateFine(record);

            if (existingFine == null) {
                FineDTO fine = new FineDTO();
                fine.setBorrowRecordId(record.getId());
                fine.setFineAmount(fineAmount);
                fine.setPaidStatus(PaidStatus.UNPAID.toString());

                addFine(fine);
            } else {
                if (existingFine.getFineAmount() != fineAmount) {
                    existingFine.setFineAmount(fineAmount);
                    updateFine(existingFine);
                }
            }
        }
    }

    // Ví dụ hàm tính tiền phạt:
    private double calculateFine(BorrowRecordDTO record) {
        if (record.getDueDate() == null) return 0;
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(),
            record.getReturnDate() != null ? record.getReturnDate() : java.time.LocalDate.now());
        double finePerDay = 5000; // VD: 5,000đ/ngày
        return overdueDays > 0 ? overdueDays * finePerDay : 0;
    }
}
