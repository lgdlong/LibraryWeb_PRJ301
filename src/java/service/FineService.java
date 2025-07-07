package service;

import dao.*;
import dto.*;
import entity.*;
import enums.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import mapper.*;

public class FineService {
    private final FineDao fineDao = new FineDao();
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    public boolean isFinePaid(long fineId) {
        if (fineId <= 0) throw new IllegalArgumentException("Fine ID must be positive");
        // Get fine by ID
        Fine fine = fineDao.getById(fineId);
        if (fine == null) return false;
        return PaidStatus.PAID.equals(fine.getPaidStatus());
    }

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
            return Collections.emptyList();
        }
    }

    public FineDTO getFineById(long id) {
        Fine fine = fineDao.getById(id);
        return fine != null ? FineMapping.toDTO(fine) : null;
    }

    public FineDTO getFineByBorrowRecordId(long borrowRecordId) {
        if (borrowRecordId <= 0) {
            throw new IllegalArgumentException("Borrow record ID must be positive");
        }
        Fine fine = fineDao.getByBorrowRecordId(borrowRecordId);
        return fine != null ? FineMapping.toDTO(fine) : null;
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
        SystemConfigService configService = new SystemConfigService();
        double finePerDay = configService.getConfigByConfigKey("overdue_fine_per_day").getConfigValue();

        // Lấy danh sách overdue
        List<BorrowRecordDTO> overdueRecords = borrowRecordService.getAllOverdue();

        for (BorrowRecordDTO record : overdueRecords) {
            try {
                Fine fine = fineDao.getByBorrowRecordId(record.getId());
                FineDTO existingFine = fine != null ? FineMapping.toDTO(fine) : null;
                double fineAmount = calculateFine(record, finePerDay);

                if (existingFine == null) {
                    FineDTO fineDTO = new FineDTO();
                    fineDTO.setBorrowRecordId(record.getId());
                    fineDTO.setFineAmount(fineAmount);
                    fineDTO.setPaidStatus(PaidStatus.UNPAID.toString());
                    addFine(fineDTO);
                } else {
                    if (Math.abs(existingFine.getFineAmount() - fineAmount) > 0.001) {
                        existingFine.setFineAmount(fineAmount);
                        updateFine(existingFine);
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to process fine for record " + record.getId() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Xử lý phạt quá hạn cho một user cụ thể (tối ưu hiệu suất)
     *
     * @param userId ID của user cần xử lý phạt
     */
    public void processOverdueFines(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }

        SystemConfigService configService = new SystemConfigService();
        double finePerDay = configService.getConfigByConfigKey("overdue_fine_per_day").getConfigValue();

        // Lấy danh sách overdue chỉ cho user này
        List<BorrowRecordDTO> overdueRecords = borrowRecordService.getOverdueByUserId(userId);

        for (BorrowRecordDTO record : overdueRecords) {
            try {
                Fine fine = fineDao.getByBorrowRecordId(record.getId());
                FineDTO existingFine = fine != null ? FineMapping.toDTO(fine) : null;
                double fineAmount = calculateFine(record, finePerDay);

                if (existingFine == null) {
                    FineDTO fineDTO = new FineDTO();
                    fineDTO.setBorrowRecordId(record.getId());
                    fineDTO.setFineAmount(fineAmount);
                    fineDTO.setPaidStatus(PaidStatus.UNPAID.toString());
                    addFine(fineDTO);
                } else {
                    if (Math.abs(existingFine.getFineAmount() - fineAmount) > 0.001) {
                        existingFine.setFineAmount(fineAmount);
                        updateFine(existingFine);
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to process fine for record " + record.getId() + " for user " + userId + ": " + e.getMessage());
            }
        }
    }

    // Ví dụ hàm tính tiền phạt:
    private double calculateFine(BorrowRecordDTO record, double finePerDay) {
        if (record.getDueDate() == null) return 0;
        LocalDate endDate = record.getReturnDate() != null ? record.getReturnDate() : java.time.LocalDate.now();
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), endDate);
        return Math.max(0, overdueDays) * finePerDay;
    }

    /**
     * Tạo phạt cho bản ghi quá hạn (chỉ tạo nếu chưa có phạt)
     * @param overdueRecord Bản ghi quá hạn cần tạo phạt
     */
    public void createFineForOverdue(BorrowRecordDTO overdueRecord) {
        if (overdueRecord == null) {
            throw new IllegalArgumentException("Overdue record must not be null");
        }

        try {
            // Kiểm tra xem đã có phạt cho bản ghi này chưa
            Fine existingFine = fineDao.getByBorrowRecordId(overdueRecord.getId());
            if (existingFine != null) {
                // Đã có phạt, không tạo thêm
                return;
            }

            // Lấy cấu hình phạt từ hệ thống
            SystemConfigService configService = new SystemConfigService();
            double finePerDay = configService.getConfigByConfigKey("overdue_fine_per_day").getConfigValue();

            // Tính số tiền phạt
            double fineAmount = calculateFine(overdueRecord, finePerDay);

            // Chỉ tạo phạt nếu số tiền > 0
            if (fineAmount > 0) {
                FineDTO fineDTO = new FineDTO();
                fineDTO.setBorrowRecordId(overdueRecord.getId());
                fineDTO.setFineAmount(fineAmount);
                fineDTO.setPaidStatus(PaidStatus.UNPAID.toString());
                addFine(fineDTO);
                System.out.println("Created fine for overdue record ID: " + overdueRecord.getId() + " with amount: " + fineAmount);
            }
        } catch (Exception e) {
            System.err.println("Failed to create fine for overdue record " + overdueRecord.getId() + ": " + e.getMessage());
            // Không ném exception để không làm gián đoạn quá trình cập nhật overdue
        }
    }
}