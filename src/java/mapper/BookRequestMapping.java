package mapper;

import dto.*;
import entity.*;
import service.*;

public class BookRequestMapping {

    private static final UserService userService = new UserService();
    private static final BookService bookService = new BookService();

    public static BookRequestDTO toDTO(BookRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("BookRequest cannot be null");
        }

        User user = userService.getUserById(request.getUserId());
        Book book = bookService.getBookById(request.getBookId());

        return new BookRequestDTO(
            request.getId(),
            user != null ? user.getName() : "Unknown User",
            book != null ? book.getTitle() : "Unknown Book",
            request.getRequestDate(),
            request.getStatus() != null
                ? request.getStatus().toString()
                : "Unknown Status"
        );
    }
    }
}
