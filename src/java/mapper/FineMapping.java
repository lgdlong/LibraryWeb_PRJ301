package mapper;

import dto.*;
import entity.*;
import service.*;

public class FineMapping {

    private static final BorrowRecordService borrowService = new BorrowRecordService();

    public static FineDTO toDTO(Fine fine) {
+        if (fine == null) {
+            throw new IllegalArgumentException("Fine cannot be null");
+        }
+
        BorrowRecordDTO borrow = borrowService.getById(fine.getBorrowId());

        if (borrow == null) {
-            return null; // or throw an exception if preferred
+            throw new IllegalStateException("Associated borrow record not found for fine ID: " + fine.getId());
        }

        // ... rest of method unchanged ...
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
