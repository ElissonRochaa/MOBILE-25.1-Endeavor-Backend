package api.endeavorbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    private LocalDateTime dataFazer;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
}
