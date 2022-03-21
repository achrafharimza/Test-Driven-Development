package com.example.tdd.repositories;

import com.example.tdd.entities.ClientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientRepository extends PagingAndSortingRepository<ClientEntity,Long> {
    ClientEntity findById(long id);
      Boolean deleteById(long id);
    ClientEntity findByEmail(String email);
    List<ClientEntity> findBySex(String sex);


     default String get() {
        return "Hello JUnit 5";
    }

}
