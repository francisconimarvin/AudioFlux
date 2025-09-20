package com.example.audioflux.model;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;


@Entity
@Table (name =  "Sala_Logging")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/// En mi base de datos vamos a usarla para representar los errores que puedan haber.
public class SalaLogging {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer idEvento;

    @ManyToOne
    @JoinColumn (name = "idSala", nullable = true)
    private Sala sala;

    @Column (name = "descripcion", nullable = true, length = 255)
    private String descripcion;

    @Column (name = "fecha", nullable = true)
    private Timestamp fecha;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = new Timestamp(System.currentTimeMillis());
        }
    }
}
