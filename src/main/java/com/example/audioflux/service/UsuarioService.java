package com.example.audioflux.service;

import com.example.audioflux.model.Usuario;
import com.example.audioflux.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Crear usuario
    public Usuario crearUsuario(String nombreUsuario) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEstadoUsuario(Usuario.EstadoUsuario.DISPONIBLE);
        return usuarioRepository.save(usuario);
    }

    // Actualizar estado de usuario
    public Usuario actualizarEstado (Usuario usuario, Usuario.EstadoUsuario nuevoEstado) {
        usuario.setEstadoUsuario(nuevoEstado);
        return usuarioRepository.save(usuario);
    }
    // Borrar usuario
    public void eliminarUsuario (Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    // Buscar usuario por ID
    public Optional <Usuario> buscarUsuarioId (Integer usuarioId) {
        return usuarioRepository.findById((usuarioId));
    }

    // Generar nombre aleatorio
    public String generarNombreAleatorio() {
        String nombreUsuario;
        do {
            nombreUsuario = "Usuario_" + (int)(Math.random() * 10000);
        } while (usuarioRepository.existsByNombreUsuario(nombreUsuario));
        return nombreUsuario;
    }

}
