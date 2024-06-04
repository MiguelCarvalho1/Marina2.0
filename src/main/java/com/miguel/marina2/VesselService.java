package com.miguel.marina2;

import com.miguel.marina2.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VesselService {

    @Autowired
    private VesselRepository repo;

    @Transactional(readOnly = true)
    public List<Vessel> findAll() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error retrieving vessels", e);
        }
    }
}