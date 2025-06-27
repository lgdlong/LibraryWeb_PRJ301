package mapper;

import dto.*;
import entity.*;
import enums.*;

public class BookMapping {

    public static BookDTO toBookDTO(Book book) {
        if (book == null) return null;

        return new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getCoverUrl(),
            book.getCategory(),
            book.getPublishedYear(),
            book.getTotalCopies(),
            book.getAvailableCopies(),
            book.getStatus() != null ? book.getStatus().toString().toLowerCase() : null
        );
    }

    public static Book toEntity(BookDTO dto) {
        if (dto == null) return null;

        BookStatus status = null;
        if (dto.getStatus() != null) {
            try {
                status = BookStatus.fromString(dto.getStatus());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid book status: " + dto.getStatus());
            }
        }

        return new Book(
            dto.getId(),
            dto.getTitle(),
            dto.getAuthor(),
            dto.getIsbn(),
            dto.getCoverUrl(),
            dto.getCategory(),
            dto.getPublishedYear(),
            dto.getTotalCopies(),
            dto.getAvailableCopies(),
            status
        );
    }
}
