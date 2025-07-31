package com.example.patron_builder.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaDeNacimiento;
    private String genero;
    private String estadoCivil;

    public UsuarioDTO() {
        // Constructor vacío necesario para MapStruct
    }

    // Constructor que recibe el Builder
    private UsuarioDTO(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.email = builder.email;
        this.fechaDeNacimiento = builder.fechaDeNacimiento;
        this.genero = builder.genero;
        this.estadoCivil = builder.estadoCivil;
    }

    // Clase interna Builder
    public static class Builder {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private LocalDate fechaDeNacimiento;
        private String genero;
        private String estadoCivil;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }


        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder fechaDeNacimiento(LocalDate fechaDeNacimiento) {
            this.fechaDeNacimiento = fechaDeNacimiento;
            return this;
        }

        public Builder genero(String genero) {
            this.genero = genero;
            return this;
        }

        public Builder estadoCivil(String estadoCivil) {
            this.estadoCivil = estadoCivil;
            return this;
        }

        public UsuarioDTO build() {
            return new UsuarioDTO(this);
        }
    }
}