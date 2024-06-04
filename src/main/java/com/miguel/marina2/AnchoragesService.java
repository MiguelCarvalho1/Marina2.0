package com.miguel.marina2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnchoragesService {
    @Autowired
    private AnchoragesRepository anchoragesRepository;

    public List<Anchorages> findAll() {
        return anchoragesRepository.findAll();
    }

    public Anchorages findById(int id) {
        Optional<Anchorages> result = anchoragesRepository.findById(String.valueOf(id));
        return result.orElse(null);
    }
}
