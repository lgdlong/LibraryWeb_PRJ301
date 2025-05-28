package service;

import dao.*;

public class FineService {
    private final FineDao fineDao = new FineDao();

    public long countUnpaidFines() {
        return fineDao.countUnpaid();
    }
}
