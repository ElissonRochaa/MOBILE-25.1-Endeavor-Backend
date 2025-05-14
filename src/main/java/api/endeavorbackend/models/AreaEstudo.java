package api.endeavorbackend.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AreaEstudo {

    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "areaEstudo")
    private List<Usuario> usuario;

    @OneToMany(mappedBy = "areaEstudo")
    private List<GrupoEstudo> grupoEstudo;
    

}
