package com.miguel.marina2;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VesselRepository extends MongoRepository<Vessel, String> {

    List<Vessel> findAll();
}
