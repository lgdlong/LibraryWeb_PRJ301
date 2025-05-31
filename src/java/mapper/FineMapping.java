package mapper;

import dto.*;
import entity.*;
import service.*;

public class FineMapping {

    private static final BorrowRecordService borrowService = new BorrowRecordService();

    public static FineDTO toDTO(Fine fine) {
        BorrowRecordDTO borrow = borrowService.getById(fine.getBorrowId());

        if (borrow == null) {
            return null; // or throw an exception if preferred
        }

        String username = borrow.getUserName();
        String bookTitle = borrow.getBookTitle();

        return new FineDTO(
            fine.getId(),
            username,
            bookTitle,
            fine.getFineAmount(),
            fine.getPaidStatus().toString()
        );
    }
}
