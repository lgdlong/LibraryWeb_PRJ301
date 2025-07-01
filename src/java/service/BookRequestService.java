package service;

import dao.*;
import db.*;
import dto.*;
import entity.*;
import enums.*;
import mapper.*;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class BookRequestService {
    private final BookRequestDao requestDao = new BookRequestDao();
    private final SystemConfigService configService = new SystemConfigService();
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final BookService bookService = new BookService();

    public boolean isRequestPending(long requestId) {
        if (requestId <= 0) {
            throw new IllegalArgumentException("Request ID must be positive");
        }
        BookRequest request = requestDao.findById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("Request not found with ID: " + requestId);
        }
        return RequestStatus.PENDING.equals(request.getStatus());
    }

    public boolean isRequestPending(BookRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new IllegalArgumentException("Request must not be null and must have a valid ID");
        }
        return RequestStatus.PENDING.equals(request.getStatus());
    }

    public void updateStatus(Connection conn, long reqId, String status) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection must not be null");
        }
        if (reqId <= 0) {
            throw new IllegalArgumentException("Request ID must be positive");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status must not be null or empty");
        }
        requestDao.updateStatus(conn, reqId, status);
    }

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

    // this method will add a new record base on the book request

    /**
     * [x] Tìm BookRequest theo requestId.
     * [x] Kiểm tra trạng thái:
     * [x] - Nếu đã APPROVED hoặc REJECTED thì trả lỗi.
     * [x] Cập nhật trạng thái BookRequest thành APPROVED.
     * [x] Tạo BorrowRecord mới:
     * [x] - Lấy thông tin userId, bookId từ BookRequest.
     * [x] - Set borrowDate là ngày hiện tại.
     * [x] - Set dueDate theo cấu hình (ví dụ: +7 ngày).
     * [x] - status = BORROWED.
     * [x] Lưu BorrowRecord vào database.
     * [#] Book available giảm đi 1.
     * [#] Trả về kết quả/thông báo thành công cho admin.
     *
     * @param requestId
     */
    public void approveRequest(long requestId) {
        Connection conn = null;
        try {
            conn = DbConfig.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            BookRequest req = requestDao.findById(conn, requestId); // sửa dao truyền conn
            if (req == null) {
                throw new IllegalArgumentException("Request not found with ID: " + requestId);
            }
            if (req.getStatus() == null) {
                throw new IllegalArgumentException("Request status is null");
            }
            if (requestId <= 0) {
                throw new IllegalArgumentException("Request ID must be positive");
            }
            if (!RequestStatus.PENDING.equals(req.getStatus())) {
                throw new IllegalArgumentException("Request is not in PENDING status");
            }

            // 1. Update status
            updateStatus(conn, requestId, RequestStatus.APPROVED.toString());

            // 2. Add BorrowRecord
            BorrowRecord newBorrowRecord = new BorrowRecord(req.getUserId(), req.getBookId());
            borrowRecordService.addNewBorrowRecord(conn, newBorrowRecord); // sửa lại để truyền conn

            // 3. Decrement Book Available
            bookService.decrementAvailableCopies(conn, req.getBookId()); // sửa lại để truyền conn

            // OK, commit
            conn.commit();
            System.out.println("Request " + requestId + " has been approved and a new borrow record has been created.");
        } catch (Exception e) {
            // Nếu bất kỳ lỗi nào thì rollback
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public List<BookRequest> viewBooksRequest(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        return requestDao.viewBooksRequest(userId);
    }


}
