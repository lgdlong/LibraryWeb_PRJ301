package service;

import dao.*;

public class BookRequestService {
    private final BookRequestDao requestDao = new BookRequestDao();

    public long countPendingRequests() {
        return requestDao.countByStatus("PENDING");
    }
}
