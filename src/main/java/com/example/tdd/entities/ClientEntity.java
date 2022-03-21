package com.example.tdd.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    @Column(name = "email", nullable = false,unique=true)
    private String email;

    //(\+212|0)([ \-_/]*)(\d[ \-_/]*){9}
    @Pattern(regexp="(\\+212|0)([ \\-_/]*)(\\d[ \\-_/]*){9}")
    @Column(name = "telephone", nullable = false,unique=true)
    private String  telephone;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Positive
    @Column(name = "age", nullable = false)
    private int  age ;

    @NotNull
    @Column(name = "sex", nullable = false)
    private String sex;

    private Boolean isactive ;

}
