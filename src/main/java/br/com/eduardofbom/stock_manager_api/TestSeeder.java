package br.com.eduardofbom.stock_manager_api;

import br.com.eduardofbom.stock_manager_api.domain.model.Categoria;
import br.com.eduardofbom.stock_manager_api.domain.model.Fornecedor;
import br.com.eduardofbom.stock_manager_api.domain.model.Produto;
import br.com.eduardofbom.stock_manager_api.domain.repository.ICategoriaRepository;
import br.com.eduardofbom.stock_manager_api.domain.repository.IFornecedorRepository;
import br.com.eduardofbom.stock_manager_api.domain.repository.IProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TestSeeder.class);

    private final ICategoriaRepository categoriaRepository;
    private final IFornecedorRepository fornecedorRepository;
    private final IProdutoRepository produtoRepository;

    public TestSeeder(ICategoriaRepository categoriaRepository, IFornecedorRepository fornecedorRepository, IProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("--- INICIANDO TESTE DE BANCO DE DADOS ---");

        if (categoriaRepository.count() == 0) {
            log.info("Criando entidades...");

            Categoria bebidas = new Categoria("Bebidas");
            categoriaRepository.save(bebidas);

            Fornecedor distribuidora = new Fornecedor("Distribuidora Coca-Cola S.A","123456789000109");
            fornecedorRepository.save(distribuidora);

            Produto refrigerante = new Produto(
                    "Refrigerante 2L",
                    bebidas,
                    "UN",
                    new BigDecimal("8.50"));
            produtoRepository.save(refrigerante);

            log.info("Dados inseridos com sucesso!");
        }

        log.info("--- BUSCANDO DADOS NO POSTGRESQL ---");
        List<Produto> produtos = produtoRepository.buscarTodosComCategoria();

        for (Produto p : produtos) {
            log.info("Produto encontrado: {} | Categoria: {} | Preço: R$ {}",
                    p.getNome(), p.getCategoria().getNome(), p.getPrecoVenda());
        }

        log.info("--- FIM DO TESTE DE BANCO DE DADOS ---");
    }
}
