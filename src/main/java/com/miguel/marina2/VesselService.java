package com.miguel.marina2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VesselService {

    @Autowired
    private VesselRepository repo;
    public List<Vessel> findAll(){
        return repo.findAll();
    }
}
