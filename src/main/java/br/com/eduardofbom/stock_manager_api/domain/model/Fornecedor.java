package br.com.eduardofbom.stock_manager_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "documento_fiscal", length = 30, unique = true)
    private String documentoFiscal;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FornecedorContato> contatos = new ArrayList<>();

    @Column(nullable = false)
    private boolean ativo = true;

    protected Fornecedor() {
    }

    public Fornecedor(String nome, String documentoFiscal) {
        this.nome = nome;
        this.documentoFiscal = documentoFiscal;
    }

    // Sincronizacao essencial para o JPA
    public void adicionarContato(FornecedorContato contato) {
        contatos.add(contato);
        contato.setFornecedor(this);
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
