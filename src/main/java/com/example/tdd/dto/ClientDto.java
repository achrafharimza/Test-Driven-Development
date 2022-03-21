package com.example.tdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private long id;
    private String email;
    private String telephone;
    private String nom;
    private int age;
    private String sex;
    private Boolean isactive;
}
