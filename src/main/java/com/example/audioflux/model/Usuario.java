package com.example.audioflux.model;

// Imports
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    // Atributos mapeados de una vez a la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column (name = "nombreUsuario", nullable = false, unique = true, length = 45)
    private String nombreUsuario;

    // Estados de la sala
    public enum EstadoUsuario {
        DISPONIBLE,
        EN_LLAMADA,
        NO_DISPONIBLE
    }
    @Enumerated( EnumType.STRING)
    @Column (name = "estadoUsuario", nullable = false)
    private EstadoUsuario estadoUsuario;

    // Mapear a la sala dando opciones de estar como primero.
    @OneToMany (mappedBy = "usuario1")
    private List<Sala> salasComoUsuario1;

    // Mapear a la sala dando opciones de estar como segundo.
    @OneToMany (mappedBy = "usuario2")
    private List<Sala> salasComoUsuario2;

    // JPA Empty
    // Constructor
    // Getters y setters
}