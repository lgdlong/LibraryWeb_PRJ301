package mapper;

import dto.*;
import entity.*;
import enums.BorrowStatus;
import service.*;
import java.util.Map;

public class BorrowRecordMapping {
    private static final BookService bookService = new BookService();
    private static final UserService userService = new UserService();

    public static BorrowRecordDTO toBorrowRecordDTO(BorrowRecord record) {
    if (record == null) {
        return null;
    }

    User user = userService.getUserById(record.getUserId());
    Book book = bookService.getBookById(record.getBookId());

    String userName = user != null ? user.getName() : "Unknown User";
    String bookTitle = book != null ? book.getTitle() : "Unknown Book";

    return new BorrowRecordDTO(
        record.getId(),
        record.getUserId(),
        record.getBookId(),
        userName,
        bookTitle,
        record.getBorrowDate(),
        record.getDueDate(),
        record.getReturnDate(),
        record.getStatus() != null ? record.getStatus().toString() : null
    );
}


    public static MostBorrowedBookDTO toMostBorrowedBookDTO(Map.Entry<Long, Long> entry) {
        if (entry == null) {
            return null;
        }

        Book book = bookService.getBookById(entry.getKey());
        String title = book != null ? book.getTitle() : "Unknown Book";

        return new MostBorrowedBookDTO(entry.getKey(), title, entry.getValue());
    }

  public static BorrowRecord toBorrowRecord(BorrowRecordDTO dto) {
    if (dto == null) return null;

    BorrowRecord record = new BorrowRecord();
    record.setId(dto.getId());
    record.setUserId(dto.getUserId());     
    record.setBookId(dto.getBookId());     
    record.setBorrowDate(dto.getBorrowDate());
    record.setDueDate(dto.getDueDate());
    record.setReturnDate(dto.getReturnDate());
    record.setStatus(BorrowStatus.valueOf(dto.getStatus()));

    return record;
}
}
