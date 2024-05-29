package com.miguel.marina2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository repository;

    public List<Country> findAll() {
        return repository.findAll();
    }

    public Country save(Country country) {
        return repository.save(country);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Country findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
