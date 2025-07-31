package com.example.patron_builder.mapper;

import com.example.patron_builder.dto.UsuarioDTO;
import com.example.patron_builder.dto.UsuarioRequest;
import com.example.patron_builder.entity.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequest request);

    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    void updateEntityFromRequest(UsuarioRequest request, @MappingTarget Usuario usuario);
}