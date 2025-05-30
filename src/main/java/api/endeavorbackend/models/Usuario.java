package api.endeavorbackend.models;

import api.endeavorbackend.models.enuns.Escolaridade;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;
    
    @Column(name = "idade")
    private int idade;

    @Enumerated(EnumType.STRING)
    private Escolaridade escolaridade;

    @ManyToOne
    @JoinColumn(name = "area_estudo")
    @JsonIgnore
    private AreaEstudo areaEstudo;
    
    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Materia> materias;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo_estudo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_de_estudo_id")
    )
    private Set<GrupoDeEstudo> gruposParticipando;
}
