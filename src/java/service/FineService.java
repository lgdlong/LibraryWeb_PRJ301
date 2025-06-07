package service;

import dao.*;
import dto.*;
import entity.*;
import mapper.*;

import java.util.*;
import java.util.stream.*;

public class FineService {
    private FineDao fineDao = new FineDao();

    public List<FineDTO> getAllFines() {
        List<Fine> requests = fineDao.getAll();

        if (requests.isEmpty()) {
            return Collections.emptyList();
        }

        return requests.stream()
            .map(FineMapping::toDTO)
            .collect(Collectors.toList());
    }

    public Fine getFineById(long id) {
        return fineDao.getById(id);
    }

    public void updateFine(Fine fine) {
        fineDao.update(fine);
    }

    public void addFine(Fine fine) {
        fineDao.add(fine);
    }

    public void deleteFine(long id) {
        fineDao.delete(id);
    }

    public long countUnpaidFines() {
        return fineDao.countUnpaidFines();
    }
}
