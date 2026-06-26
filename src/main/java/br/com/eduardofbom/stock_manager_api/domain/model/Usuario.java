package br.com.eduardofbom.stock_manager_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargosUsuario cargo;

    @Column(nullable = false)
    private Boolean ativo = true;

    protected Usuario() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario outra = (Usuario) o;
        return id != null && id.equals(outra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
