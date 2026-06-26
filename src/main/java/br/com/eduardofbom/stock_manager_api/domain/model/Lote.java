package br.com.eduardofbom.stock_manager_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime lancamento = LocalDateTime.now();

    @Column(name = "quant_inicial", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantInicial;

    @Setter
    @Column(name = "quant_disponivel", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantDisponivel;

    @Setter
    @Column(name = "custo_unid", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoUnid;

    @Setter
    @Column(nullable = false)
    private LocalDate validade;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusLote status = StatusLote.ATIVO;

    // Usar apenas se precisar instanciar o lote manualmente
    public Lote(Produto produto, Fornecedor fornecedor, BigDecimal quantidade, BigDecimal custoUnid, LocalDate validade) {
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.lancamento = LocalDateTime.now();
        this.quantInicial = quantidade;
        this.quantDisponivel = quantidade;
        this.custoUnid = custoUnid;
        this.validade = validade;
        this.status = StatusLote.ATIVO;
    }

    protected Lote() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lote)) return false;

        Lote outra = (Lote) o;
        return id != null && id.equals(outra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
