package com.example.patron_builder.controller;

import com.example.patron_builder.dto.UsuarioDTO;
import com.example.patron_builder.dto.UsuarioRequest;
import com.example.patron_builder.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    public UsuarioDTO crearUsuario(@RequestBody UsuarioRequest request) {
        return usuarioService.crearUsuario(request);
    }

    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @PutMapping("/actualizar/{id}")
    public UsuarioDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        return usuarioService.actualizarUsuario(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

}
