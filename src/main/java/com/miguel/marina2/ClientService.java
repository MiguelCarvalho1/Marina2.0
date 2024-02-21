package com.miguel.marina2;

import java.util.List;

public class ClientService {
    private ClientRepository repository;

    public List<Client> findAll(){
        return repository.findAll();
    }
}
