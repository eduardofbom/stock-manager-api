package br.com.eduardofbom.stock_manager_api.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "data_movimentacao", nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao= LocalDateTime.now();

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TiposMovimentacao tipo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Usar apenas quando precisar instancial manualmente
    public MovimentacaoEstoque(Lote lote, Usuario usuario, BigDecimal quantidade, TiposMovimentacao tipo, String descricao) {
        this.lote = lote;
        this.usuario = usuario;
        this.dataMovimentacao = LocalDateTime.now();
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    protected MovimentacaoEstoque() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimentacaoEstoque)) return false;

        MovimentacaoEstoque outra = (MovimentacaoEstoque) o;
        return id != null && id.equals(outra.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
