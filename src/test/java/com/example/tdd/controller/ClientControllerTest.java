package com.example.tdd.controller;

import com.example.tdd.dto.ClientDto;
import com.example.tdd.entities.ClientEntity;
import com.example.tdd.repositories.ClientRepository;
import com.example.tdd.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper =new ObjectMapper();
    ObjectWriter objectWriter =objectMapper.writer();

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    @InjectMocks
    ClientController clientController;

    ClientDto clt1 =new ClientDto(44L,"clt1@gmail.com","061854671741","clt1",12,"homme",true);
    ClientDto clt2 =new ClientDto(66L,"clt2@gmail.com","067854671747","clt2",12,"homme",true);
    ClientDto clt3 =new ClientDto(33L,"clt3@gmail.com","063854671743","clt3",12,"homme",true);
    ClientDto cldto =new ClientDto(45L,"cldto@gmail.com","064854471741","cldto",12,"homme",true);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(clientController).build();
        List<ClientDto> clients= new ArrayList<>(Arrays.asList(clt1,clt2,clt3));
    }

    @Test
    void all()throws Exception {
        List<ClientDto> allclients= new ArrayList<>(Arrays.asList(clt1,clt2,clt3));
        Mockito.when(clientService.all(0,5)).thenReturn(allclients);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$" ,hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nom",is("clt3")))

                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;

    }
    @Test
    void bysex()throws Exception {
        List<ClientDto> allclients= new ArrayList<>(Arrays.asList(clt1,clt2,clt3));
        Mockito.when(clientService.getBySex("home")).thenReturn(allclients);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/all/home")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$" ,hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nom",is("clt3")))

                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }



    @Test
    void byid()throws Exception {

        Mockito.when(clientService.getById(cldto.getId())).thenReturn(cldto);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/45")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom",is("cldto")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }
    @Test
    void delete()throws Exception {

        Mockito.when(clientService.delete(cldto.getId())).thenReturn(Boolean.TRUE);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/45")

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())

                .andExpect(MockMvcResultMatchers.jsonPath("$",is(Boolean.TRUE)))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }
    @Test
    void byemail()throws Exception {
        Mockito.when(clientService.getByEmail("achraf@gmail.com")).thenReturn(clt3);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/achraf@gmail.com?email")
//                        .param("email","achraf@gmail.com")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",is("clt3@gmail.com")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }



    @Test
    void save()throws Exception {
        ClientDto cltsave =new ClientDto(63L,"cltsave@gmail.com","065853671741","cltsave",12,"homme",true);

        Mockito.when(clientService.save(cltsave)).thenReturn(cltsave);
        String content =objectWriter.writeValueAsString(cltsave);
        MockHttpServletRequestBuilder mockReq =MockMvcRequestBuilders.post("/clients/save")

                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockReq)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom",is("cltsave")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;

    }
    @Test
    void update()throws Exception {
        ClientDto updated =new ClientDto(1L,"cltsave@gmail.com","065853671741","cltsave",12,"homme",true);

        Mockito.when(clientService.update(updated)).thenReturn(updated);
        String content =objectWriter.writeValueAsString(updated);
        MockHttpServletRequestBuilder mockReq =MockMvcRequestBuilders.put("/clients/1")

                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockReq)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom",is("cltsave")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;

    }



}