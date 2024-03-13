package com.miguel.marina2;

import java.util.List;

public class CountryService {
    private CountryRepository repo;
    public List<Country> findAll(){
        return repo.findAll();
    }
}
