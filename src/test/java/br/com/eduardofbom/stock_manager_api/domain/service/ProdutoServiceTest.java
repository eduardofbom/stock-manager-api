package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoResponseDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoUpdateDTO;
import br.com.eduardofbom.stock_manager_api.domain.model.Categoria;
import br.com.eduardofbom.stock_manager_api.domain.model.Produto;
import br.com.eduardofbom.stock_manager_api.domain.repository.ICategoriaRepository;
import br.com.eduardofbom.stock_manager_api.domain.repository.IProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @Mock
    private ICategoriaRepository categoriaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve criar um produto com sucesso quando a categoria informada existir")
    void deveCriarProdutoComSucessoQuandoCategoriaExistir() {
        Long categoriaId = 1L;
        Categoria categoriaFake = new Categoria("Bebidas");

        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(
                "Coca-Cola 2L", "789123456", "UN",
                new BigDecimal("10.000"), new BigDecimal("8.50"), categoriaId
        );

        Produto produtoSalvoFake = new Produto(
                produtoRequestDTO.nome(), categoriaFake, produtoRequestDTO.unidMedida(),
                produtoRequestDTO.precoVenda()
        );
        produtoSalvoFake.setCodBarras(produtoRequestDTO.codBarras());
        produtoSalvoFake.setEstoqMin(produtoRequestDTO.estoqMin());

        Field idField;
        try {
            idField = Produto.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(produtoSalvoFake, 100L);
        } catch (Exception e) {
            fail("Falha ao injetar ID de teste via reflexão");
        }

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaFake));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvoFake);

        ProdutoResponseDTO response = produtoService.criar(produtoRequestDTO);

        assertNotNull(response);
        assertEquals(100L, response.id());
        assertEquals("Coca-Cola 2L", response.nome());
        assertEquals("789123456", response.codBarras());
        assertEquals("Bebidas", response.categoriaName());
        assertEquals(BigDecimal.ZERO, response.quantAtual());

        verify(categoriaRepository, times(1)).findById(categoriaId);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção e abortar salvamento quando a categoria não for encontrada")
    void deveLancarExcecaoEAbortarSalvamentoQuandoCategoriaNaoEncontrada() {
        Long idInexistente = 99L;
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(
                "Sprinte 2L", "789123457", "UN",
                new BigDecimal("10.000"), new BigDecimal("7.50"),
                idInexistente
        );

        when(categoriaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> produtoService.criar(produtoRequestDTO));

        assertTrue(excecao.getMessage().contains("Categoria não encontrada com o ID: " + idInexistente));

        verify(produtoRepository, never()).save(any(Produto.class));
        verify(categoriaRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso quando produto e categoria existirem")
    void deveAtualizarProdutoComSucesso() {
        Long produtoId = 100L;
        Long novaCategoriaId = 2L;

        Categoria categoriaAntiga = new Categoria("Bebidas");
        Categoria categoriaNova = new Categoria("Alimentos");

        Produto produtoExistente = new Produto(
                "Fanta Laranja 2L", categoriaAntiga, "UN",
                new BigDecimal("5.00")
        );
        produtoExistente.setCodBarras("111111111");
        produtoExistente.setEstoqMin(new BigDecimal("10.000"));

        ProdutoUpdateDTO produtoUpdateDTO = new ProdutoUpdateDTO(
                "Fanta Uva 2L", new BigDecimal("10.000"),
                new BigDecimal("6.50"), novaCategoriaId
        );

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        when(categoriaRepository.findById(novaCategoriaId)).thenReturn(Optional.of(categoriaNova));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProdutoResponseDTO response = produtoService.atualizar(produtoId, produtoUpdateDTO);

        assertNotNull(response);
        assertEquals("Fanta Uva 2L", response.nome());
        assertEquals("Alimentos", response.categoriaName());
        assertEquals(new BigDecimal("6.50"), response.precoVenda());
        assertEquals("111111111", response.codBarras());  // nao pode ser alterado em uma atualizacao
        assertEquals(BigDecimal.ZERO, response.quantAtual());  // nao pode ser alterado em uma atualizacao

        verify(produtoRepository, times(1)).findById(produtoId);
        verify(categoriaRepository, times(1)).findById(novaCategoriaId);
        verify(produtoRepository, times(1)).save(produtoExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção e abortar ao tentar atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        Long produtoInexistenteId = 999L;
        ProdutoUpdateDTO produtoUpdateDTO = new ProdutoUpdateDTO(
                "Fanta Uva 2L", new BigDecimal("10.000"),
                new BigDecimal("6.50"), 1L
        );

        when(produtoRepository.findById(produtoInexistenteId)).thenReturn(Optional.empty());

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
                () -> produtoService.atualizar(produtoInexistenteId, produtoUpdateDTO));

        assertTrue(excecao.getMessage().contains("Produto não encontrado com o ID: " + produtoInexistenteId));

        verify(categoriaRepository, never()).findById(anyLong());
        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
