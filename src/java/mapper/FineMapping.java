package mapper;

import dto.*;
import entity.*;
import service.*;

public class FineMapping {

    private static final BorrowRecordService borrowService = new BorrowRecordService();

    public static FineDTO toDTO(Fine fine) {
        if (fine == null) {
            throw new IllegalArgumentException("Fine cannot be null");
        }

        BorrowRecordDTO borrow = borrowService.getById(fine.getBorrowId());

        if (borrow == null) {
            throw new IllegalStateException("Associated borrow record not found for fine ID: " + fine.getId());
        }

        String username = borrow.getUserName();
        String bookTitle = borrow.getBookTitle();

        return new FineDTO(
            fine.getId(),
            username,
            bookTitle,
            fine.getBorrowId(),
            fine.getFineAmount(),
            fine.getPaidStatus()
        );
    }

    public static Fine toEntity(FineDTO fineDTO) {
        if (fineDTO == null) {
            throw new IllegalArgumentException("FineDTO cannot be null");
        }

        return new Fine(
            fineDTO.getId(),
            fineDTO.getBorrowRecordId(),
            fineDTO.getFineAmount(),
            fineDTO.getPaidStatus()
        );
    }
}
