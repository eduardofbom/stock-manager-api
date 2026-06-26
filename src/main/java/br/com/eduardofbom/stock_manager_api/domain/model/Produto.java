package br.com.eduardofbom.stock_manager_api.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoriaId;

    @Column(name = "cod_barras", length = 13)
    private String codBarras;

    @Column(name = "unid_medida", nullable = false, length = 2)
    private String unidMedida;

    @Column(name = "estoq_min", nullable = false, precision = 10, scale = 3)
    private BigDecimal estoqMin;

    @Column(name = "quant_atual", precision = 10, scale = 3)
    private BigDecimal quantAtual = BigDecimal.ZERO;

    @Column(name = "preco_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(nullable = false)
    private Boolean ativo = true;

    protected Produto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;

        Produto outra = (Produto) o;
        return id != null && id.equals(outra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
