package com.example.tdd.repositories;

import com.example.tdd.dto.ClientDto;
import com.example.tdd.entities.ClientEntity;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;



    @Test
    void all() {
        Pageable pageableRequest = PageRequest.of(0, 4);
        Page<ClientEntity> clients =clientRepository.findAll(pageableRequest);

        assertEquals(4,clients.stream().count());

    }
    @Test
    void save() {
        ClientEntity clt =new ClientEntity(0L,"cltsave@gmail.com","0648640763","cltsave",12,"homme",true);
       /* ClientEntity clt =new ClientEntity();*/
        ClientEntity cltsaved =clientRepository.save(clt);

        assertNotEquals(0,cltsaved.getId());

    }
    @Test
    void update() {
        ClientEntity clt =new ClientEntity(26L,"sept1aa@gmail.com","0628640782","cltsave",12,"homme",true);
        clt.setNom("updated");
        ClientEntity cltsaved =clientRepository.save(clt);
        assertEquals("updated",cltsaved.getNom());
        assertEquals(26L,cltsaved.getId());

    }

    @Test
    void byemail() {
       /* ClientEntity clt =new ClientEntity(0L,"cltsave@gmail.com","0648640763","cltsave",12,"homme",true);
        ClientEntity cltsaved =clientRepository.save(clt);*/
        ClientEntity byemail =clientRepository.findByEmail("achraf@gmail.com");

        assertEquals("achraf@gmail.com",byemail.getEmail());

    }

    @Test
    void byid() {

        ClientEntity byemail =clientRepository.findById(1L);

        assertEquals("achraf@gmail.com",byemail.getEmail());

    }

    @Test
    void delete() {

        ClientEntity clt =new ClientEntity(0L,"cltsave@gmail.com","0648640763","cltsave",12,"homme",true);
        ClientEntity cltsaved =clientRepository.save(clt);
//        assertEquals(1,clientRepository.deleteById(1));

    }
    @Test
    void bysex() {

        List<ClientEntity> byemail =clientRepository.findBySex("achraf");

        assertEquals(4,byemail.size());

    }

}
