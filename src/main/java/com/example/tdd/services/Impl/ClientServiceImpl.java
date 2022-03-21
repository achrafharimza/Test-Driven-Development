package com.example.tdd.services.Impl;

import com.example.tdd.dto.ClientDto;
import com.example.tdd.dto.services.IMapClassWithDto;
import com.example.tdd.entities.ClientEntity;
import com.example.tdd.repositories.ClientRepository;
import com.example.tdd.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    IMapClassWithDto<ClientEntity, ClientDto> clientMapping;

    public ClientServiceImpl() {
    }

    @Override
    public ClientDto save(ClientDto client) {


        ClientEntity clientEntity = clientMapping.convertToEntity(client, ClientEntity.class);

        clientEntity = clientRepository.save(clientEntity);

        return clientMapping.convertToDto(clientEntity, ClientDto.class);
    }
    @Override
    public ClientDto update(ClientDto client) {
        ClientEntity clientEntity = clientMapping.convertToEntity(client, ClientEntity.class);
//        contactEntity.setId(id);
        clientEntity = clientRepository.save(clientEntity);

        return clientMapping.convertToDto(clientEntity, ClientDto.class);
    }

    @Override
    public List<ClientDto> all(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<ClientEntity> clients =clientRepository.findAll(pageableRequest);
        return clientMapping.convertPageToListDto(clients,ClientDto.class);
    }

    @Override
    public ClientDto getById(long id) {
        ClientEntity client = clientRepository.findById(id);

        return clientMapping.convertToDto(client,ClientDto.class);
    }

    @Override
    public ClientDto getByEmail(String email) {
        ClientEntity client = clientRepository.findByEmail(email);

        return clientMapping.convertToDto(client,ClientDto.class);
    }

    @Override
    public List<ClientDto> getBySex(String sex) {
        List<ClientEntity> clients =  clientRepository.findBySex(sex);

        return clientMapping.convertListToListDto(clients,ClientDto.class);
    }

    @Override
    public boolean delete(long id) {
        ClientEntity client = clientRepository.findById(id);

        if(client == null)  return false;
        boolean isdele= clientRepository.deleteById(id);
        System.out.println(isdele);

        return true;
    }


    @Override
    public String get() {
        return clientRepository.get();
    }

}
