package api.endeavorbackend.models;

import api.endeavorbackend.models.enuns.Escolaridade;
import api.endeavorbackend.models.enuns.Role;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter @Setter
public class Usuario implements UserDetails {

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

    @Column(name = "role")
    private Role role;

    public Usuario(String nome, String email, String senha, int idade, Escolaridade escolaridade, AreaEstudo areaEstudo, Role role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.escolaridade = escolaridade;
        this.areaEstudo = areaEstudo;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                           new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
