package api.endeavorbackend.models;

import api.endeavorbackend.models.enuns.StatusCronometro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tempo_materia")
public class TempoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "materia", nullable = false)
    private Materia materia;

    @Column(name = "inicio", nullable = false)
    private Timestamp inicio;

    @Column(name = "fim")
    private Timestamp fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCronometro status;

    @Column(name = "tempo_total_acumulado", nullable = false)
    private Long tempoTotalAcumulado = 0L;


}
