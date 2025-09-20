package com.example.audioflux.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table (name = "Sala")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idSala;

    // FK Buscando los usuarios
    @ManyToOne
    @JoinColumn(name = "idUsuario_1", nullable = false)
    private Usuario usuario1;

    // Nullable true porque una sala puede no tener al usuario 2.
    @ManyToOne
    @JoinColumn(name = "idUsuario_2", nullable = true)
    private Usuario usuario2;

    // Estados de la sala
    public enum EstadoSala {
        ABIERTA,
        EN_CURSO,
        CERRADA
    }

    @Enumerated (EnumType.STRING)
    @Column (name = "estadoSala", nullable = false)
    private EstadoSala estadoSala;

    @Column (name = "fechaCreacion", nullable = false, updatable = false)
    private Timestamp fechaCreacion;

    @Column (name = "fechaCierre", nullable = true)
    private Timestamp fechaCierre;


    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = new Timestamp(System.currentTimeMillis());
        }
    }
    // Constructor
    // JPA Empty
    // Getters y setters
}
