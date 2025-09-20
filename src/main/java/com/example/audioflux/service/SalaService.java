package com.example.audioflux.service;


import com.example.audioflux.model.HistorialLlamadas;
import com.example.audioflux.model.Sala;
import com.example.audioflux.model.SalaLogging;
import com.example.audioflux.model.Usuario;
import com.example.audioflux.repository.HistorialLlamadasRepository;
import com.example.audioflux.repository.SalaLoggingRepository;
import com.example.audioflux.repository.SalaRepository;
import com.example.audioflux.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final HistorialLlamadasRepository historialLlamadasrepository;
    private final SalaLoggingRepository salaLoggingRepository;
    private final UsuarioService usuarioService;

    public Sala emparejarUsuario(Usuario usuario) {
        // Cambiamos el estado del usuario
        usuarioService.actualizarEstado(usuario, Usuario.EstadoUsuario.EN_LLAMADA);

        // Buscar sala abierta que no tenga usuario2
        Optional<Sala> salaAbierta = salaRepository.findAll().stream()
                .filter(s -> s.getEstadoSala() == Sala.EstadoSala.ABIERTA && s.getUsuario2() == null)
                .findFirst();

        if (salaAbierta.isPresent()) {
            Sala sala = salaAbierta.get();
            sala.setUsuario2(usuario);
            sala.setEstadoSala(Sala.EstadoSala.EN_CURSO);
            return salaRepository.save(sala);
        } else {
            // En caso que no hayan salas abiertas
            Sala sala = new Sala();
            sala.setUsuario1(usuario);
            sala.setEstadoSala(Sala.EstadoSala.ABIERTA);
            sala.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            return salaRepository.save(sala);
        }
    }

    // Cerrar sala y registrar historial, ademas de liberar usuarios de la misma.
    public void cerrarSala(Sala sala) {
        sala.setEstadoSala(Sala.EstadoSala.CERRADA);
        sala.setFechaCierre(new Timestamp(System.currentTimeMillis()));

        // Liberar usuarios
        if (sala.getUsuario1() != null) {
            usuarioService.actualizarEstado(sala.getUsuario1(), Usuario.EstadoUsuario.DISPONIBLE);
        }

        if (sala.getUsuario2() != null) {
            usuarioService.actualizarEstado(sala.getUsuario2(), Usuario.EstadoUsuario.DISPONIBLE);
        }

        // Guardar historial
        HistorialLlamadas historialLlamadas = new HistorialLlamadas();
        historialLlamadas.setSala(sala);
        historialLlamadas.setUsuario1(sala.getUsuario1());
        historialLlamadas.setUsuario2(sala.getUsuario2());
        historialLlamadas.setFechaInicio(sala.getFechaCreacion());
        historialLlamadas.setFechaFin(sala.getFechaCierre());
        if(sala.getFechaCierre() != null && sala.getFechaCreacion() != null) {
            historialLlamadas.setDuracion((int) ((sala.getFechaCierre().getTime() - sala.getFechaCreacion().getTime()) / 1000));
        }
        historialLlamadasrepository.save(historialLlamadas);

        // Log
        SalaLogging salaLogging = new SalaLogging();
        salaLogging.setSala(sala);
        salaLogging.setDescripcion("Sala cerrada y usuarios liberados");
        salaLoggingRepository.save(salaLogging);
    }

    // Cuando un usuario abandona la app
    public void usuarioAbandona(Usuario usuario) {
        // Cerrar todas las salas donde esté
        usuario.getSalasComoUsuario1().forEach(this::cerrarSala);
        usuario.getSalasComoUsuario2().forEach(this::cerrarSala);

        // Borrar usuario
        usuarioService.eliminarUsuario(usuario);
    }
}

