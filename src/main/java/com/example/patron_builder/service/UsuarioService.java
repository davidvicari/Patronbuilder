package com.example.patron_builder.service;

import com.example.patron_builder.dto.UsuarioDTO;
import com.example.patron_builder.dto.UsuarioRequest;
import com.example.patron_builder.entity.Usuario;
import com.example.patron_builder.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.stream.Collectors;

import com.example.patron_builder.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.time.LocalDate;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void eliminarUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.delete(usuarioOptional.get());
    }


    public UsuarioDTO crearUsuario(UsuarioRequest request) {
        validarUsuarioParaCreacion(request);

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setFechaDeNacimiento(request.getFechaDeNacimiento());
        usuario.setGenero(request.getGenero());
        usuario.setEstadoCivil(request.getEstadoCivil());

        Usuario guardado = usuarioRepository.save(usuario);

        return new UsuarioDTO.Builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .apellido(guardado.getApellido())
                .email(guardado.getEmail())
                .fechaDeNacimiento(guardado.getFechaDeNacimiento())
                .genero(guardado.getGenero())
                .estadoCivil(guardado.getEstadoCivil())
                .build();
    }



    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioDTO.Builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .email(usuario.getEmail())
                        .fechaDeNacimiento(usuario.getFechaDeNacimiento())
                        .genero(usuario.getGenero())
                        .estadoCivil(usuario.getEstadoCivil())
                        .build())
                .collect(Collectors.toList());
    }

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioDTO actualizarUsuario(Long id, UsuarioRequest request) {

        validarUsuarioParaActualizacion(request);

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        Usuario usuario = usuarioOptional.get();

        // Solo actualiza los campos que no son null
        usuarioMapper.updateEntityFromRequest(request, usuario);

        Usuario actualizado = usuarioRepository.save(usuario);

        return new UsuarioDTO.Builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .apellido(actualizado.getApellido())
                .email(actualizado.getEmail())
                .fechaDeNacimiento(actualizado.getFechaDeNacimiento())
                .genero(actualizado.getGenero())
                .estadoCivil(actualizado.getEstadoCivil())
                .build();
    }


    private void validarUsuarioParaCreacion(UsuarioRequest request) {
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es obligatorio");
        }


        validarCamposComunes(request);
    }

    private void validarUsuarioParaActualizacion(UsuarioRequest request) {
        // Solo validamos los campos que vienen en el request (no null)
        if (request.getNombre() != null && request.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío si se proporciona");
        }

        if (request.getEmail() != null && request.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email no puede estar vacío si se proporciona");
        }


        validarCamposComunes(request);
    }

    private void validarCamposComunes(UsuarioRequest request) {
        if (request.getNombre() != null && !request.getNombre().matches("[a-zA-ZÁÉÍÓÚÑáéíóúñ\\s]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre solo debe contener letras y espacios");
        }

        if (request.getEmail() != null && !request.getEmail().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email no tiene un formato válido");
        }

        if (request.getApellido() != null && !request.getApellido().matches("[a-zA-ZÁÉÍÓÚÑáéíóúñ\\s]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El apellido solo debe contener letras y espacios");
        }

        if (request.getFechaDeNacimiento() != null) {
            LocalDate hoy = LocalDate.now();
            if (!request.getFechaDeNacimiento().isBefore(hoy)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de nacimiento debe estar en el pasado");
            }

            int edad = hoy.getYear() - request.getFechaDeNacimiento().getYear();
            if (request.getFechaDeNacimiento().plusYears(edad).isAfter(hoy)) {
                edad--;
            }
            if (edad < 13) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario debe tener al menos 13 años");
            }
        }

        if (request.getGenero() != null && !request.getGenero().matches("(?i)^(masculino|femenino|otro)$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El género debe ser 'masculino', 'femenino' u 'otro'");
        }

        if (request.getEstadoCivil() != null && !request.getEstadoCivil().matches("(?i)^(soltero|casado|divorciado|viudo|otro)$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado civil no válido");
        }
    }


}
