package com.example.tdd.services.Impl;

import com.example.tdd.controller.ClientController;
import com.example.tdd.dto.ClientDto;
import com.example.tdd.dto.services.IMapClassWithDto;
import com.example.tdd.entities.ClientEntity;
import com.example.tdd.repositories.ClientRepository;
import com.example.tdd.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClientServiceImplTest {


    @Mock
    private ClientRepository clientRepository;
    @Mock
    IMapClassWithDto<ClientEntity, ClientDto> clientMapping;

    @InjectMocks
    ClientServiceImpl clientServiceImpl;

    ClientEntity clt =new ClientEntity(1L,"cons@gmail.com","0628541782","cons",12,"homme",true);
    ClientDto cltDto=  new ClientDto(1L,"cons@gmail.com","0628541782","cons",12,"homme",true);

    List<ClientEntity> cltList= new ArrayList<>(Arrays.asList(clt));
    List<ClientDto> cltDtoList= new ArrayList<>(Arrays.asList(cltDto));

    Page<ClientEntity>  cltPage= new PageImpl<>(cltList);

    @Test
    void getById() {

        Mockito.when(clientRepository.findById(1)).thenReturn(clt);
        Mockito.when(clientMapping.convertToDto(clt,ClientDto.class)).thenReturn(cltDto);

        ClientDto cltFinded=clientServiceImpl.getById(1);
        assertEquals(cltFinded,cltDto);
}
    @Test
    void getByEmail() {

        Mockito.when(clientRepository.findByEmail(clt.getEmail())).thenReturn(clt);
        Mockito.when(clientMapping.convertToDto(clt,ClientDto.class)).thenReturn(cltDto);

        ClientDto cltFinded=clientServiceImpl.getByEmail(clt.getEmail());
        assertEquals(cltFinded,cltDto);
}
    @Test
    void getBySex() {

        Mockito.when(clientRepository.findBySex("homme")).thenReturn(cltList);
        Mockito.when(clientMapping.convertListToListDto(cltList,ClientDto.class)).thenReturn(cltDtoList);

        List<ClientDto> cltFinded=clientServiceImpl.getBySex("homme");
        assertEquals(cltFinded,cltDtoList);
}
    @Test
    void all() {
        Pageable pageableRequest = PageRequest.of(0, 5);
        Mockito.when(clientRepository.findAll(pageableRequest)).thenReturn(cltPage);
        Mockito.when(clientMapping.convertPageToListDto(cltPage,ClientDto.class)).thenReturn(cltDtoList);

        List<ClientDto> cltFinded=clientServiceImpl.all(0,5);
        assertEquals(cltFinded,cltDtoList);
}

    @Test
    void save() {
        Mockito.when(clientMapping.convertToEntity(cltDto,ClientEntity.class)).thenReturn(clt);
        Mockito.when(clientRepository.save(clt)).thenReturn(clt);
        Mockito.when(clientMapping.convertToDto(clt,ClientDto.class)).thenReturn(cltDto);

        ClientDto cltFinded=clientServiceImpl.save(cltDto);
        assertEquals(cltFinded,cltDto);
    }
    @Test
    void update() {
        Mockito.when(clientMapping.convertToEntity(cltDto,ClientEntity.class)).thenReturn(clt);
        Mockito.when(clientRepository.save(clt)).thenReturn(clt);
        Mockito.when(clientMapping.convertToDto(clt,ClientDto.class)).thenReturn(cltDto);

        ClientDto cltFinded=clientServiceImpl.save(cltDto);
        assertEquals(cltFinded,cltDto);
    }
    @Test
    void delete() {

        Mockito.when(clientRepository.findById(1)).thenReturn(clt);
        Mockito.when(clientRepository.deleteById(1)).thenReturn(Boolean.TRUE);



        boolean cltFinded=clientServiceImpl.delete(1);
        assertEquals(true,cltFinded);
    }






}