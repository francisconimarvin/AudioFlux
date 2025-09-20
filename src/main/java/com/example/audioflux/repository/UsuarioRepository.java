package com.example.audioflux.repository;

import com.example.audioflux.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    boolean existsByNombreUsuario(String nombreUsuario);
}
