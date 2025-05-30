package api.endeavorbackend.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GrupoEstudo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "area_estudo")
    private AreaEstudo areaEstudo;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @OneToMany
    private List<ConviteGrupo> convites;




}
