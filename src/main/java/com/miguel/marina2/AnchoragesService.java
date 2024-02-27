package com.miguel.marina2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnchoragesService {
    @Autowired
    private  AnchoragesRepository anchoragesRepository;


    public List<Anchorages> findAll() {
        return anchoragesRepository.findAll();
    }
}
