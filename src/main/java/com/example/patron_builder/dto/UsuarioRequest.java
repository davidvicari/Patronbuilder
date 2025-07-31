package com.example.patron_builder.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaDeNacimiento;
    private String genero;
    private String estadoCivil;
}
