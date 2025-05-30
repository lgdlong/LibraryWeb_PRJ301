package mapper;

import dto.*;
import entity.*;
import service.*;

public class BorrowRecordMapping {
    private static final BookService bookService = new BookService();
    private static final UserService userService = new UserService();

    public static BorrowRecordDTO toBorrowRecordDTO(BorrowRecord record) {
        if (record == null) {
            return null;
        }

        // get user's name and book title from the record
        User user = userService.getUserById(record.getUserId());
        Book book = bookService.getBookById(record.getBookId());

        String userName = user.getName();
        String bookTitle = book.getTitle();

        return new BorrowRecordDTO(
            record.getId(),
            userName,
            bookTitle,
            record.getBorrowDate(),
            record.getDueDate(),
            record.getReturnDate(),
            record.getStatus() != null ? record.getStatus().toString() : null
        );
    }
}
