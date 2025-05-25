package api.endeavorbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "area_estudo")
public class AreaEstudo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @OneToMany(mappedBy = "areaEstudo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GrupoDeEstudo> grupoDeEstudos = new HashSet<>();
}
