package com.example.tdd.services;

import com.example.tdd.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto client);
    List<ClientDto> all(int page, int limit);
    ClientDto getById(long id);

    ClientDto getByEmail(String email);

    List<ClientDto> getBySex(String sex);


    boolean delete(long id);

    ClientDto update(ClientDto client);

    String get();
}
