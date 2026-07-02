package br.com.eduardofbom.stock_manager_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "documento_fiscal", length = 30)
    private String documentoFiscal;

    protected Fornecedor() {
    }

    public Fornecedor(String nome, String documentoFiscal) {
        this.nome = nome;
        this.documentoFiscal = documentoFiscal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fornecedor)) return false;

        Fornecedor outra = (Fornecedor) o;
        return id != null && id.equals(outra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
