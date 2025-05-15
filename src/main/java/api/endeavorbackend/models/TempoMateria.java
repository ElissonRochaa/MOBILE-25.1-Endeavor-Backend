package api.endeavorbackend.models;

import api.endeavorbackend.enuns.StatusCronometro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "tempo_materia")
public class TempoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public Long getDuracao() {
        if (fim != null && inicio != null) {
            return (fim.getTime() - inicio.getTime()) / 1000 + tempoTotalAcumulado; // Adiciona o tempo total acumulado
        }
        return tempoTotalAcumulado;
    }

}
