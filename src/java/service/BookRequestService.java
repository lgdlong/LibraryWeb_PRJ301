package service;

import dao.*;
import dto.*;
import entity.*;
import java.util.*;
import java.util.stream.*;
import mapper.*;

public class BookRequestService {
    private final BookRequestDao requestDao = new BookRequestDao();

    public long countPendingRequests() {
        return requestDao.countByStatus("PENDING");
    }

    public List<BookRequestDTO> getAllRequests() {
        List<BookRequest> requests = requestDao.findAll();

        if (requests.isEmpty()) {
            return Collections.emptyList();
        }

        return requests.stream()
            .map(BookRequestMapping::toDTO)
            .collect(Collectors.toList());
    }

    public BookRequest getRequestById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Request ID must be positive");
        }
        return requestDao.findById(id);
    }

    public void updateStatus(long id, String status) {
        if (id <= 0) {
            throw new IllegalArgumentException("Request ID must be positive");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status must not be null or empty");
        }
        requestDao.updateStatus(id, status);
    }

    public List<BookRequest> viewBooksRequest(long userId){
        if(userId <= 0){
            throw new IllegalArgumentException("User ID must be positive");
        }
        return requestDao.viewBooksRequest(userId);
     }
}
