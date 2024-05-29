package com.miguel.marina2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Client findById(String id) {
        return repository.findById(id).orElse(null);
    }
}