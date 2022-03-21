package com.example.tdd;

import com.example.tdd.controller.ClientController;
import com.example.tdd.dto.ClientDto;
import com.example.tdd.repositories.ClientRepository;
import com.example.tdd.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TestIntégration {


@Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper =new ObjectMapper();
    ObjectWriter objectWriter =objectMapper.writer();

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    @InjectMocks
    ClientController clientController;

    @Test
    void all()throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(greaterThan(1))))
//                .andExpect(MockMvcResultMatchers.jsonPath("$" ,hasSize(0)))


                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;

    }
    @Test
    void bysex()throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/all/achraf")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(greaterThan(1))))
//                .andExpect(MockMvcResultMatchers.jsonPath("$" ,hasSize(0)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nom",is("clt3")))

                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }
    @Test
    void byid()throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/1")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom",is("achrafupdated")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }
    @Test
    void byemail()throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/achraf@gmail.com?email")
//                        .param("email","achraf@gmail.com")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom",is("achrafupdated")))
                .andDo(MockMvcResultHandlers.print()).andReturn()
        ;
    }
    @Test
    void save()throws Exception {
        ClientDto cltsave =new ClientDto(0L,"cltsave@gmail.com","0648940789","cltsave",12,"homme",true);


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
