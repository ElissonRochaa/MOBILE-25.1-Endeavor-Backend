package api.endeavorbackend.models;

import api.endeavorbackend.models.DTOs.TempoMateriaDTO;
import api.endeavorbackend.models.enuns.StatusCronometro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Checklist> checklists = new HashSet<>();

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TempoMateria> tempoMaterias = new HashSet<>();

    @Transient
    public TempoMateriaDTO getSessaoAtivaOuPausada() {
        return tempoMaterias.stream()
                .filter(t -> t.getStatus() == StatusCronometro.EM_ANDAMENTO || t.getStatus() == StatusCronometro.PAUSADO)
                .findFirst().map(TempoMateriaDTO::new)
                .orElse(null);
    }


}
