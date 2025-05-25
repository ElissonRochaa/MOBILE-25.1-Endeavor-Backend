package api.endeavorbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grupo_de_estudo")
public class GrupoDeEstudo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;

    private String descricao;

    private int capacidade;

    private boolean privado;

    @ManyToOne
    @JoinColumn(name = "area_estudo_id", nullable = false)
    private AreaEstudo areaEstudo;

    @ManyToMany(mappedBy = "grupos")
    private Set<Usuario> usuarios = new HashSet<>();
}
