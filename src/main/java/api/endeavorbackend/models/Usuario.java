package api.endeavorbackend.models;

import api.endeavorbackend.enuns.Escolaridade;
import api.endeavorbackend.enuns.Role;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

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

    @Column(name = "role")
    private Role role;

    public Usuario(String nome, String email, String senha, int idade, Escolaridade escolaridade, Role role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.escolaridade = escolaridade;
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
