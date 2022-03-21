package com.example.tdd.controller;

import com.example.tdd.dto.ClientDto;
import com.example.tdd.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //------- save : -------------------------------------------------------------------

    @PostMapping("/save")

    public ResponseEntity<ClientDto> save(@RequestBody ClientDto clientDto ){
        ClientDto clientadded = clientService.save(clientDto);
        return new ResponseEntity<ClientDto>(clientadded, HttpStatus.CREATED);
    }

    //------- All  : -------------------------------------------------------------------

    @GetMapping("/")

    public ResponseEntity<List<ClientDto>> all(@RequestParam(value ="page" ,defaultValue = "0") int page , @RequestParam(value ="limit",defaultValue = "5")int limit){
        System.out.println("all");
        List<ClientDto> clientDto = clientService.all(page, limit);
        return ResponseEntity.ok(clientDto);
    }

    //------- By Id ----------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable long id)  {
        ClientDto client = clientService.getById(id);
        return new ResponseEntity<ClientDto>(client, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------

    //------- By email ----------------------------------------------------

    @GetMapping(value = "/{email}",params = "email")
    public ResponseEntity<ClientDto> getByEmail(@PathVariable("email") String email)  {
        System.out.println("getByEmail");
        ClientDto client = clientService.getByEmail(email);
        return new ResponseEntity<ClientDto>(client, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------
    //------- By sex ----------------------------------------------------

    @GetMapping("/all/{sex}")
    public ResponseEntity<List<ClientDto>> getBySex(@PathVariable("sex") String sex)  {
        List<ClientDto> contact = clientService.getBySex(sex);
        return new ResponseEntity<List<ClientDto>>(contact, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------

    //------- Delete  : --------------------------------------------------------------

    @DeleteMapping("/{id}")

    public ResponseEntity<Boolean> delete(@PathVariable long id){
       Boolean isdeleted= clientService.delete(id);
        return new ResponseEntity<Boolean>(isdeleted,HttpStatus.NO_CONTENT);

    }
    //------- update  : --------------------------------------------------------------

    @PutMapping("/{id}")

    public ResponseEntity<ClientDto> update(@RequestBody ClientDto client,@PathVariable long id  ){

        ClientDto updatedclient=  clientService.update(client);



        return new ResponseEntity<ClientDto>(updatedclient, HttpStatus.CREATED);


    }
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello Controller";
    }
}
