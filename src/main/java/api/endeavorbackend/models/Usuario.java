package api.endeavorbackend.models;

import api.endeavorbackend.enuns.Escolaridade;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private long id;

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

    @OneToMany(mappedBy = "usuario")
    private List<GrupoEstudo> gruposEstudo;
}
