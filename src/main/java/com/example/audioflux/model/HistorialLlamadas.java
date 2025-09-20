package com.example.audioflux.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table (name = "Historial_Llamadas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialLlamadas {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idHistorialLlamadas;

    @ManyToOne
    @JoinColumn (name = "idSala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn (name = "idUsuario_1", nullable = false)
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn (name = "idUsuario_2", nullable = true)
    private Usuario usuario2;

    @Column (name = "fechaInicio", nullable = false, updatable = false)
    private Timestamp fechaInicio;

    @Column (name = "fechaFin", nullable = true)
    private Timestamp fechaFin;

    @Column (name = "duracion")
    private Integer duracion;

    // JPA Empty
    // Constructor
    // Getters y setters

}

